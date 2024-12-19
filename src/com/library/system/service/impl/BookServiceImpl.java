package com.library.system.service.impl;

import com.library.system.dao.*;
import com.library.system.dao.impl.AuthorDAOImpl;
import com.library.system.dao.impl.BookDAOImpl;
import com.library.system.dao.impl.BooksCategoryDAOImpl;
import com.library.system.dao.impl.CategoryDAOImpl;
import com.library.system.model.Book;
import com.library.system.model.Category;
import com.library.system.service.BookService;
import com.library.system.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BookServiceImpl implements BookService {

    // Déclarez la collection books
    private List<Book> books = new ArrayList<>();
    private final BookDAO bookDAO;
    private final AuthorDAO authorDAO;
    private final CategoryDAO categoryDAO;
    private final BooksCategoryDAO booksCategoryDAO;
    private final BookAuthorDAO bookAuthorDAO = new BookAuthorDAO();

    // Constructeur alternatif avec une connexion
    public BookServiceImpl(Connection connection) {
        this.authorDAO = new AuthorDAOImpl(connection);  // Créez l'instance de AuthorDAO
        this.bookDAO = new BookDAOImpl(connection, authorDAO);  // Passez AuthorDAO au constructeur de BookDAOImpl
        this.categoryDAO = new CategoryDAOImpl(connection);
        this.booksCategoryDAO = new BooksCategoryDAOImpl(connection);


    }


    public BookServiceImpl(BookDAO bookDAO, AuthorDAO authorDAO, CategoryDAO categoryDAO, BooksCategoryDAO booksCategoryDAO) {
        this.bookDAO = bookDAO;
        this.authorDAO = authorDAO;
        this.categoryDAO = categoryDAO;
        this.booksCategoryDAO = booksCategoryDAO;
    }


    @Override
    public boolean addBook(Book book) {
        books.add(book); // Ajouter à la liste des livres
        System.out.println("Book added: " + book.getTitle());
        return false;
    }

    @Override
    public boolean addBookWithRelations(Book book, String[] authors, String[] categories) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            connection.setAutoCommit(false);

            // Ajouter le livre
            int bookId = bookDAO.addBook(book, connection);

            // Ajouter les auteurs et les relations
            List<String> authorNames = Arrays.asList(authors);
            bookDAO.addBookWithAuthors(book, authorNames);  // Utilisation de la méthode addBookWithAuthors

            // Ajouter les catégories et les relations
            for (String categoryName : categories) {
                Category categoryId = categoryDAO.getOrCreateCategory(categoryName.trim(), connection);
                booksCategoryDAO.addBookCategoryRelation(bookId, categoryId.getCategoryId(), connection);
            }

            connection.commit();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    @Override
    public void updateBook(Book book) {
        for (Book b : books) {
            if (b.getBookId() == book.getBookId()) {
                b.setTitle(book.getTitle());
                b.setNumberOfCopies(book.getNumberOfCopies());
                System.out.println("Book updated: " + book.getTitle());
            }
        }
    }

    @Override
    public void removeBook(int bookId) {
        String query = "DELETE FROM \"book\" WHERE book_id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, bookId);
            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Le livre avec l'ID " + bookId + " a été supprimé.");
            } else {
                System.out.println("Aucun livre trouvé avec l'ID " + bookId + ".");
            }
        } catch (Exception e) {
            System.err.println("Erreur lors de la suppression du livre : " + e.getMessage());
        }
    }

    // Méthode pour récupérer tous les livres disponibles
    @Override
    public void displayAvailableBooks() {
        String query = "SELECT * FROM book WHERE number_of_copies > 0";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                String title = resultSet.getString("title");
                int numberOfCopies = resultSet.getInt("number_of_copies");
                System.out.println("Title: " + title + " | Copies Available: " + numberOfCopies);
            }

        } catch (SQLException e) {
            System.err.println("Error retrieving books: " + e.getMessage());
        }
    }

    // Méthode pour vérifier la disponibilité d'un livre
    @Override
    public boolean isAvailable(Book book) {
        String query = "SELECT number_of_copies FROM book WHERE book_id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, book.getBookId());
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int numberOfCopies = resultSet.getInt("number_of_copies");
                return numberOfCopies > 0;
            }

        } catch (SQLException e) {
            System.err.println("Error checking availability: " + e.getMessage());
        }

        return false;
    }

    // Méthode pour rechercher un livre par son titre
    @Override
    public List<Book> searchByTitle(String title) {
        List<Book> books = new ArrayList<>();
        String query = "SELECT * FROM book WHERE book_title LIKE ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, "%" + title + "%");
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String bookTitle = resultSet.getString("title");
                int numberOfCopies = resultSet.getInt("number_of_copies");
                books.add(new Book(id, bookTitle, numberOfCopies));
            }

        } catch (SQLException e) {
            System.err.println("Error searching books by title: " + e.getMessage());
        }

        return books;
    }

    // Méthode pour rechercher un livre par son auteur
    // Méthode pour rechercher un livre par son auteur
    @Override
    public List<Book> searchByAuthor(String authorName) {
        List<Book> books = new ArrayList<>();
        String query = "SELECT b.* FROM book b " +
                "JOIN book_author ba ON b.book_id = ba.book_id " + // jointure corrigée
                "JOIN author a ON ba.author_id = a.author_id " + // jointure corrigée
                "WHERE a.name LIKE ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, "%" + authorName + "%");
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String title = resultSet.getString("title");
                int numberOfCopies = resultSet.getInt("number_of_copies");
                books.add(new Book(id, title, numberOfCopies));
            }

        } catch (SQLException e) {
            System.err.println("Error searching books by author: " + e.getMessage());
        }

        return books;
    }

    // Méthode pour rechercher un livre par sa catégorie
    @Override
    public List<Book> searchByCategory(String category) {
        List<Book> books = new ArrayList<>();
        String query = "SELECT b.* FROM book b " +
                "JOIN books_category bc ON b.book_id = bc.book_id " + // jointure corrigée
                "JOIN category c ON bc.category_id = c.category_id " + // jointure corrigée
                "WHERE c.name LIKE ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, "%" + category + "%");
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String title = resultSet.getString("title");
                int numberOfCopies = resultSet.getInt("number_of_copies");
                books.add(new Book(id, title, numberOfCopies));
            }

        } catch (SQLException e) {
            System.err.println("Error searching books by category: " + e.getMessage());
        }

        return books;
    }


    // Méthode pour rechercher un livre par des mots-clés
    @Override
    public List<Book> searchByKeywords(String keywords) {
        List<Book> books = new ArrayList<>();
        String query = "SELECT b.* FROM book b " +
                "JOIN book_author ba ON b.book_id = ba.book_id " + // jointure corrigée
                "JOIN author a ON ba.author_id = a.author_id " + // jointure corrigée
                "JOIN books_category bc ON b.book_id = bc.book_id " + // jointure corrigée
                "JOIN category c ON bc.category_id = c.category_id " + // jointure corrigée
                "WHERE b.book_title LIKE ? OR a.name LIKE ? OR c.name LIKE ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            String searchPattern = "%" + keywords + "%";
            statement.setString(1, searchPattern);
            statement.setString(2, searchPattern);
            statement.setString(3, searchPattern);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String title = resultSet.getString("title");
                int numberOfCopies = resultSet.getInt("number_of_copies");
                books.add(new Book(id, title, numberOfCopies));
            }

        } catch (SQLException e) {
            System.err.println("Error searching books by keywords: " + e.getMessage());
        }

        return books;
    }

}
