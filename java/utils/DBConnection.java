

package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/rentelvehicle";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "2025";

    // Load the JDBC driver when the class is loaded
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("MySQL JDBC Driver not found: " + e.getMessage());
            
        }
    }

    // Private constructor to prevent instantiation
    private DBConnection() {}

  
    public static Connection getConnection() throws SQLException {
        try {
            return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        } catch (SQLException e) {
            System.err.println("Error connecting to database: " + e.getMessage());
            throw e;
        }
    }
}
