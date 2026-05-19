package com.phonebook.tests.api;

import com.phonebook.api.client.BaseApi;
import com.phonebook.api.dto.ResponseMessageDto;
import com.phonebook.api.dto.TokenDto;
import com.phonebook.model.User;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import io.qameta.allure.Issue;
import io.qameta.allure.Description;
import java.io.IOException;

import static com.phonebook.core.config.PropertiesReader.getProperty;

public class LoginApiTests implements BaseApi {

    @Test
    public void loginPositive_ApiTest() throws IOException {
        SoftAssert softAssert = new SoftAssert();

        User user = new User(
                getProperty("base.properties", "login"),
                getProperty("base.properties", "password")
        );

        try (Response response = postRequest(BASE_URL + LOGIN_URL, GSON.toJson(user), null)) {
            softAssert.assertEquals(response.code(), 200, "Expected 200 for successful login");

            TokenDto token = GSON.fromJson(response.body().string(), TokenDto.class);
            softAssert.assertNotNull(token.getToken(), "Token should not be null");
        }

        softAssert.assertAll();
    }

    @Test
    @Issue("BUG-LOGIN-MESSAGE")
    @Description("API returns 'Login or Password incorrect' instead of 'Invalid credentials'")
    public void loginNegative_InvalidPassword_ApiTest() throws IOException {
        SoftAssert softAssert = new SoftAssert();

        User user = new User(
                getProperty("base.properties", "login"),
                "wrongPassword"
        );

        try (Response response = postRequest(BASE_URL + LOGIN_URL, GSON.toJson(user), null)) {
            softAssert.assertEquals(response.code(), 401, "Expected 401 for invalid password");

            ResponseMessageDto dto = GSON.fromJson(response.body().string(), ResponseMessageDto.class);

            softAssert.assertTrue(
                    dto.containsMessage("Login or Password incorrect"),
                    "Expected 'Login or Password incorrect', got: " + dto
            );
        }

        softAssert.assertAll();
    }

    @Test
    @Issue("BUG-LOGIN-401")
    @Description("API returns 401 instead of 400 and wrong message for empty fields")
    public void loginNegative_EmptyFields_ApiTest() throws IOException {
        SoftAssert softAssert = new SoftAssert();

        User user = new User("", "");

        try (Response response = postRequest(BASE_URL + LOGIN_URL, GSON.toJson(user), null)) {
            softAssert.assertEquals(response.code(), 401, "Expected 401 for empty fields");

            ResponseMessageDto dto = GSON.fromJson(response.body().string(), ResponseMessageDto.class);

            softAssert.assertTrue(
                    dto.containsMessage("Login or Password incorrect"),
                    "Expected 'Login or Password incorrect', got: " + dto
            );
        }

        softAssert.assertAll();
    }

    private Response postRequest(String url, String json, String authToken) throws IOException {
        Request.Builder builder = new Request.Builder()
                .url(url)
                .post(RequestBody.create(json, JSON));
        if (authToken != null) builder.addHeader(AUTH, authToken);
        return OK_HTTP_CLIENT.newCall(builder.build()).execute();
    }
}