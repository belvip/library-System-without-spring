package com.library.system.service;

import com.library.system.model.Author;

import java.sql.SQLException;

public interface AuthorService {

    Author getOrCreateAuthor(String firstName, String lastName, String email) throws SQLException;
}
