package com.library.system.service.impl;

import com.library.system.model.Book;
import com.library.system.service.BookService;
import com.library.system.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookServiceImpl implements BookService {

    // Déclarez la collection books
    private List<Book> books = new ArrayList<>();

    @Override
    public void addBook(Book book) {
        books.add(book); // Ajouter à la liste des livres
        System.out.println("Book added: " + book.getTitle());
    }

    @Override
    public void updateBook(Book book) {
        for (Book b : books) {
            if (b.getId() == book.getId()) {
                b.setTitle(book.getTitle());
                b.setNumberOfCopies(book.getNumberOfCopies());
                System.out.println("Book updated: " + book.getTitle());
            }
        }
    }

    @Override
    public void removeBook(int bookId) {
        books.removeIf(b -> b.getId() == bookId);
        System.out.println("Book removed with ID: " + bookId);
    }

    // Méthode pour récupérer tous les livres disponibles
    @Override
    public void displayAvailableBooks() {
        String query = "SELECT * FROM books WHERE number_of_copies > 0";

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
        String query = "SELECT number_of_copies FROM books WHERE id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, book.getId());
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
        String query = "SELECT * FROM books WHERE title LIKE ?";

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
    @Override
    public List<Book> searchByAuthor(String authorName) {
        List<Book> books = new ArrayList<>();
        String query = "SELECT b.* FROM books b " +
                "JOIN book_authors ba ON b.id = ba.book_id " +
                "JOIN authors a ON ba.author_id = a.id " +
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
        String query = "SELECT b.* FROM books b " +
                "JOIN book_categories bc ON b.id = bc.book_id " +
                "JOIN categories c ON bc.category_id = c.id " +
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

    @Override
    public List<Book> searchByKeywords(String keywords) {
        List<Book> books = new ArrayList<>();
        String query = "SELECT b.* FROM books b " +
                "JOIN book_authors ba ON b.id = ba.book_id " +
                "JOIN authors a ON ba.author_id = a.id " +
                "JOIN book_categories bc ON b.id = bc.book_id " +
                "JOIN categories c ON bc.category_id = c.id " +
                "WHERE b.title LIKE ? OR a.name LIKE ? OR c.name LIKE ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            // Utilisation des mots-clés pour rechercher dans le titre, l'auteur ou la catégorie
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