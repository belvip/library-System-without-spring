package com.library.system.util;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.SQLException;

public class DatabaseTableCreator {

    public static void createTables(Connection connection) {
        try (Statement statement = connection.createStatement()) {

            // Création de la table Book
            String createBookTable = "CREATE TABLE IF NOT EXISTS Book (" +
                    "book_id SERIAL PRIMARY KEY, " +
                    "book_title VARCHAR(100) NOT NULL, " +
                    "number_of_copies INT NOT NULL" +
                    ");";
            statement.executeUpdate(createBookTable);

            // Création de la table Member
            String createMemberTable = "CREATE TABLE IF NOT EXISTS Member (" +
                    "member_id SERIAL PRIMARY KEY, " +
                    "first_name VARCHAR(100) NOT NULL, " +
                    "last_name VARCHAR(100) NOT NULL, " +
                    "email VARCHAR(100) UNIQUE NOT NULL, " +
                    "adhesion_date DATE NOT NULL" +
                    ");";
            statement.executeUpdate(createMemberTable);

            // Création de la table Author
            String createAuthorTable = "CREATE TABLE IF NOT EXISTS Author (" +
                    "author_id SERIAL PRIMARY KEY, " +
                    "first_name VARCHAR(100) NOT NULL, " +
                    "last_name VARCHAR(100) NOT NULL, " +
                    "email VARCHAR(100) UNIQUE NOT NULL" +
                    ");";
            statement.executeUpdate(createAuthorTable);

            // Création de la table Category
            String createCategoryTable = "CREATE TABLE IF NOT EXISTS Category (" +
                    "category_id SERIAL PRIMARY KEY, " +
                    "category_name VARCHAR(100) NOT NULL" +
                    ");";
            statement.executeUpdate(createCategoryTable);

            // Création de la table Loan
            String createLoanTable = "CREATE TABLE IF NOT EXISTS Loan (" +
                    "loan_id SERIAL PRIMARY KEY, " +
                    "loan_date DATE NOT NULL, " +
                    "due_date DATE NOT NULL, " +
                    "return_date DATE, " +
                    "is_returned BOOLEAN NOT NULL, " +
                    "member_id INT NOT NULL REFERENCES Member(member_id)" +
                    ");";
            statement.executeUpdate(createLoanTable);

            // Création de la table Book_Author (jointure)
            String createBookAuthorTable = "CREATE TABLE IF NOT EXISTS Book_Author (" +
                    "book_id INT NOT NULL REFERENCES Book(book_id), " +
                    "author_id INT NOT NULL REFERENCES Author(author_id), " +
                    "PRIMARY KEY (book_id, author_id)" +
                    ");";
            statement.executeUpdate(createBookAuthorTable);

            // Création de la table Books_Category (jointure)
            String createBooksCategoryTable = "CREATE TABLE IF NOT EXISTS Books_Category (" +
                    "book_id INT NOT NULL REFERENCES Book(book_id), " +
                    "category_id INT NOT NULL REFERENCES Category(category_id), " +
                    "PRIMARY KEY (book_id, category_id)" +
                    ");";
            statement.executeUpdate(createBooksCategoryTable);

            // Création de la table Books_Loans (jointure)
            String createBooksLoansTable = "CREATE TABLE IF NOT EXISTS Books_Loans (" +
                    "book_id INT NOT NULL REFERENCES Book(book_id), " +
                    "member_id INT NOT NULL REFERENCES Member(member_id), " +
                    "PRIMARY KEY (book_id, member_id)" +
                    ");";
            statement.executeUpdate(createBooksLoansTable);

            // Création de la table HandleDelayAndPenalties
            String createHandleDelayAndPenaltiesTable = "CREATE TABLE IF NOT EXISTS HandleDelayAndPenalties (" +
                    "penalty_rate_per_day DOUBLE PRECISION NOT NULL, " +
                    "PRIMARY KEY (penalty_rate_per_day)" +
                    ");";
            statement.executeUpdate(createHandleDelayAndPenaltiesTable);

            System.out.println("Tables created successfully.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
