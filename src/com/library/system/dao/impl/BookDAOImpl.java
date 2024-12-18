package com.library.system.dao.impl;

import com.library.system.dao.BookDAO;
import com.library.system.model.Book;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BookDAOImpl implements BookDAO {

    @Override
    public int addBook(Book book, Connection connection) throws SQLException {
        String sql = "INSERT INTO book (title, numberofcopies) VALUES (?, ?, ?, ?) RETURNING id";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, book.getTitle());
            statement.setInt(4, book.getNumberOfCopies());

            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            } else {
                throw new SQLException("Failed to insert book, no ID obtained.");
            }
        }
    }
}
