import com.library.system.controller.BookController;
import com.library.system.util.ConsoleHandler;
import com.library.system.util.DatabaseConnection;
import com.library.system.util.DatabaseTableCreator;

import java.sql.Connection;

public class Main {
    public static void main(String[] args) {
        System.out.println("Bienvenue dans le système de gestion de bibliothèque !");

        // Initialisation de ConsoleHandler
        ConsoleHandler consoleHandler = new ConsoleHandler();



        try (Connection connection = DatabaseConnection.getConnection()) {
            if (connection != null) {
                //System.out.println("Connexion réussie à la base de données PostgreSQL !");
            } else {
                System.out.println("Connexion échouée !");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        DatabaseTableCreator.createTables();

        //Démarrer l'interaction avec l'utilisateur via ConsoleHandler
        consoleHandler.start();



    }


}