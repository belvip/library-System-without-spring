package com.library.system.dao.impl;

import com.library.system.dao.BookDAO;
import com.library.system.model.Author;
import com.library.system.model.Book;
import com.library.system.model.Category;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;

public class BookDAOImpl implements BookDAO {

    @Override
    public int addBook(Book book, Connection connection) throws SQLException {
        // Requête SQL modifiée pour inclure l'ID de l'auteur et la catégorie
        String query = "INSERT INTO book (title, numberofcopies) VALUES (?, ?) RETURNING id";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            // Paramétrage des valeurs à insérer dans la requête
            stmt.setString(1, book.getTitle());  // Titre du livre
            stmt.setInt(2, book.getNumberOfCopies());  // Nombre de copies

            // Exécution de la requête pour ajouter le livre
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int bookId = rs.getInt("id");

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

    // Méthode pour ajouter les relations avec les auteurs
    private void addBookAuthors(int bookId, Set<Author> authors, Connection connection) throws SQLException {
        String authorQuery = "INSERT INTO book_author (bookid, authorid) VALUES (?, ?)";

        try (PreparedStatement authorStmt = connection.prepareStatement(authorQuery)) {
            for (Author author : authors) {
                authorStmt.setInt(1, bookId);  // ID du livre
                authorStmt.setInt(2, author.getId());  // ID de l'auteur
                authorStmt.addBatch();  // Ajout à un lot de requêtes
            }
            authorStmt.executeBatch();  // Exécution du lot
        }
    }

    // Méthode pour ajouter les relations avec les catégories
    private void addBookCategories(int bookId, Set<Category> categories, Connection connection) throws SQLException {
        String categoryQuery = "INSERT INTO books_category (bookid, categoryid) VALUES (?, ?)";

        try (PreparedStatement categoryStmt = connection.prepareStatement(categoryQuery)) {
            for (Category category : categories) {
                categoryStmt.setInt(1, bookId);  // ID du livre
                categoryStmt.setInt(2, category.getId());  // ID de la catégorie
                categoryStmt.addBatch();  // Ajout à un lot de requêtes
            }
            categoryStmt.executeBatch();  // Exécution du lot
        }
    }
}
