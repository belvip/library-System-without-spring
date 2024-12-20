package com.library.system.model;

public class Author {
    private int authorId;
    private String firstName;
    private String lastName;
    private String email;

    // Constructeurs
    public Author() {}

    public Author(int authorId, String firstName, String lastName, String email) {
        this.authorId = authorId;
        setFirstName(firstName);  // Validation du prénom
        setLastName(lastName);    // Validation du nom
        setEmail(email);          // Validation de l'email
    }

    public Author(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    // Getters et Setters
    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        if (firstName == null || firstName.trim().isEmpty()) {
            throw new IllegalArgumentException("Le prénom ne peut pas être vide.");
        }
        if (firstName.length() > 50) {
            throw new IllegalArgumentException("Le prénom ne peut pas dépasser 50 caractères.");
        }
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        if (lastName == null || lastName.trim().isEmpty()) {
            throw new IllegalArgumentException("Le nom ne peut pas être vide.");
        }
        if (lastName.length() > 50) {
            throw new IllegalArgumentException("Le nom ne peut pas dépasser 50 caractères.");
        }
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("L'email ne peut pas être vide.");
        }
        // Regex pour valider l'email
        if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            throw new IllegalArgumentException("L'email n'est pas valide.");
        }
        this.email = email;
    }


}
