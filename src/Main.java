
import com.library.system.controller.BookController;
import com.library.system.controller.CategoryController;
import com.library.system.dao.CategoryDAO;
import com.library.system.dao.impl.CategoryDAOImpl;
import com.library.system.service.CategoryService;
import com.library.system.service.impl.CategoryServiceImpl;
import com.library.system.util.ConsoleHandler;
import com.library.system.util.DatabaseConnection;
import com.library.system.util.DatabaseTableCreator;

import java.sql.Connection;

public class Main {
    public static void main(String[] args) {
        System.out.println("Bienvenue dans le système de gestion de bibliothèque !");

        try (Connection connection = DatabaseConnection.getConnection()) {
            if (connection != null) {
                System.out.println("Connexion réussie à la base de données PostgreSQL !");
            } else {
                System.out.println("Connexion échouée !");
                return; // Arrêter le programme si la connexion échoue
            }

            // Créer les tables nécessaires dans la base de données
            DatabaseTableCreator.createTables(connection);

            // Initialisation de ConsoleHandler en passant la connexion
            ConsoleHandler consoleHandler = new ConsoleHandler(connection);

            BookController bookController = new BookController(connection);

            // Initialiser les DAO, services et contrôleurs
            CategoryDAO categoryDAO = new CategoryDAOImpl(connection);
            CategoryService categoryService = new CategoryServiceImpl(categoryDAO);
            CategoryController categoryController = new CategoryController(categoryService);

            // Exemples d'utilisation des contrôleurs
            categoryController.addOrGetCategory();
            categoryController.listAllCategories();

            // Démarrer l'interaction avec l'utilisateur via ConsoleHandler
            consoleHandler.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
