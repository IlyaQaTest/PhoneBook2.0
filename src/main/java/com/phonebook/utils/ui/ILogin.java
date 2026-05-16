package com.phonebook.utils.ui;

import com.phonebook.model.TokenDto;
import com.phonebook.model.User;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import static com.phonebook.utils.ui.PropertiesReader.getProperty;

/**
 * Provides a default method for performing API login and retrieving an authentication token.
 * Implements BaseApi to reuse shared constants and HTTP client configuration.
 */
public interface ILogin extends BaseApi {

    Logger logger = LoggerFactory.getLogger(ILogin.class);

    /**
     * Performs login using credentials from the properties file and returns a TokenDto object.
     *
     * @return TokenDto containing the authentication token.
     */
    default TokenDto loginGetToken() {
        User user = new User(
                getProperty("base.properties", "login"),
                getProperty("base.properties", "password")
        );

        RequestBody requestBody = RequestBody.create(GSON.toJson(user), JSON);
        Request request = new Request.Builder()
                .url(BASE_URL + LOGIN_URL)
                .post(requestBody)
                .build();

        try (Response response = OK_HTTP_CLIENT.newCall(request).execute()) {
            if (response.body() == null) {
                logger.error("Response body is null during login.");
                throw new RuntimeException("Response body is null");
            }

            TokenDto token = GSON.fromJson(response.body().string(), TokenDto.class);
            logger.info("Login successful. Token received for user: {}", user.getUsername());
            return token;

        } catch (IOException e) {
            logger.error("Error during API login: {}", e.getMessage(), e);
            throw new RuntimeException("Error during API login", e);
        }
    }
}