package com.library.system.model;

public class Category {
    private int categoryId;
    private String category_name;

    // Constructeur par défaut
    public Category() {}

    // Constructeur avec paramètres
    public Category(int categoryId, String name) {
        this.categoryId = categoryId;
        setName(name); // Validation ajoutée ici
    }

    // Getters et Setters
    public int getCategoryId() {
        return categoryId;
    }

    public void setId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return category_name;
    }

    public void setName(String category_name) {
        if (category_name == null || category_name.trim().isEmpty()) {
            throw new IllegalArgumentException("Le nom de la catégorie ne peut pas être vide.");
        }

        if (category_name.length() > 50) {
            throw new IllegalArgumentException("Le nom de la catégorie ne peut pas dépasser 50 caractères.");
        }

        // Regex pour vérifier que le nom de la catégorie contient uniquement des lettres et des espaces
        if (!category_name.matches("[a-zA-Z ]+")) {
            throw new IllegalArgumentException("Le nom de la catégorie ne peut contenir que des lettres et des espaces.");
        }

        this.category_name = category_name;
    }

}
