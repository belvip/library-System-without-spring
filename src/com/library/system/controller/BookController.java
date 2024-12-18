package com.library.system.controller;

import com.library.system.service.BookService;
import com.library.system.service.impl.BookServiceImpl;
import com.library.system.model.Book;

import java.util.Scanner;

public class BookController {
    private final BookService bookService;

    // Constructeur
    public BookController() {
        this.bookService = new BookServiceImpl();
    }

    // Méthode pour ajouter un livre
    public void addBook() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== Ajouter un Livre ===");

        System.out.print("Entrez le titre du livre : ");
        String title = scanner.nextLine();

        System.out.print("Entrez le nombre de copies : ");
        int numberOfCopies = scanner.nextInt();

        // Créer un objet Book
        Book book = new Book();
        book.setTitle(title);
        book.setNumberOfCopies(numberOfCopies);

        // Appel du service pour sauvegarder le livre
        boolean success = bookService.addBook(book);

        if (success) {
            System.out.println("Livre ajouté avec succès !");
        } else {
            System.out.println("Erreur lors de l'ajout du livre.");
        }
    }
}
