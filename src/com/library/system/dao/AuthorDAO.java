package com.library.system.dao;

import com.library.system.model.Author;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface AuthorDAO {
    //Author getOrCreateAuthor(String authorName, Connection connection);
    Author getOrCreateAuthor(String firstName, String lastName, String email) throws SQLException;

    Author findByEmail(String email);

    Author save(Author author) throws SQLException;


}
