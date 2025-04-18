package org.example.model;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseHelper {
    private static String DB_URL = "jdbc:sqlite:events.db";

    public static <Connection> Connection connect() throws SQLException {
        return (Connection) DriverManager.getConnection(DB_URL);
    }

    public static void createTable() {
        String sql = """
            CREATE TABLE IF NOT EXISTS events (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                name TEXT NOT NULL,
                start TEXT NOT NULL,
                end TEXT NOT NULL
            );
        """;

        try (Connection connection = connect();
             Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static String overrideDbUrl() {
        return DB_URL;
    }
}