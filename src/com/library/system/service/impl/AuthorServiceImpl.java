package com.library.system.service.impl;

import com.library.system.dao.AuthorDAO;
import com.library.system.model.Author;
import com.library.system.service.AuthorService;

import java.sql.Connection;
import java.sql.SQLException;

public class AuthorServiceImpl implements AuthorService {
    private final AuthorDAO authorDAO;
    private final Connection connection;

    // Constructeur pour injecter le DAO et la connexion
    public AuthorServiceImpl(AuthorDAO authorDAO, Connection connection) {
        this.authorDAO = authorDAO;
        this.connection = connection;
    }

    @Override
    public Author getOrCreateAuthor(String firstName, String lastName, String email) throws SQLException {
        // Vérification des paramètres
        if (firstName == null || firstName.isEmpty() || lastName == null || lastName.isEmpty()) {
            throw new IllegalArgumentException("Le prénom et le nom de l'auteur sont obligatoires.");
        }

        // Appeler la méthode DAO pour récupérer ou créer l'auteur
        Author author = authorDAO.findByEmail(email);
        if (author == null) {
            // Si l'auteur n'existe pas, créer un nouvel auteur
            author = new Author(firstName, lastName, email);
            authorDAO.save(author);  // Sauvegarder l'auteur dans la base
        }

        return author;
    }
}
