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
     * @param tokenDto the authorization token
     * @return the server response containing the contact list
     */
    public static Response requestGetAllUserContacts(String tokenDto) {
        return given()
                .baseUri(BASE_URL)
                .header(AUTH, tokenDto)
                .contentType(ContentType.JSON)
                .get(GET_ALL_CONTACTS_URL)
                .thenReturn();
    }

    /**
     * Adds a new contact for the authenticated user.
     *
     * @param contact  the contact object to be added
     * @param tokenDto the authorization token
     * @return the server response
     */
    public static Response requestAddNewContact(Contact contact, String tokenDto) {
        return given()
                .baseUri(BASE_URL)
                .header(AUTH, tokenDto)
                .contentType(ContentType.JSON)
                .body(contact)
                .post(ADD_NEW_CONTACT_URL)
                .thenReturn();
    }
}