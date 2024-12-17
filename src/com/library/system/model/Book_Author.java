package com.library.system.model;

public class Book_Author {
    private int bookId;
    private int authorId;

    // Constructeur par défaut
    public Book_Author() {}

    // Constructeur avec paramètres
    public Book_Author(int bookId, int authorId) {
        this.bookId = bookId;
        this.authorId = authorId;
    }

    // Getters et Setters
    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }


}
