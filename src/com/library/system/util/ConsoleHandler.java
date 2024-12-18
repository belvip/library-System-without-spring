package com.library.system.util;

import com.library.system.model.Book;
import com.library.system.service.BookService;
import com.library.system.service.impl.BookServiceImpl;

import java.util.InputMismatchException;
import java.util.Scanner;

public class ConsoleHandler {
    private final Scanner scanner = new Scanner(System.in);
    private final BookService bookService;

    public ConsoleHandler() {
        this.bookService = new BookServiceImpl(); // Injection du service
    }

    public void start() {
        boolean running = true;
        while (running) {
            displayMenu();
            int choice = getChoiceInput();
            switch (choice) {
                case 1:
                    addBook();
                    break;
                case 2:
                    updateBook();
                    break;
                case 3:
                    removeBook();
                    break;
                case 4:
                    displayBooks();
                    break;
                case 5:
                    running = false;
                    System.out.println("Exiting the system...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private int getChoiceInput() {
        try {
            return scanner.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter a valid number.");
            scanner.nextLine(); // Clear buffer
            return -1; // Retourner une valeur invalide pour forcer le menu à réafficher
        }
    }

    /* ^^^^^^^^^^^^^^^^^^^^^^^ Menu d'affichage ^^^^^^^^^^^^^^^^^^^^^^^ */
    private void displayMenu() {
        System.out.println("\n================ Library Management System ==================");
        System.out.println("Choisissez une option en entrant le nombre correspondant:");
        System.out.println("--------------------------------------------------------------");
        System.out.printf("%-5s %-40s\n", "1", "Ajouter un livre");
        System.out.printf("%-5s %-40s\n", "2", "Mettre à jour un livre");
        System.out.printf("%-5s %-40s\n", "3", "Supprimer un livre");
        System.out.printf("%-5s %-40s\n", "4", "Afficher tous les livres");
        System.out.printf("%-5s %-40s\n", "5", "Quitter le système");
        System.out.println("--------------------------------------------------------------");
        System.out.print("Entrez votre choix: ");
    }

    /* ^^^^^^^^^^^^^^^^^^^^^^^ Ajouter un livre ^^^^^^^^^^^^^^^^^^^^^^^ */
    private void addBook() {
        scanner.nextLine(); // Consommer la ligne restante
        System.out.println("\n=== Ajouter un nouveau livre ===");
        System.out.print("Titre du livre : ");
        String title = scanner.nextLine();

        System.out.print("Nombre de copies : ");
        int numberOfCopies = scanner.nextInt();

        // Créer un nouvel objet Book
        Book newBook = new Book();
        newBook.setTitle(title);
        newBook.setNumberOfCopies(numberOfCopies);

        // Appeler la méthode avec l'objet Book
        bookService.addBook(newBook);
        System.out.println("Livre ajouté avec succès !");
    }


    /* ^^^^^^^^^^^^^^^^^^^^^^^ Mettre à jour un livre ^^^^^^^^^^^^^^^^^^^^^^^ */
    private void updateBook() {
        scanner.nextLine();  // Consommer la ligne restante
        System.out.println("\n=== Mettre à jour un livre ===");
        System.out.print("ID du livre à mettre à jour : ");
        int id = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Nouveau titre du livre : ");
        String newTitle = scanner.nextLine();

        System.out.print("Nouveau nombre de copies : ");
        int newCopies = scanner.nextInt();

        // Créer un objet Book avec les nouvelles informations
        Book updatedBook = new Book();
        updatedBook.setId(id);
        updatedBook.setTitle(newTitle);
        updatedBook.setNumberOfCopies(newCopies);

        // Appeler la méthode avec l'objet Book
        bookService.updateBook(updatedBook);
        System.out.println("Livre mis à jour avec succès !");
    }

    /* ^^^^^^^^^^^^^^^^^^^^^^^ Supprimer un livre ^^^^^^^^^^^^^^^^^^^^^^^ */
    private void removeBook() {
        System.out.println("\n=== Supprimer un livre ===");
        System.out.print("ID du livre à supprimer : ");
        int id = scanner.nextInt();

        bookService.removeBook(id);
        System.out.println("Livre supprimé avec succès !");
    }

    /* ^^^^^^^^^^^^^^^^^^^^^^^ Afficher tous les livres ^^^^^^^^^^^^^^^^^^^^^^^ */
    private void displayBooks() {
        System.out.println("\n=== Liste des livres disponibles ===");
        bookService.displayAvailableBooks(); // Appel de la méthode correcte
    }

}
