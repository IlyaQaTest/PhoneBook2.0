package com.phonebook.mydb;

import com.phonebook.mydb.config.DatabaseConfig;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * DBHelper provides helper methods for interacting with the MySQL database.
 * Supports CRUD operations for the contacts table.
 */
public class DBHelper {

    /**
     * Checks if a contact with the given email exists in the database.
     *
     * @param email contact email
     * @return true if the contact exists, false otherwise
     */
    public static boolean isContactExists(String email) {
        String query = "SELECT 1 FROM contacts WHERE email = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            return rs.next();

        } catch (SQLException e) {
            throw new RuntimeException("Database query failed", e);
        }
    }

    /**
     * Inserts a new contact into the database.
     *
     * @param name        contact name
     * @param lastName    contact last name
     * @param email       contact email
     * @param phone       contact phone number
     * @param address     contact address
     * @param description contact description
     */
    public static void insertContact(String name, String lastName, String email,
                                     String phone, String address, String description) {

        if (isContactExists(email)) {
            deleteContact(email);
        }

        String query = "INSERT INTO contacts (name, lastName, email, phone, address, description) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, name);
            stmt.setString(2, lastName);
            stmt.setString(3, email);
            stmt.setString(4, phone);
            stmt.setString(5, address);
            stmt.setString(6, description);

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Database insert failed", e);
        }
    }

    /**
     * Updates an existing contact by email.
     *
     * @param email       contact email
     * @param phone       new phone number
     * @param address     new address
     * @param description new description
     */
    public static void updateContact(String email, String phone, String address, String description) {

        String query = "UPDATE contacts SET phone = ?, address = ?, description = ? WHERE email = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, phone);
            stmt.setString(2, address);
            stmt.setString(3, description);
            stmt.setString(4, email);

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Database update failed", e);
        }
    }

    /**
     * Deletes a contact by email.
     *
     * @param email contact email
     */
    public static void deleteContact(String email) {

        String query = "DELETE FROM contacts WHERE email = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, email);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Database delete failed", e);
        }
    }

    /**
     * Retrieves all contact emails from the database.
     *
     * @return list of contact emails
     */
    public static List<String> getAllContacts() {

        List<String> contacts = new ArrayList<>();
        String query = "SELECT email FROM contacts";

        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                contacts.add(rs.getString("email"));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Database select failed", e);
        }

        return contacts;
    }
}
