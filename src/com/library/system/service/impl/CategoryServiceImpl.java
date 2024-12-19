package com.library.system.service.impl;

import com.library.system.dao.CategoryDAO;
import com.library.system.model.Category;
import com.library.system.service.CategoryService;
import com.library.system.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class CategoryServiceImpl implements CategoryService {
    private final CategoryDAO categoryDAO;

    // Constructeur pour injecter le DAO
    public CategoryServiceImpl(CategoryDAO categoryDAO) {
        this.categoryDAO = categoryDAO;
    }

    @Override
    public int getOrCreateCategory(String categoryName) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            // Retourne l'ID de la catégorie (int) et non un objet Category
            return categoryDAO.getOrCreateCategory(categoryName, connection).getCategoryId();
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la récupération ou de la création de la catégorie", e);
        }
    }

    @Override
    public List<Category> getAllCategories() {
        try (Connection connection = DatabaseConnection.getConnection()) {
            return categoryDAO.getAllCategories(connection);
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la récupération des catégories", e);
        }
    }
}
