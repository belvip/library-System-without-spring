package com.library.system.dao.impl;

import com.library.system.dao.AuthorDAO;
import com.library.system.model.Author;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthorDAOImpl implements AuthorDAO {

    @Override
    public Author getOrCreateAuthor(String authorName, Connection connection) {
        // Vérifier si l'auteur existe déjà
        String checkQuery = "SELECT id, firstname FROM author WHERE firstname = ?";
        try (PreparedStatement checkStmt = connection.prepareStatement(checkQuery)) {
            checkStmt.setString(1, authorName); // Assurez-vous que c'est authorName et non name
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) {
                // Si l'auteur existe déjà, retourner l'objet Author avec l'ID et le prénom
                return new Author(rs.getInt("id"), rs.getString("firstname"), "", "");
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Gérer les erreurs SQL
        }

        // Si l'auteur n'existe pas, l'insérer dans la base de données
        String insertQuery = "INSERT INTO author (firstname) VALUES (?) RETURNING id";
        try (PreparedStatement insertStmt = connection.prepareStatement(insertQuery)) {
            insertStmt.setString(1, authorName); // Assurez-vous que c'est authorName et non name
            ResultSet rs = insertStmt.executeQuery();

            if (rs.next()) {
                // Retourner l'ID nouvellement créé et le prénom de l'auteur
                return new Author(rs.getInt("id"), authorName, "", "");  // Utilisez des valeurs par défaut
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Gérer les erreurs SQL
        }

        return null; // Retourner null si une erreur se produit ou l'auteur n'est pas trouvé/créé
    }

}
