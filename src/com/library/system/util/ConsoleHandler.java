package com.library.system.util;

import com.library.system.dao.BookDAO;
import com.library.system.dao.impl.BookDAOImpl;  // Implémentation BookDAO
import com.library.system.dao.AuthorDAO;
import com.library.system.dao.impl.AuthorDAOImpl;  // Assurez-vous d'importer AuthorDAOImpl
import com.library.system.dao.CategoryDAO;
import com.library.system.dao.impl.CategoryDAOImpl;  // Implémentation CategoryDAO
import com.library.system.dao.BooksCategoryDAO;
import com.library.system.dao.impl.BooksCategoryDAOImpl;  // Implémentation BooksCategoryDAO
import com.library.system.model.Author;
import com.library.system.model.Book;
import com.library.system.model.Category;
import com.library.system.service.impl.BookServiceImpl;

import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.Set;

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

        // Saisie du titre du livre avec validation
        String title = "";
        while (title.isEmpty()) {
            System.out.print("Titre du livre : ");
            title = scanner.nextLine().trim();
            if (title.isEmpty()) {
                System.out.println("Le titre ne peut pas être vide. Essayez à nouveau.");
            }
        }

        // Saisie du nombre de copies avec validation
        int numberOfCopies = -1;
        while (numberOfCopies <= 0) {
            System.out.print("Nombre de copies : ");
            try {
                numberOfCopies = Integer.parseInt(scanner.nextLine().trim());
                if (numberOfCopies <= 0) {
                    System.out.println("Le nombre de copies doit être un nombre positif. Essayez à nouveau.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Veuillez entrer un nombre valide.");
            }
        }

        // Saisie des auteurs avec validation
        System.out.print("Entrez les auteurs (prénom nom, email séparés par une virgule) : ");
        String authorsInput = scanner.nextLine();
        Set<Author> authorSet = parseAuthors(authorsInput);

        if (authorSet.isEmpty()) {
            System.out.println("Le livre doit avoir au moins un auteur.");
            return; // Arrêter l'ajout du livre si aucun auteur n'est valide
        }

        // Saisie des catégories avec validation
        System.out.print("Entrez la catégorie (une seule, séparée par une virgule) : ");
        String categoriesInput = scanner.nextLine();
        Set<Category> categorySet = parseCategories(categoriesInput);

        if (categorySet.isEmpty()) {
            System.out.println("Le livre doit avoir au moins une catégorie.");
            return; // Arrêter l'ajout du livre si aucune catégorie n'est valide
        }

        // Créer le livre sans spécifier l'ID
        Book newBook = new Book(title, numberOfCopies);
        newBook.setAuthors(authorSet);  // Associer les auteurs
        newBook.setCategories(categorySet);  // Associer la catégorie

        // Conversion des auteurs et catégories en tableaux de chaînes
        String[] authorsArray = new String[authorSet.size()];
        int index = 0;
        for (Author author : authorSet) {
            authorsArray[index++] = author.getFirstName() + " " + author.getLastName();
        }

        String[] categoriesArray = new String[categorySet.size()];
        index = 0;
        for (Category category : categorySet) {
            categoriesArray[index++] = category.getName();
        }

        // Ajouter le livre avec ses relations dans le service (ID géré par la base de données)
        boolean success = bookService.addBookWithRelations(newBook, authorsArray, categoriesArray);

        if (success) {
            System.out.println("Livre ajouté avec succès !");
        } else {
            System.out.println("Erreur lors de l'ajout du livre.");
        }
    }

    // Méthode pour parser les auteurs avec validation de l'email
    private Set<Author> parseAuthors(String authorsInput) {
        Set<Author> authorSet = new HashSet<>();
        String[] authors = authorsInput.split(",");

        for (String authorInput : authors) {
            String[] authorDetails = authorInput.trim().split(" ");  // Séparer prénom et nom
            if (authorDetails.length == 2) {
                String firstName = authorDetails[0];
                String lastName = authorDetails[1];

                // Demander l'email pour chaque auteur avec validation
                String email = "";
                while (!isValidEmail(email)) {
                    System.out.print("Entrez l'email de " + firstName + " " + lastName + ": ");
                    email = scanner.nextLine().trim();
                    if (!isValidEmail(email)) {
                        System.out.println("L'email " + email + " n'est pas valide. Essayez à nouveau.");
                    }
                }

                // Créer un nouvel objet Author
                Author author = new Author();
                author.setFirstName(firstName);
                author.setLastName(lastName);
                author.setEmail(email);

                authorSet.add(author);  // Ajouter à l'ensemble des auteurs
            } else {
                System.out.println("Format d'auteur incorrect pour: " + authorInput);
            }
        }
        return authorSet;
    }

    // Méthode pour valider un email
    private boolean isValidEmail(String email) {
        // Utilisation d'une expression régulière simple pour valider un format d'email
        return email.matches("^[A-Za-z0-9+_.-]+@(.+)$");
    }

    // Méthode pour parser les catégories (prendre seulement la première catégorie)
    private Set<Category> parseCategories(String categoriesInput) {
        Set<Category> categorySet = new HashSet<>();
        String[] categories = categoriesInput.split(",");

        if (categories.length > 0) {
            Category category = new Category();
            category.setName(categories[0].trim());  // Prendre seulement la première catégorie
            categorySet.add(category);
        }
        return categorySet;
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
