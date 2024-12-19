package com.library.system.model;

import java.util.Date;
import java.util.regex.Pattern;

public class Member {

    private int memberId;
    private String firstName;
    private String lastName;
    private String email;
    private Date adhesionDate;

    // Constructeur avec validations
    public Member(int id, String firstName, String lastName, String email, Date adhesionDate) {
        setId(id);
        setFirstName(firstName);
        setLastName(lastName);
        setEmail(email);
        setAdhesionDate(adhesionDate);
    }

    // Validation pour l'ID
    public void setId(int memberId) {
        if (memberId <= 0) {
            throw new IllegalArgumentException("L'ID doit être strictement positif.");
        }
        this.memberId = memberId;
    }

    // Validation pour le prénom
    public void setFirstName(String firstName) {
        if (!firstName.matches("^[a-zA-Z]{2,50}$")) {
            throw new IllegalArgumentException("Le prénom doit contenir uniquement des lettres (2-50 caractères).");
        }
        this.firstName = firstName;
    }

    // Validation pour le nom
    public void setLastName(String lastName) {
        if (!lastName.matches("^[a-zA-Z]{2,50}$")) {
            throw new IllegalArgumentException("Le nom doit contenir uniquement des lettres (2-50 caractères).");
        }
        this.lastName = lastName;
    }

    // Validation pour l'email
    public void setEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
        if (!Pattern.matches(emailRegex, email)) {
            throw new IllegalArgumentException("L'adresse email est invalide.");
        }
        this.email = email;
    }

    // Validation pour la date d'adhésion
    public void setAdhesionDate(Date adhesionDate) {
        if (adhesionDate.after(new Date())) {
            throw new IllegalArgumentException("La date d'adhésion ne peut pas être dans le futur.");
        }
        this.adhesionDate = adhesionDate;
    }

    // Getters
    public int getId() {
        return memberId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public Date getAdhesionDate() {
        return adhesionDate;
    }

    // Méthode pour afficher les détails du membre
    @Override
    public String toString() {
        return "Member{" +
                "id=" + memberId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", adhesionDate=" + adhesionDate +
                '}';
    }
}
