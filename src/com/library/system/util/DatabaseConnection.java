package com.library.system.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    // URL de la base de données PostgreSQL
    private static final String URL = "jdbc:postgresql://localhost:5432/library_system";
    private static final String USER = "postgres";
    private static final String PASSWORD = "belvi";

    // Méthode pour établir la connexion avec la base de données
    public static Connection getConnection() throws SQLException {
        try {
            // Charger le driver PostgreSQL (si nécessaire)
            Class.forName("org.postgresql.Driver");

            // Retourner la connexion à la base de données
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            throw new SQLException("PostgreSQL JDBC Driver is missing", e);
        }
    }
}
