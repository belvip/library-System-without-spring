import com.library.system.util.DatabaseConnection;

import java.sql.Connection;

public class Main {
    public static void main(String[] args) {

        try (Connection connection = DatabaseConnection.getConnection()) {
            if (connection != null) {
                System.out.println("Connexion réussie à la base de données PostgreSQL !");
            } else {
                System.out.println("Connexion échouée !");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}