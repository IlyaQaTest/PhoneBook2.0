package com.phonebook.api;

import com.phonebook.model.User;
import com.phonebook.utils.api.BaseApi;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static io.restassured.RestAssured.given;

public class AuthenticationController implements BaseApi {

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);

    /**
     * Sends registration or login request with User payload.
     *
     * @param user User object containing email/password
     * @param endpoint API endpoint (e.g. "/login" or "/register")
     * @return Response from server
     */
    public static Response requestRegLogin(User user, String endpoint) {

        logger.info("Sending POST request to endpoint: {}", endpoint);
        logger.debug("Request body: {}", user);

        Response response = given()
                .baseUri(BASE_URL)
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .post(endpoint)
                .thenReturn();

        logger.info("Received response with status code: {}", response.getStatusCode());
        logger.debug("Response body: {}", response.asString());

        return response;
    }
}