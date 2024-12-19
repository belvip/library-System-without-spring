package com.library.system.dao.impl;

import com.library.system.dao.AuthorDAO;
import com.library.system.model.Author;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthorDAOImpl implements AuthorDAO {
    private final Connection connection;

    // Constructeur pour injecter la connexion
    public AuthorDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Author getOrCreateAuthor(String firstName, String lastName, String email) throws SQLException {
        // Requête SELECT pour vérifier si l'auteur existe déjà par email
        String selectQuery = "SELECT * FROM author WHERE email = ?";

        // Requête INSERT pour ajouter un auteur si nécessaire
        String insertQuery = "INSERT INTO author (first_name, last_name, email) VALUES (?, ?, ?) RETURNING author_id, first_name, last_name, email";

        try (PreparedStatement selectStmt = connection.prepareStatement(selectQuery)) {
            selectStmt.setString(1, email);
            ResultSet rs = selectStmt.executeQuery();

            // Si l'auteur existe déjà, le récupérer
            if (rs.next()) {
                return new Author(
                        rs.getInt("author_id"),  // Correction du nom de la colonne
                        rs.getString("first_name"),  // Utilisation de first_name
                        rs.getString("last_name"),  // Utilisation de last_name
                        rs.getString("email")
                );
            } else {
                // Si l'auteur n'existe pas, on l'insère
                try (PreparedStatement insertStmt = connection.prepareStatement(insertQuery)) {
                    insertStmt.setString(1, firstName);
                    insertStmt.setString(2, lastName);
                    insertStmt.setString(3, email);
                    ResultSet insertRs = insertStmt.executeQuery();

                    // Récupérer les données de l'auteur inséré
                    if (insertRs.next()) {
                        return new Author(
                                insertRs.getInt("author_id"),  // Correction du nom de la colonne
                                insertRs.getString("first_name"),
                                insertRs.getString("last_name"),
                                insertRs.getString("email")
                        );
                    }
                }
            }
        }
        throw new SQLException("Erreur lors de la récupération ou de la création de l'auteur.");
    }

    @Override
    public Author findByEmail(String email) {
        return null;
    }

    @Override
    public Author save(Author author) throws SQLException {
        // Requête pour insérer un auteur dans la base de données
        String insertQuery = "INSERT INTO author (first_name, last_name, email) VALUES (?, ?, ?) RETURNING author_id";

        try (PreparedStatement insertStmt = connection.prepareStatement(insertQuery)) {
            insertStmt.setString(1, author.getFirstName());
            insertStmt.setString(2, author.getLastName());
            insertStmt.setString(3, author.getEmail());

            // Exécuter la requête d'insertion et récupérer l'ID
            ResultSet resultSet = insertStmt.executeQuery();
            if (resultSet.next()) {
                // Mettre à jour l'objet Author avec l'ID généré
                author.setAuthorId(resultSet.getInt("author_id"));
            }
        }
        return author;  // Retourner l'auteur avec son ID
    }


}
