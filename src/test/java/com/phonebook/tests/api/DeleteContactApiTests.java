package com.phonebook.tests.api;

import com.phonebook.api.client.BaseApi;
import com.phonebook.api.dto.ResponseMessageDto;
import com.phonebook.api.dto.TokenDto;
import com.phonebook.model.Contact;
import com.phonebook.model.User;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import io.qameta.allure.Issue;
import io.qameta.allure.Description;
import java.io.IOException;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.phonebook.core.config.PropertiesReader.getProperty;
import static com.phonebook.model.factory.ContactFactory.positiveContact;


/**
 * Unified test class for deleting contacts via API.
 * Combines positive and negative scenarios with clean helper methods.
 */
public class DeleteContactApiTests implements BaseApi {

    private TokenDto token;

    @BeforeClass
    public void login() throws IOException {
        User user = new User(getProperty("base.properties", "login"),
                getProperty("base.properties", "password"));

        try (Response response = postRequest(BASE_URL + LOGIN_URL, GSON.toJson(user), null)) {
            if (response.isSuccessful() && response.body() != null) {
                token = GSON.fromJson(response.body().string(), TokenDto.class);
            } else {
                throw new RuntimeException("Login failed! Status code: " + response.code());
            }
        }
    }

    @Test
    public void deleteContactPositive_ApiTest() throws IOException {
        SoftAssert softAssert = new SoftAssert();

        String contactId = addNewContactAndGetId(positiveContact());

        try (Response response = deleteContact(contactId)) {
            softAssert.assertEquals(response.code(), 200, "Status code should be 200");

            if (response.body() != null) {
                ResponseMessageDto dto =
                        GSON.fromJson(response.body().string(), ResponseMessageDto.class);

                softAssert.assertTrue(
                        dto.containsMessage("Contact was deleted"),
                        "Expected message 'Contact was deleted', got: " + dto
                );
            }
        }
        softAssert.assertAll();
    }

    @Test
    @Issue("BUG-DELETE-404")
    @Description("Server returns 400 instead of 404 when deleting contact with non-existent ID")
    public void deleteContactNegative_NotFound_ApiTest() throws IOException {
        SoftAssert softAssert = new SoftAssert();
        String randomId = UUID.randomUUID().toString();

        try (Response response = deleteContact(randomId)) {
            softAssert.assertEquals(response.code(), 400, "Should return 400 for non-existent ID");

            if (response.body() != null) {
                ResponseMessageDto dto =
                        GSON.fromJson(response.body().string(), ResponseMessageDto.class);

                softAssert.assertTrue(
                        dto.containsMessage("not found"),
                        "Error message should mention 'not found', got: " + dto
                );
            }
        }
        softAssert.assertAll();
    }

    // --- Helper methods ---

    private Response deleteContact(String id) throws IOException {
        Request request = new Request.Builder()
                .url(BASE_URL + DELETE_CONTACT_URL + id)
                .addHeader(AUTH, token.getToken())
                .delete()
                .build();
        return OK_HTTP_CLIENT.newCall(request).execute();
    }

    private String addNewContactAndGetId(Contact contact) throws IOException {
        try (Response response = postRequest(BASE_URL + ADD_NEW_CONTACT_URL, GSON.toJson(contact), token.getToken())) {
            if (!response.isSuccessful() || response.body() == null) {
                throw new RuntimeException("Failed to create contact for test. Code: " + response.code());
            }
            return extractIdFromResponse(response.body().string());
        }
    }

    private String extractIdFromResponse(String responseBody) {
        Pattern pattern = Pattern.compile("ID: (\\S+)");
        Matcher matcher = pattern.matcher(responseBody);
        if (matcher.find()) {
            return matcher.group(1).replaceAll("[\"}]", "").trim();
        }
        throw new RuntimeException("Could not extract ID from response: " + responseBody);
    }

    private Response postRequest(String url, String json, String authToken) throws IOException {
        Request.Builder builder = new Request.Builder()
                .url(url)
                .post(RequestBody.create(json, JSON));
        if (authToken != null) {
            builder.addHeader(AUTH, authToken);
        }
        return OK_HTTP_CLIENT.newCall(builder.build()).execute();
    }
}