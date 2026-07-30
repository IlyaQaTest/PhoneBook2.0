package com.phonebook.tests.api;

import io.qameta.allure.Issue;
import io.qameta.allure.Description;
import com.phonebook.api.client.BaseApi;
import com.phonebook.api.client.ILogin;
import com.phonebook.model.ContactsList;
import com.phonebook.model.ErrorMessage;
import com.phonebook.api.dto.TokenDto;
import okhttp3.Request;
import okhttp3.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import java.io.IOException;

/**
 * Unified test class for retrieving all user contacts via API.
 * Includes positive and negative scenarios with reusable helper methods.
 */
public class GetAllContactsApiTests implements BaseApi, ILogin {

    private TokenDto token;

    @BeforeClass
    public void login() {
        token = login_get_token();
    }

    @Test
    public void getAllContactsPositive_ApiTest() {
        Request request = new Request.Builder()
                .url(BASE_URL + GET_ALL_CONTACTS_URL)
                .addHeader(AUTH, token.getToken())
                .get()
                .build();

        try (Response response = OK_HTTP_CLIENT.newCall(request).execute()) {
            Assert.assertEquals(response.code(), 200, "Expected status code 200");

            if (response.body() != null) {
                String body = response.body().string();
                ContactsList contactsList = GSON.fromJson(body, ContactsList.class);

                Assert.assertNotNull(contactsList, "Contacts list should not be null");
                Assert.assertTrue(contactsList.getContacts() != null && !contactsList.getContacts().isEmpty(),
                        "Contacts list should not be empty");
            } else {
                Assert.fail("Response body is empty");
            }
        } catch (IOException e) {
            throw new RuntimeException("Error executing GET request: " + e.getMessage(), e);
        }
    }

    @Test
    @Issue("BUG-CONTACTS-TOKEN")
    @Description("Server returns unclear error message for invalid token format")
    public void getAllContactsNegative_WrongToken_ApiTest() {
        SoftAssert softAssert = new SoftAssert();

        Request request = new Request.Builder()
                .url(BASE_URL + GET_ALL_CONTACTS_URL)
                .addHeader(AUTH, "invalid_token_format")
                .get()
                .build();

        try (Response response = OK_HTTP_CLIENT.newCall(request).execute()) {
            softAssert.assertEquals(response.code(), 401, "Expected 401 Unauthorized");

            if (response.body() != null) {
                String body = response.body().string();
                ErrorMessage errorMessage = GSON.fromJson(body, ErrorMessage.class);

                softAssert.assertEquals(errorMessage.getError(), "Unauthorized", "Error field mismatch");
                softAssert.assertTrue(String.valueOf(errorMessage.getMessage())
                                .contains("strings must contain exactly 2 period characters."),
                        "Unexpected error message: " + errorMessage.getMessage());
            } else {
                softAssert.fail("Response body is empty, expected error message");
            }

            softAssert.assertAll();
        } catch (IOException e) {
            throw new RuntimeException("Error executing negative test: " + e.getMessage(), e);
        }
    }
}