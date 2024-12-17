package com.library.system.model;

public class Books_Loans {
    private int bookId;
    private int memberId;

    // Constructeur par défaut
    public Books_Loans() {}

    // Constructeur avec paramètres
    public Books_Loans(int bookId, int memberId) {
        this.bookId = bookId;
        this.memberId = memberId;
    }

    // Getters et Setters
    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }


}
