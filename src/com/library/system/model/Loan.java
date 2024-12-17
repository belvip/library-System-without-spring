package com.library.system.model;

import java.util.Date;
import java.util.List;

public class Loan {
    private int id;
    private Date loanDate;
    private Date dueDate;
    private Date returnDate;
    private Member member;  // Relation avec le membre
    private boolean isReturned;  // Indicateur si le livre est retourné ou pas
    private List<Book> books;  // Liste des livres empruntés

    // Constructeurs
    public Loan() {}

    public Loan(int id, Date loanDate, Date dueDate, Member member, List<Book> books) {
        this.id = id;
        setLoanDate(loanDate);  // Validation de la date d'emprunt
        setDueDate(dueDate);  // Validation de la date d'échéance
        this.member = member;  // Validation du membre
        setBooks(books);  // Validation des livres
        this.isReturned = false;  // L'emprunt n'est pas retourné par défaut
    }

    // Getters et Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getLoanDate() {
        return loanDate;
    }

    public void setLoanDate(Date loanDate) {
        if (loanDate == null) {
            throw new IllegalArgumentException("La date d'emprunt ne peut pas être nulle.");
        }
        if (loanDate.after(new Date())) {
            throw new IllegalArgumentException("La date d'emprunt ne peut pas être dans le futur.");
        }
        this.loanDate = loanDate;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        if (dueDate == null) {
            throw new IllegalArgumentException("La date d'échéance ne peut pas être nulle.");
        }
        if (dueDate.before(loanDate)) {
            throw new IllegalArgumentException("La date d'échéance ne peut pas être antérieure à la date d'emprunt.");
        }
        this.dueDate = dueDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        if (returnDate != null && returnDate.before(loanDate)) {
            throw new IllegalArgumentException("La date de retour ne peut pas être antérieure à la date d'emprunt.");
        }
        this.returnDate = returnDate;
        // Mettre à jour l'état retourné si la date de retour est définie
        this.isReturned = returnDate != null;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        if (member == null) {
            throw new IllegalArgumentException("Le membre ne peut pas être nul.");
        }
        this.member = member;
    }

    public boolean isReturned() {
        return isReturned;
    }

    public void setReturned(boolean returned) {
        this.isReturned = returned;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        if (books == null || books.isEmpty()) {
            throw new IllegalArgumentException("La liste des livres ne peut pas être vide.");
        }
        this.books = books;
    }
}
