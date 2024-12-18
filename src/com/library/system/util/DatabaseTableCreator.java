package com.library.system.util;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.SQLException;

public class DatabaseTableCreator {

    public static void createTables() {
        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement()) {

            // Création de la table Book
            String createBookTable = "CREATE TABLE IF NOT EXISTS Book (" +
                    "id SERIAL PRIMARY KEY, " +
                    "title VARCHAR(100) NOT NULL, " +
                    "numberOfCopies INT NOT NULL" +
                    ");";
            statement.executeUpdate(createBookTable);

            // Création de la table Member
            String createMemberTable = "CREATE TABLE IF NOT EXISTS Member (" +
                    "id SERIAL PRIMARY KEY, " +
                    "firstName VARCHAR(100) NOT NULL, " +
                    "lastName VARCHAR(100) NOT NULL, " +
                    "email VARCHAR(100) UNIQUE NOT NULL, " +
                    "adhesionDate DATE NOT NULL" +
                    ");";
            statement.executeUpdate(createMemberTable);

            // Création de la table Author
            String createAuthorTable = "CREATE TABLE IF NOT EXISTS Author (" +
                    "id SERIAL PRIMARY KEY, " +
                    "firstName VARCHAR(100) NOT NULL, " +
                    "lastName VARCHAR(100) NOT NULL, " +
                    "email VARCHAR(100) UNIQUE NOT NULL" +
                    ");";
            statement.executeUpdate(createAuthorTable);

            // Création de la table Category
            String createCategoryTable = "CREATE TABLE IF NOT EXISTS Category (" +
                    "id SERIAL PRIMARY KEY, " +
                    "name VARCHAR(100) NOT NULL" +
                    ");";
            statement.executeUpdate(createCategoryTable);

            // Création de la table Loan
            String createLoanTable = "CREATE TABLE IF NOT EXISTS Loan (" +
                    "id SERIAL PRIMARY KEY, " +
                    "loanDate DATE NOT NULL, " +
                    "dueDate DATE NOT NULL, " +
                    "returnDate DATE, " +
                    "isReturned BOOLEAN NOT NULL, " +
                    "memberId INT NOT NULL REFERENCES Member(id)" +
                    ");";
            statement.executeUpdate(createLoanTable);

            // Création de la table Book_Author (jointure)
            String createBookAuthorTable = "CREATE TABLE IF NOT EXISTS book_author (" +
                    "booid INT NOT NULL REFERENCES book(id), " +
                    "authorid INT NOT NULL REFERENCES author(id), " +
                    "PRIMARY KEY (booid, authorid)" +
                    ");";

            statement.executeUpdate(createBookAuthorTable);

            // Création de la table Books_Category (jointure)
            String createBooksCategoryTable = "CREATE TABLE IF NOT EXISTS Books_Category (" +
                    "bookId INT NOT NULL REFERENCES Book(id), " +
                    "categoryId INT NOT NULL REFERENCES Category(id), " +
                    "PRIMARY KEY (bookId, categoryId)" +
                    ");";
            statement.executeUpdate(createBooksCategoryTable);

            // Création de la table Books_Loans (jointure)
            String createBooksLoansTable = "CREATE TABLE IF NOT EXISTS Books_Loans (" +
                    "bookId INT NOT NULL REFERENCES Book(id), " +
                    "memberId INT NOT NULL REFERENCES Member(id), " +
                    "PRIMARY KEY (bookId, memberId)" +
                    ");";
            statement.executeUpdate(createBooksLoansTable);

            // Création de la table HandleDelayAndPenalties
            String createHandleDelayAndPenaltiesTable = "CREATE TABLE IF NOT EXISTS HandleDelayAndPenalties (" +
                    "penaltyRatePerDay DOUBLE PRECISION NOT NULL" +
                    ");";
            statement.executeUpdate(createHandleDelayAndPenaltiesTable);

            //System.out.println("Tables created successfully.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
