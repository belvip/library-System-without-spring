package com.library.system.util;

import com.library.system.dao.BookDAO;
import com.library.system.dao.impl.BookDAOImpl;  // Implémentation BookDAO
import com.library.system.dao.AuthorDAO;
import com.library.system.dao.impl.AuthorDAOImpl;  // Assurez-vous d'importer AuthorDAOImpl
import com.library.system.dao.CategoryDAO;
import com.library.system.dao.impl.CategoryDAOImpl;  // Implémentation CategoryDAO
import com.library.system.dao.BooksCategoryDAO;
import com.library.system.dao.impl.BooksCategoryDAOImpl;  // Implémentation BooksCategoryDAO
import com.library.system.model.Book;
import com.library.system.service.impl.BookServiceImpl;

import java.util.InputMismatchException;
import java.util.Scanner;

public class ConsoleHandler {
    private final Scanner scanner = new Scanner(System.in);
    private final BookServiceImpl bookService;  // Gardez uniquement cette ligne

    public ConsoleHandler() {
        // Créez les instances nécessaires pour BookServiceImpl
        BookDAO bookDAO = new BookDAOImpl();  // Utilisez BookDAOImpl
        AuthorDAO authorDAO = new AuthorDAOImpl();  // Utilisez AuthorDAOImpl
        CategoryDAO categoryDAO = new CategoryDAOImpl();  // Utilisez CategoryDAOImpl
        BooksCategoryDAO booksCategoryDAO = new BooksCategoryDAOImpl();  // Utilisez BooksCategoryDAOImpl

        // Passez ces instances au constructeur de BookServiceImpl
        this.bookService = new BookServiceImpl(bookDAO, authorDAO, categoryDAO, booksCategoryDAO);
    }  // <-- Ajoutez cette accolade fermante pour le constructeur

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
        scanner.nextLine(); // Consommer la ligne restante

        System.out.print("Entrez les auteurs (séparés par une virgule) : ");
        String authorsInput = scanner.nextLine();
        String[] authors = authorsInput.split(",");

        System.out.print("Entrez les catégories (séparées par une virgule) : ");
        String categoriesInput = scanner.nextLine();
        String[] categories = categoriesInput.split(",");

        // Créer le livre sans spécifier l'ID
        Book newBook = new Book(title, numberOfCopies);

        // Ajouter le livre dans le service (ID sera géré par la base de données)
        boolean success = bookService.addBookWithRelations(newBook, authors, categories);

        if (success) {
            System.out.println("Livre ajouté avec succès !");
        } else {
            System.out.println("Erreur lors de l'ajout du livre.");
        }
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

        // Créer un objet Book avec le constructeur existant
        Book updatedBook = new Book(newTitle, newCopies);  // Utilisation du constructeur avec title et numberOfCopies
        updatedBook.setId(id);  // Définir l'ID via le setter

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
