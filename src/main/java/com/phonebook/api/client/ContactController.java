package com.phonebook.api.client;

import com.phonebook.model.Contact;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

/**
 * Handles contact-related API requests.
 * Implements BaseApi to use shared configuration and base URL.
 */
public class ContactController implements BaseApi {

    /**
     * Retrieves all contacts for the authenticated user.
     *
     * @param token the authorization token
     * @return the server response containing the contact list
     */
    public static Response requestGetAllUserContacts(String token) {
        return given()
                .baseUri(BASE_URL)
                .header(AUTH, token)
                .contentType(ContentType.JSON)
                .get(GET_ALL_CONTACTS_URL)
                .thenReturn();
    }

    /**
     * Adds a new contact for the authenticated user.
     *
     * @param contact the contact object to be added
     * @param token   the authorization token
     * @return the server response
     */
    public static Response requestAddNewContact(Contact contact, String token) {
        return given()
                .baseUri(BASE_URL)
                .header(AUTH, token)
                .contentType(ContentType.JSON)
                .body(contact)
                .post(ADD_NEW_CONTACT_URL)
                .thenReturn();
    }

    /**
     * Deletes a contact by email for the authenticated user.
     *
     * @param email the contact email to delete
     * @param token the authorization token
     * @return the server response
     */
    public static Response requestDeleteContact(String email, String token) {
        return given()
                .baseUri(BASE_URL)
                .header(AUTH, token)
                .queryParam("email", email)
                .delete(DELETE_CONTACT_URL)
                .thenReturn();
    }
}
