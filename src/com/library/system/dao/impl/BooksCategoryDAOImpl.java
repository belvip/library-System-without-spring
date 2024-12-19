package com.library.system.dao.impl;

import com.library.system.dao.BooksCategoryDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BooksCategoryDAOImpl implements BooksCategoryDAO {

    private final Connection connection;

    // Constructeur pour accepter une connexion
    public BooksCategoryDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void addBookCategoryRelation(int bookId, int categoryId, Connection connection) throws SQLException {
        String query = "INSERT INTO books_category (book_id, category_id) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, bookId);
            stmt.setInt(2, categoryId);
            stmt.executeUpdate();
        }
    }

}
