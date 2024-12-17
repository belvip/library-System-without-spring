package com.library.system.model;

public class Books_Category {
    private int bookId;
    private int categoryId;

    // Constructeur par défaut
    public Books_Category() {}

    // Constructeur avec paramètres
    public Books_Category(int bookId, int categoryId) {
        this.bookId = bookId;
        this.categoryId = categoryId;
    }

    // Getters et Setters
    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    // Méthodes supplémentaires si nécessaires
}
