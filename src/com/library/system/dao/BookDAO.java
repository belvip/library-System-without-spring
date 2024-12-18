package com.library.system.dao;

import com.library.system.model.Book;
import java.sql.Connection;
import java.sql.SQLException;

public interface BookDAO {
    int addBook(Book book, Connection connection) throws SQLException;
}
