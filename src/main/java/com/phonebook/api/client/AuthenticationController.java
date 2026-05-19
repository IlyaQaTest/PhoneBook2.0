package com.phonebook.api.client;

import com.phonebook.model.User;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

/**
 * Handles user authentication requests (registration and login).
 * Implements BaseApi to use shared configuration and base URL.
 */
public class AuthenticationController implements BaseApi {

    /**
     * Sends a registration or login request with the given user data.
     *
     * @param user the user object containing credentials
     * @param url  the endpoint path (e.g., "/api/register" or "/api/login")
     * @return the server response
     */
    public static Response requestRegLogin(User user, String url) {
        return given()
                .baseUri(BASE_URL)
                .contentType(ContentType.JSON)
                .body(user)
                .post(url)
                .thenReturn();
    }
}