package com.phonebook.api;

import com.phonebook.model.Contact;
import com.phonebook.utils.api.BaseApi;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static io.restassured.RestAssured.given;

public class ContactController implements BaseApi {

    private static final Logger logger = LoggerFactory.getLogger(ContactController.class);

    /**
     * Sends GET request to retrieve all contacts for the current user.
     *
     * @param token Authorization token (e.g. "Bearer abc123")
     * @return Response from server
     */

    public static Response requestGetAllUserContacts(String token) {

        logger.info("Sending GET request to fetch all user contacts");
        logger.debug("Authorization token: {}", token);

        Response response = given()
                .baseUri(BASE_URL)
                .header("Authorization", token)
                .contentType(ContentType.JSON)
                .when()
                .get(GET_ALL_CONTACTS_URL)
                .thenReturn();

        logger.info("Received response with status code: {}", response.getStatusCode());
        logger.debug("Response body: {}", response.asString());

        return response;
    }

    /**
     * Sends POST request to add a new contact.
     *
     * @param contact Contact object to add
     * @param token Authorization token
     * @return Response from server
     */
    public static Response requestAddNewContact(Contact contact, String token) {

        logger.info("Sending POST request to add new contact");
        logger.debug("Request body: {}", contact);
        logger.debug("Authorization token: {}", token);

        Response response = given()
                .baseUri(BASE_URL)
                .header("Authorization", token)
                .contentType(ContentType.JSON)
                .body(contact)
                .when()
                .post(ADD_NEW_CONTACT_URL)
                .thenReturn();

        logger.info("Received response with status code: {}", response.getStatusCode());
        logger.debug("Response body: {}", response.asString());

        return response;
    }
}