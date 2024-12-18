package com.library.system.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BookAuthorDAO {

    // Méthode pour ajouter une relation entre un livre et un auteur
    public void addBookAuthorRelation(int bookId, int authorId, Connection connection) throws SQLException {
        String sql = "INSERT INTO book_author (bookid, authorid) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, bookId);
            stmt.setInt(2, authorId);
            stmt.executeUpdate();
        }
    }

    // Autres méthodes pour gérer les auteurs et relations...
}
