package com.library.system.model;

import java.util.HashSet;
import java.util.Set;

public class Book {
    private int id;  // ID généré par la base de données
    private String title;
    private int numberOfCopies;


    // Relations
    private Set<Author> authors = new HashSet<>();  // Un livre peut avoir plusieurs auteurs
    private Set<Category> categories = new HashSet<>(); // Un livre peut appartenir à plusieurs catégories

    public Book() {
        // Initialiser les valeurs par défaut si nécessaire
        this.authors = new HashSet<>();
        this.categories = new HashSet<>();
    }


    // Constructeur sans ID
    public Book(String title, int numberOfCopies) {
        setTitle(title); // Validation ajoutée ici
        setNumberOfCopies(numberOfCopies); // Validation ajoutée ici
    }

    // Constructeur avec ID (utile si vous avez besoin de définir l'ID après)
    public Book(int id, String title, int numberOfCopies) {
        this.id = id;
        setTitle(title);
        setNumberOfCopies(numberOfCopies);
    }

    // Getters et Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Le titre ne peut pas être vide.");
        }
        this.title = title;
    }

    public int getNumberOfCopies() {
        return numberOfCopies;
    }

    public void setNumberOfCopies(int numberOfCopies) {
        if (numberOfCopies < 0) {
            throw new IllegalArgumentException("Le nombre de copies doit être supérieur ou égal à 0.");
        }
        this.numberOfCopies = numberOfCopies;
    }

    public Set<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(Set<Author> authors) {
        if (authors == null || authors.isEmpty()) {
            throw new IllegalArgumentException("Le livre doit avoir au moins un auteur.");
        }
        this.authors = authors;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }

    // Méthodes
    public boolean isAvailable() {
        return numberOfCopies > 0;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", numberOfCopies=" + numberOfCopies +
                '}';
    }
}
