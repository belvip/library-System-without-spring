package com.library.system.dao;

import com.library.system.model.Author;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface AuthorDAO {
    Author getOrCreateAuthor(String authorName, Connection connection);

    // Méthode pour vérifier ou créer un auteur
    /*public int getOrCreateAuthor(String name, Connection connection) throws SQLException {
        // Vérifier si l'auteur existe déjà
        String checkQuery = "SELECT id FROM author WHERE firstname = ?";
        try (PreparedStatement checkStmt = connection.prepareStatement(checkQuery)) {
            checkStmt.setString(1, name);
            ResultSet rs = checkStmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("id"); // Retourner l'ID existant
            }
        }

        // Si l'auteur n'existe pas, l'insérer dans la base de données
        String insertQuery = "INSERT INTO author (firstname) VALUES (?) RETURNING id";
        try (PreparedStatement insertStmt = connection.prepareStatement(insertQuery)) {
            insertStmt.setString(1, name);
            ResultSet rs = insertStmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("id"); // Retourner l'ID nouvellement créé
            }
        }

        throw new SQLException("Erreur lors de l'insertion de l'auteur.");
    }*/
}
