package com.phonebook.tests.api;

import com.phonebook.api.client.BaseApi;
import com.phonebook.model.User;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import io.qameta.allure.Issue;
import io.qameta.allure.Description;
import static com.phonebook.model.factory.UserFactory.positiveUser;

public class RegistrationApiTests implements BaseApi {

    /**
     * Helper method to send registration request.
     * Keeps tests clean and follows DRY principle.
     */
    private Response executeRegistration(Object body) throws IOException {
        RequestBody requestBody = RequestBody.create(GSON.toJson(body), JSON);
        Request request = new Request.Builder()
                .url(BASE_URL + REGISTRATION_URL)
                .post(requestBody)
                .build();
        return OK_HTTP_CLIENT.newCall(request).execute();
    }

    // ---------------------- POSITIVE TESTS ----------------------

    @Test
    public void registrationPositive() {
        SoftAssert softAssert = new SoftAssert();

        try (Response response = executeRegistration(positiveUser())) {
            softAssert.assertEquals(response.code(), 200, "Expected status 200");
        } catch (IOException e) {
            throw new RuntimeException("API communication error", e);
        }

        softAssert.assertAll();
    }

    // ---------------------- NEGATIVE TESTS ----------------------

    @Test
    public void registrationNegative_WrongPassword() {
        SoftAssert softAssert = new SoftAssert();
        User user = positiveUser();
        user.setPassword("wrong_password");

        try (Response response = executeRegistration(user)) {
            softAssert.assertEquals(response.code(), 400, "Expected 400 for invalid password");
        } catch (IOException e) {
            throw new RuntimeException("API error", e);
        }

        softAssert.assertAll();
    }

    @Test
    public void registrationNegative_InvalidEmail() {
        SoftAssert softAssert = new SoftAssert();
        int i = new Random().nextInt(10000);
        User user = new User("mir" + i + "gmailcom", "Password123$"); // missing '@'

        try (Response response = executeRegistration(user)) {
            String responseBody = response.body() != null ? response.body().string() : "";

            softAssert.assertEquals(response.code(), 400, "Expected 400 for invalid email");
            softAssert.assertTrue(responseBody.contains("must be a well-formed email address"),
                    "Error message mismatch. Got: " + responseBody);
        } catch (IOException e) {
            throw new RuntimeException("API error", e);
        }

        softAssert.assertAll();
    }

    @Test
    public void registrationNegative_EmptyPassword() {
        SoftAssert softAssert = new SoftAssert();
        int i = new Random().nextInt(10000);
        User user = new User("mir" + i + "@gmail.com", "");

        try (Response response = executeRegistration(user)) {
            String responseBody = response.body() != null ? response.body().string() : "";

            softAssert.assertEquals(response.code(), 400, "Server should not allow empty password");
            softAssert.assertTrue(responseBody.contains("Bad Request"),
                    "Expected error message not found");
        } catch (IOException e) {
            throw new RuntimeException("API error", e);
        }

        softAssert.assertAll();
    }

    @Test
    public void registrationNegative_UserAlreadyExists() {
        SoftAssert softAssert = new SoftAssert();
        User user = new User("family@mail.ru", "Family123!");

        try (Response response = executeRegistration(user)) {
            String responseBody = response.body() != null ? response.body().string() : "";

            softAssert.assertEquals(response.code(), 409, "Expected 409 Conflict");
            softAssert.assertTrue(responseBody.contains("User already exist"),
                    "Error message mismatch. Got: " + responseBody);
        } catch (IOException e) {
            throw new RuntimeException("API error", e);
        }

        softAssert.assertAll();
    }

    @Test
    public void registrationNegative_EmptyEmail() {
        SoftAssert softAssert = new SoftAssert();
        User user = new User("", "Family123!");

        try (Response response = executeRegistration(user)) {
            String responseBody = response.body() != null ? response.body().string() : "";

            softAssert.assertEquals(response.code(), 400, "Empty email should not be allowed");
            softAssert.assertTrue(responseBody.contains("must not be blank"),
                    "Expected error message not found. Got: " + responseBody);
        } catch (IOException e) {
            throw new RuntimeException("API error", e);
        }

        softAssert.assertAll();
    }

    @Test
    @Issue("BUG-REG-500")
    @Description("Server returns 500 instead of 400 for invalid content type")
    public void registrationNegative_WrongContentType_Text() {
        SoftAssert softAssert = new SoftAssert();
        User user = positiveUser();

        RequestBody requestBody = RequestBody.create(GSON.toJson(user), TEXT);
        Request request = new Request.Builder()
                .url(BASE_URL + REGISTRATION_URL)
                .post(requestBody)
                .build();

        try (Response response = OK_HTTP_CLIENT.newCall(request).execute()) {
            softAssert.assertEquals(response.code(), 500,
                    "Expected 400 for invalid content type, but server returns 500 (BUG)");
        } catch (IOException e) {
            throw new RuntimeException("API error", e);
        }

        softAssert.assertAll();
    }

    @Test
    @Issue("BUG-REG-500")
    @Description("Server returns 500 instead of 400 for invalid JSON keys")
    public void registrationNegative_InvalidJsonKeys() {
        SoftAssert softAssert = new SoftAssert();
        User user = positiveUser();

        Map<String, String> invalidJson = new HashMap<>();
        invalidJson.put("name", user.getUsername()); // wrong key
        invalidJson.put("password", user.getPassword());

        try (Response response = executeRegistration(invalidJson)) {
            softAssert.assertEquals(response.code(), 500,
                    "Expected 400 for invalid JSON keys, but server returns 500 (BUG)");
        } catch (IOException e) {
            throw new RuntimeException("API error", e);
        }

        softAssert.assertAll();
    }

    @Test
    @Issue("BUG-REG-500")
    @Description("Server returns 500 instead of 400 for malformed JSON structure")
    public void registrationNegative_InvalidJsonStructure() {
        SoftAssert softAssert = new SoftAssert();

        String brokenJson = "{\"email\":\"test@mail.com\", \"password\":\"Pass123!\""; // missing brace

        RequestBody requestBody = RequestBody.create(brokenJson, JSON);
        Request request = new Request.Builder()
                .url(BASE_URL + REGISTRATION_URL)
                .post(requestBody)
                .build();

        try (Response response = OK_HTTP_CLIENT.newCall(request).execute()) {
            softAssert.assertEquals(response.code(), 500,
                    "Expected 400 for malformed JSON, but server returns 500 (BUG)");
        } catch (IOException e) {
            throw new RuntimeException("API error", e);
        }

        softAssert.assertAll();
    }
}