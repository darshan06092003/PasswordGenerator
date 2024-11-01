package com.example.passwordgenerator;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConfig {
    private static final String DB_URL = "jdbc:h2:./passwordsDB"; // Store database in a local file

    static {
        try {
            Connection connection = DriverManager.getConnection(DB_URL, "sa", "");
            Statement stmt = connection.createStatement();

            // Create the Passwords table if it doesn't exist
            String sql = "CREATE TABLE IF NOT EXISTS Passwords (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY," +
                    "password VARCHAR(255)," +
                    "length INT," +
                    "uppercase BOOLEAN," +
                    "digits BOOLEAN," +
                    "special BOOLEAN," +
                    "timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP)";
            stmt.execute(sql);
            stmt.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, "sa", "");
    }
}
