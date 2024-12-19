package com.library.system.controller;

import com.library.system.model.Author;
import com.library.system.service.AuthorService;
import java.util.Scanner;

public class AuthorController {
    private final AuthorService authorService;

    // Injection du service
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    // Méthode pour gérer la récupération ou la création d'un auteur
    public Author handleAuthorCreation() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Entrez le prénom de l'auteur : ");
        String firstName = scanner.nextLine();
        System.out.print("Entrez le nom de l'auteur : ");
        String lastName = scanner.nextLine();
        System.out.print("Entrez l'email de l'auteur : ");
        String email = scanner.nextLine();

        try {
            // Appel au service pour récupérer ou créer l'auteur
            Author author = authorService.getOrCreateAuthor(firstName, lastName, email);

            if (author != null) {
                System.out.println("Auteur récupéré ou créé avec succès : " + author);
                return author; // Retourner l'auteur créé ou récupéré
            } else {
                System.out.println("Impossible de récupérer ou créer l'auteur.");
                return null;
            }
        } catch (Exception e) {
            System.out.println("Erreur lors du traitement de l'auteur : " + e.getMessage());
            return null;
        }
    }
}
