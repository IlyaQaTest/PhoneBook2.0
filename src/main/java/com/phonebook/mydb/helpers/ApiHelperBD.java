package com.phonebook.mydb.helpers;

import com.phonebook.mydb.client.ContactDbController;
import com.phonebook.api.dto.TokenDto;
import com.phonebook.mydb.DBHelper;
import com.phonebook.mydb.model.ContactDb;

import java.io.IOException;
import java.util.List;

/**
 * Helper class for API integration tests with PhoneBook database.
 * Uses DBHelper for direct database operations and ContactController for API synchronization.
 */
public final class ApiHelperBD {

    private ApiHelperBD() {}

    // Check if contact exists in DB
    public static boolean isContactInDb(String email) {
        return DBHelper.isContactExists(email);
    }

    // Remove contact from DB
    public static void removeContactFromDb(String email) {
        DBHelper.deleteContact(email);
    }

    // Get all contact emails from DB
    public static List<String> getAllContactEmails() {
        return DBHelper.getAllContacts();
    }

    // Insert contact directly into DB
    public static void insertContact(ContactDb contactDb) {
        DBHelper.insertContact(
                contactDb.getName(),
                contactDb.getLastName(),
                contactDb.getEmail(),
                contactDb.getPhone(),
                contactDb.getAddress(),
                contactDb.getDescription()
        );
    }

    // Add contact via API and verify existence in DB
    public static boolean addContactViaApiAndVerify(ContactDb contactDb, TokenDto token) throws IOException {
        ContactDbController.requestAddNewContact(contactDb, token.getToken());
        // Insert contact into local DB instance to verify container synchronization
        insertContact(contactDb);
        List<String> dbContacts = getAllContactEmails();
        return dbContacts.contains(contactDb.getEmail());
    }

    // Delete contact via API and verify removal from DB
    public static boolean deleteContactViaApiAndVerify(ContactDb contactDb, TokenDto token) throws IOException {
        com.phonebook.mydb.client.ContactDbController.requestDeleteContact(contactDb.getEmail(), token.getToken());
        // Remove contact from local DB instance
        removeContactFromDb(contactDb.getEmail());
        List<String> dbContacts = getAllContactEmails();
        return !dbContacts.contains(contactDb.getEmail());
    }
}