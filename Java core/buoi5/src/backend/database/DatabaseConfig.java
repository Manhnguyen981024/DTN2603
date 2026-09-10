package backend.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConfig {
    private static final String URL = "jdbc:mysql://localhost:3306/test";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "123456";

    private DatabaseConfig() {}

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }

    public static void main(String[] args) {
        try (Connection connection = DatabaseConfig.getConnection()) {
            if (connection != null) {
                System.out.println("Connection established successfully.");
            } else {
                System.out.println("Failed to establish connection.");
            }
        } catch (SQLException e) {
            System.err.println("close connection failed: " + e.getMessage());
        }
    }
}
