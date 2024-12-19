package com.library.system.dao.impl;

import com.library.system.dao.CategoryDAO;
import com.library.system.model.Category;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoryDAOImpl implements CategoryDAO {

    private final Connection connection;

    // Constructeur pour accepter une connexion
    public CategoryDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Category getOrCreateCategory(String categoryName, Connection connection) throws SQLException {
        String query = "SELECT category_id, category_name FROM category WHERE category_name = ?";
        String insertQuery = "INSERT INTO category (category_name) VALUES (?) RETURNING category_id, category_name";

        try (PreparedStatement selectStmt = connection.prepareStatement(query)) {
            selectStmt.setString(1, categoryName);
            ResultSet resultSet = selectStmt.executeQuery();

            if (resultSet.next()) {
                // Si la catégorie existe déjà, créez un objet Category
                int categoryId = resultSet.getInt("category_id");
                String name = resultSet.getString("category_name");
                return new Category(categoryId, name); // Retourne un objet Category
            } else {
                // Si la catégorie n'existe pas, insérez-la et récupérez l'ID
                try (PreparedStatement insertStmt = connection.prepareStatement(insertQuery)) {
                    insertStmt.setString(1, categoryName);
                    ResultSet insertResult = insertStmt.executeQuery();

                    if (insertResult.next()) {
                        int categoryId = insertResult.getInt("category_id");
                        String name = insertResult.getString("category_name");
                        return new Category(categoryId, name); // Retourne un objet Category
                    } else {
                        throw new SQLException("Insertion dans la table category a échoué.");
                    }
                }
            }
        }
    }


    @Override
    public List<Category> getAllCategories(Connection connection) throws SQLException {
        String query = "SELECT category_id, category_name FROM category";
        List<Category> categories = new ArrayList<>();

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int categoryId = rs.getInt("category_id");
                String categoryName = rs.getString("category_name");

                try {
                    // Tenter d'utiliser setName ou le constructeur
                    categories.add(new Category(categoryId, categoryName));
                } catch (IllegalArgumentException e) {
                    //System.err.println("Nom invalide détecté dans la base de données : " + categoryName);
                    // Ajouter une catégorie par défaut ou ignorer
                    categories.add(new Category(categoryId, "CategorieDefaut"));
                }
            }
        }

        return categories;
    }


}
