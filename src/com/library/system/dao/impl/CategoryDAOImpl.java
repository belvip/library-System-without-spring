package com.library.system.dao.impl;

import com.library.system.dao.CategoryDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CategoryDAOImpl implements CategoryDAO {
    @Override
    public int getOrCreateCategory(String name, Connection connection) throws SQLException {
        // Vérifier si la catégorie existe déjà
        String checkQuery = "SELECT id FROM category WHERE name = ?";
        try (PreparedStatement checkStmt = connection.prepareStatement(checkQuery)) {
            checkStmt.setString(1, name);
            ResultSet rs = checkStmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("id"); // Retourner l'ID existant
            }
        }

        // Si la catégorie n'existe pas, l'insérer dans la base de données
        String insertQuery = "INSERT INTO category (name) VALUES (?) RETURNING id";
        try (PreparedStatement insertStmt = connection.prepareStatement(insertQuery)) {
            insertStmt.setString(1, name);
            ResultSet rs = insertStmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("id"); // Retourner l'ID nouvellement créé
            }
        }

        throw new SQLException("Erreur lors de l'insertion de la catégorie.");
    }
}

