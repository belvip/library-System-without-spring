package com.library.system.controller;

import com.library.system.dao.AuthorDAO;
import com.library.system.dao.BookDAO;
import com.library.system.dao.BooksCategoryDAO;
import com.library.system.dao.CategoryDAO;
import com.library.system.dao.impl.AuthorDAOImpl;
import com.library.system.dao.impl.BookDAOImpl;
import com.library.system.dao.impl.BooksCategoryDAOImpl;
import com.library.system.dao.impl.CategoryDAOImpl;
import com.library.system.service.BookService;
import com.library.system.service.impl.BookServiceImpl;
import com.library.system.model.Book;

import java.util.Scanner;

public class BookController {
    private final BookService bookService;

    // Utilisez une classe concrète qui implémente BookDAO
    BookDAO bookDAO = new BookDAOImpl();  // Exemple : BookDAOImpl, ou toute autre classe concrète
    AuthorDAO authorDAO = new AuthorDAOImpl();  // Assurez-vous d'avoir une implémentation concrète
    CategoryDAO categoryDAO = new CategoryDAOImpl();
    BooksCategoryDAO booksCategoryDAO = new BooksCategoryDAOImpl();

    // Constructeur
    public BookController(BookDAO bookDAO, AuthorDAO authorDAO, CategoryDAO categoryDAO, BooksCategoryDAO booksCategoryDAO) {
        this.bookService = new BookServiceImpl(bookDAO, authorDAO, categoryDAO, booksCategoryDAO);
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
