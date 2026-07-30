package com.phonebook.mydb.client;

import com.phonebook.mydb.config.ApiConfigBD;
import com.phonebook.mydb.model.ContactDb;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static com.phonebook.mydb.config.ApiConfigBD.*;

/**
 * Handles contact-related API requests for the database module.
 * Uses ApiConfigBD for DB-specific configuration and base URL.
 */
public class ContactDbController {

    /**
     * Retrieves all contacts from the database for the authenticated user.
     *
     * @param token the authorization token
     * @return the server response containing the contact list
     */
    public static Response requestGetAllUserContacts(String token) {
        return given()
                .baseUri(BASE_DB_URL)
                .header(AUTH_DB, token)
                .contentType(ContentType.JSON)
                .get(GET_ALL_CONTACTS_DB_URL)
                .thenReturn();
    }

    /**
     * Adds a new contact directly to the database.
     *
     * @param contactDb the contact object to be added
     * @param token     the authorization token
     * @return the server response
     */
    public static Response requestAddNewContact(ContactDb contactDb, String token) {
        return given()
                .baseUri(BASE_DB_URL)
                .header(AUTH_DB, token)
                .contentType(ContentType.JSON)
                .body(contactDb)
                .post(ADD_CONTACT_DB_URL)
                .thenReturn();
    }

    /**
     * Deletes a contact by ID from the database.
     *
     * @param id    the contact ID to delete
     * @param token the authorization token
     * @return the server response
     */
    public static Response requestDeleteContact(String id, String token) {
        return given()
                .baseUri(BASE_DB_URL)
                .header(AUTH_DB, token)
                .delete(DELETE_CONTACT_DB_URL + id)
                .thenReturn();
    }
}
