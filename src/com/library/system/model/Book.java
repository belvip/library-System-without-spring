package com.library.system.model;

import java.util.HashSet;
import java.util.Set;

public class Book {
    private int id;
    private String title;
    private int numberOfCopies;

    // Relations
    private Set<Author> authors = new HashSet<>();  // Un livre peut avoir plusieurs auteurs
    private Set<Category> categories = new HashSet<>(); // Un livre peut appartenir à plusieurs catégories

    // Constructeurs
    public Book() {}

    public Book(int id, String title, int numberOfCopies) {
        this.id = id;
        setTitle(title); // Validation ajoutée ici
        setNumberOfCopies(numberOfCopies); // Validation ajoutée ici
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
        // Validation complexe sur le titre
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Le titre ne peut pas être vide.");
        }

        if (!title.matches("[a-zA-Z\\s]+")) {
            throw new IllegalArgumentException("Le titre ne peut contenir que des lettres et des espaces.");
        }

        if (title.length() > 100) {
            throw new IllegalArgumentException("Le titre ne peut pas dépasser 100 caractères.");
        }

        // Validation complexe : le titre ne doit pas commencer par un espace et doit contenir au moins un mot
        if (title.startsWith(" ")) {
            throw new IllegalArgumentException("Le titre ne doit pas commencer par un espace.");
        }

        if (title.split("\\s+").length < 1) {
            throw new IllegalArgumentException("Le titre doit contenir au moins deux mots.");
        }

        this.title = title;
    }

    public int getNumberOfCopies() {
        return numberOfCopies;
    }

    public void setNumberOfCopies(int numberOfCopies) {
        // Validation complexe sur le nombre de copies
        if (numberOfCopies < 0) {
            throw new IllegalArgumentException("Le nombre de copies doit être supérieur ou égal à 0.");
        }

        if (numberOfCopies > 1000) {
            throw new IllegalArgumentException("Le nombre de copies ne peut pas être supérieur à 1000.");
        }

        this.numberOfCopies = numberOfCopies;
    }

    public Set<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(Set<Author> authors) {
        // Validation complexe sur les auteurs bien
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
        StringBuilder authorsList = new StringBuilder();
        for (Author author : authors) {
            authorsList.append(author.getFirstName()).append(" ").append(author.getLastName()).append(", ");
        }
        // Enlever la dernière virgule et l'espace
        if (authorsList.length() > 0) {
            authorsList.setLength(authorsList.length() - 2);
        }

        StringBuilder categoriesList = new StringBuilder();
        for (Category category : categories) {
            categoriesList.append(category.getName()).append(", ");
        }
        // Enlever la dernière virgule et l'espace
        if (categoriesList.length() > 0) {
            categoriesList.setLength(categoriesList.length() - 2);
        }

        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", numberOfCopies=" + numberOfCopies +
                ", authors=[" + authorsList.toString() + "]" +
                ", categories=[" + categoriesList.toString() + "]" +
                '}';
    }

}
