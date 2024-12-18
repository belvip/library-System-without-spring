package com.library.system.service;

import com.library.system.model.Book;
import java.util.List;

public interface BookService {
    boolean addBook(Book book);
    void updateBook(Book book);
    void removeBook(int bookId);
    List<Book> searchByTitle(String title);
    List<Book> searchByCategory(String category);
    List<Book> searchByAuthor(String authorName);
    List<Book> searchByKeywords(String keywords);
    void displayAvailableBooks(); // Afficher les livres disponibles
    boolean isAvailable(Book book); // Vérifier la disponibilité d'un livre


}
