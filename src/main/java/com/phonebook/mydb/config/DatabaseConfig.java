package com.phonebook.mydb.config;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * DatabaseConfig handles loading configuration and establishing connections to the MySQL database.
 * It reads parameters from resources/properties/db.properties and initializes the JDBC driver.
 */
public class DatabaseConfig {

    private static final String CONFIG_PATH = "properties/db.properties";
    private static String url;
    private static String user;
    private static String password;

    static {
        try {
            Properties props = new Properties();

            try (InputStream input = DatabaseConfig.class.getClassLoader().getResourceAsStream(CONFIG_PATH)) {

                if (input == null) {
                    throw new RuntimeException("Configuration file not found: " + CONFIG_PATH);
                }

                props.load(input);
            }

            url = props.getProperty("db.url");
            user = props.getProperty("db.user");
            password = props.getProperty("db.password");

            if (url == null || user == null || password == null) {
                throw new RuntimeException("Invalid database configuration. Check db.url, db.user, db.password.");
            }

            Class.forName("com.mysql.cj.jdbc.Driver");

            System.out.println("Database configuration loaded successfully:");
            System.out.println("URL: " + url);
            System.out.println("User: " + user);
            System.out.println("Password: ********");

        } catch (Exception e) {
            throw new RuntimeException("Failed to load database configuration", e);
        }
    }

    /**
     * Returns a new connection to the MySQL database.
     *
     * @return Connection object
     * @throws SQLException if the connection cannot be established
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }
}
