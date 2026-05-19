package com.phonebook.api.client;

import com.phonebook.api.dto.TokenDto;
import com.phonebook.model.User;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import java.io.IOException;

import static java.lang.System.getProperty;

/**
 * Provides a default login method for API tests.
 * Implements BaseApi to use shared configuration and constants.
 */
public interface ILogin extends BaseApi {

    /**
     * Performs user login and retrieves an authorization token.
     *
     * @return TokenDto containing the authentication token
     */
    default TokenDto login_get_token() {
        User user = new User(
                getProperty("base.properties", "login"),
                getProperty("base.properties", "password")
        );

        RequestBody requestBody = RequestBody.create(GSON.toJson(user), JSON);
        Request request = new Request.Builder()
                .url(BASE_URL + LOGIN_URL)
                .post(requestBody)
                .build();

        // Using try-with-resources to automatically close the Response
        try (Response response = OK_HTTP_CLIENT.newCall(request).execute()) {
            if (response.body() == null) {
                throw new RuntimeException("Response body is null");
            }
            return GSON.fromJson(response.body().string(), TokenDto.class);
        } catch (IOException e) {
            throw new RuntimeException("Error during API login", e);
        }
    }
}