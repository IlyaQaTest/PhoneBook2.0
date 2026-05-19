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
import static com.phonebook.core.config.PropertiesReader.getProperty;
import static com.phonebook.model.factory.ContactFactory.positiveContact;

public class EditContactApiTests implements BaseApi {

    private TokenDto token;

    @BeforeClass
    public void login() throws IOException {
        User user = new User(
                getProperty("base.properties", "login"),
                getProperty("base.properties", "password")
        );

        try (Response response = postRequest(BASE_URL + LOGIN_URL, GSON.toJson(user), null)) {
            if (response.isSuccessful() && response.body() != null) {
                token = GSON.fromJson(response.body().string(), TokenDto.class);
            } else {
                throw new RuntimeException("Login failed! Status code: " + response.code());
            }
        }
    }

    @Test
    public void editContactPositive_ApiTest() throws IOException {
        SoftAssert softAssert = new SoftAssert();

        Contact contact = positiveContact();
        String contactId = addNewContactAndGetId(contact);

        contact.setId(contactId);
        contact.setName("Updated Name");
        contact.setLastName("Updated Last Name");

        try (Response response = editContact(contact)) {
            softAssert.assertEquals(response.code(), 200);

            ResponseMessageDto dto =
                    GSON.fromJson(response.body().string(), ResponseMessageDto.class);

            softAssert.assertTrue(
                    dto.containsMessage("Contact was updated"),
                    "Expected 'Contact was updated', got: " + dto
            );
        }

        softAssert.assertAll();
    }

    @Test
    @Issue("BUG-EDIT-404")
    @Description("Server returns 400 instead of 404 when editing contact with invalid ID")
    public void editContactNegative_InvalidId_ApiTest() throws IOException {
        SoftAssert softAssert = new SoftAssert();

        Contact contact = positiveContact();
        contact.setId(UUID.randomUUID().toString());

        try (Response response = editContact(contact)) {
            softAssert.assertEquals(response.code(), 400);

            ResponseMessageDto dto =
                    GSON.fromJson(response.body().string(), ResponseMessageDto.class);

            softAssert.assertTrue(
                    dto.containsMessage("not found"),
                    "Expected 'not found', got: " + dto
            );
        }

        softAssert.assertAll();
    }

    @Test
    public void editContactNegative_EmptyName_ApiTest() throws IOException {
        SoftAssert softAssert = new SoftAssert();

        Contact contact = positiveContact();
        String contactId = addNewContactAndGetId(contact);

        contact.setId(contactId);
        contact.setName("");

        try (Response response = editContact(contact)) {
            softAssert.assertEquals(response.code(), 400);

            ResponseMessageDto dto =
                    GSON.fromJson(response.body().string(), ResponseMessageDto.class);

            softAssert.assertTrue(
                    dto.containsMessage("must not be blank"),
                    "Expected 'must not be blank', got: " + dto
            );
        }

        softAssert.assertAll();
    }

    // --- Helper methods ---

    private Response editContact(Contact contact) throws IOException {
        RequestBody body = RequestBody.create(GSON.toJson(contact), JSON);
        Request request = new Request.Builder()
                .url(BASE_URL + EDIT_CONTACT_URL)
                .addHeader(AUTH, token.getToken())
                .put(body)
                .build();
        return OK_HTTP_CLIENT.newCall(request).execute();
    }

    private String addNewContactAndGetId(Contact contact) throws IOException {
        try (Response response = postRequest(BASE_URL + ADD_NEW_CONTACT_URL, GSON.toJson(contact), token.getToken())) {
            if (!response.isSuccessful() || response.body() == null) {
                throw new RuntimeException("Failed to create contact. Code: " + response.code());
            }
            String body = response.body().string();
            return body.split(": ")[1].replaceAll("[\"}]", "").trim();
        }
    }

    private Response postRequest(String url, String json, String authToken) throws IOException {
        Request.Builder builder = new Request.Builder()
                .url(url)
                .post(RequestBody.create(json, JSON));
        if (authToken != null) builder.addHeader(AUTH, authToken);
        return OK_HTTP_CLIENT.newCall(builder.build()).execute();
    }
}