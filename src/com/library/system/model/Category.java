package com.library.system.model;

public class Category {
    private int id;
    private String name;

    // Constructeur par défaut
    public Category() {}

    // Constructeur avec paramètres
    public Category(int id, String name) {
        this.id = id;
        setName(name); // Validation ajoutée ici
    }

    // Getters et Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Le nom de la catégorie ne peut pas être vide.");
        }

        if (name.length() > 50) {
            throw new IllegalArgumentException("Le nom de la catégorie ne peut pas dépasser 50 caractères.");
        }

        // Regex pour vérifier que le nom de la catégorie contient uniquement des lettres et des espaces
        if (!name.matches("[a-zA-Z\\s]+")) {
            throw new IllegalArgumentException("Le nom de la catégorie ne peut contenir que des lettres et des espaces.");
        }

        this.name = name;
    }

}
