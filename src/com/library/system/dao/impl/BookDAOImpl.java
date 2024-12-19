package com.library.system.dao.impl;

import com.library.system.dao.AuthorDAO;
import com.library.system.dao.BookDAO;
import com.library.system.model.Author;
import com.library.system.model.Book;
import com.library.system.model.Category;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;

public class BookDAOImpl implements BookDAO {

    private final Connection connection;
    private final AuthorDAO authorDAO;

    // Constructeur pour accepter une connexion
    public BookDAOImpl(Connection connection, AuthorDAO authorDAO) {
        this.connection = connection;
        this.authorDAO = authorDAO;
    }

    @Override
    public int addBook(Book book, Connection connection) throws SQLException {
        // Requête SQL modifiée pour inclure l'ID de l'auteur et la catégorie
        String query = "INSERT INTO book (book_title, number_of_copies) VALUES (?, ?) RETURNING book_id";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            // Paramétrage des valeurs à insérer dans la requête
            stmt.setString(1, book.getTitle());  // Titre du livre
            stmt.setInt(2, book.getNumberOfCopies());  // Nombre de copies

            // Exécution de la requête pour ajouter le livre
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int bookId = rs.getInt("book_id");

                // Associer l'auteur(s) et la catégorie(s) au livre
                // Ajoutez les relations dans les tables de jointure (par exemple, book_author et book_category)
                addBookAuthors(bookId, book.getAuthors(), connection);
                addBookCategories(bookId, book.getCategories(), connection);

                // Retourner l'ID du livre nouvellement inséré
                return bookId;
            } else {
                throw new SQLException("Failed to insert book, no ID obtained.");
            }
        }
    }

    @Override
    public void addBookWithAuthors(Book book, List<String> authorNames) throws SQLException {
        // Insérer le livre
        String insertBookQuery = "INSERT INTO book (book_title, number_of_copies) VALUES (?, ?) RETURNING book_id";
        try (PreparedStatement stmt = connection.prepareStatement(insertBookQuery)) {
            stmt.setString(1, book.getTitle());
            stmt.setInt(2, book.getNumberOfCopies());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int bookId = rs.getInt("book_id");
                book.setBookId(bookId);

                // Ajouter les auteurs
                for (String authorName : authorNames) {
                    String[] parts = authorName.split(" ");
                    String firstName = parts[0];
                    String lastName = parts[1];
                    String email = firstName + "." + lastName + "@default.com";

                    Author author = authorDAO.getOrCreateAuthor(firstName, lastName, email);

                    // Ajouter la relation livre-auteur
                    String insertBookAuthorQuery = "INSERT INTO book_author (book_id, author_id) VALUES (?, ?)";
                    try (PreparedStatement insertStmt = connection.prepareStatement(insertBookAuthorQuery)) {
                        insertStmt.setInt(1, bookId);
                        insertStmt.setInt(2, author.getAuthorId());
                        insertStmt.executeUpdate();
                    }
                }
            }
        }
    }


    // Méthode pour ajouter les relations avec les auteurs
    private void addBookAuthors(int bookId, Set<Author> authors, Connection connection) throws SQLException {
        String insertQuery = "INSERT INTO book_author (book_id, author_id) VALUES (?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(insertQuery)) {
            for (Author author : authors) {
                stmt.setInt(1, bookId);  // Utilisez l'ID du livre
                stmt.setInt(2, author.getAuthorId());  // Utilisez l'ID de l'auteur
                stmt.addBatch();  // Ajout à un lot de requêtes
            }
            stmt.executeBatch();  // Exécution du lot
        }
    }


    // Méthode pour ajouter les relations avec les catégories
    private void addBookCategories(int book_id, Set<Category> categories, Connection connection) throws SQLException {
        String categoryQuery = "INSERT INTO books_category (book_id, category_id) VALUES (?, ?)";

        try (PreparedStatement categoryStmt = connection.prepareStatement(categoryQuery)) {
            for (Category category : categories) {
                categoryStmt.setInt(1, book_id);  // ID du livre
                categoryStmt.setInt(2, category.getCategoryId());  // ID de la catégorie
                categoryStmt.addBatch();  // Ajout à un lot de requêtes
            }
            categoryStmt.executeBatch();  // Exécution du lot
        }
    }
}
