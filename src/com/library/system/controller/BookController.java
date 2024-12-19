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

import java.sql.Connection;
import java.util.Scanner;

public class BookController {
    private final BookService bookService;

    public BookController(Connection connection) {
        // Créez les instances nécessaires pour BookServiceImpl
        AuthorDAO authorDAO = new AuthorDAOImpl(connection);  // Créez l'instance de AuthorDAO
        BookDAO bookDAO = new BookDAOImpl(connection, authorDAO);  // Passez AuthorDAO au constructeur de BookDAOImpl
        CategoryDAO categoryDAO = new CategoryDAOImpl(connection);
        BooksCategoryDAO booksCategoryDAO = new BooksCategoryDAOImpl(connection);
        // Passez ces instances au constructeur de BookServiceImpl
        this.bookService = new BookServiceImpl(bookDAO, authorDAO, categoryDAO, booksCategoryDAO);
    }


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
