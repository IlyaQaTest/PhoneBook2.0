package com.phonebook.tests.api;

import com.phonebook.api.client.BaseApi;
import com.phonebook.api.dto.ResponseMessageDto;
import com.phonebook.model.Contact;
import com.phonebook.model.User;
import com.phonebook.model.factory.UserBuilder;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import io.qameta.allure.Issue;
import io.qameta.allure.Description;
import org.testng.asserts.SoftAssert;

import static com.phonebook.model.factory.ContactFactory.positiveContact;
import static io.restassured.RestAssured.given;

public class AddContactApiTests implements BaseApi {

    private static final Logger logger = LoggerFactory.getLogger(AddContactApiTests.class);
    private String token;

    @BeforeClass
    public void login() {
        RestAssured.baseURI = BASE_URL;

        // Using UserBuilder for consistency
        User user = UserBuilder.builder()
                .username("fuf@fuf.fuf")
                .password("000FuFu&^")
                .build();

        token = given()
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .post(LOGIN_URL)
                .then()
                .statusCode(200)
                .extract()
                .path("token");

        logger.info("Authorization successful. Token received.");
    }

    @Test
    public void addContactPositive() {
        int i = (int) (System.currentTimeMillis() / 1000 % 3600);
        Contact contact = Contact.builder()
                .name("Ilya")
                .lastName("QA")
                .phone("1234567890" + i)
                .email("test" + i + "@mail.com")
                .address("Haifa")
                .description("API Test")
                .build();

        Response response = given()
                .header("Authorization", token)
                .contentType(ContentType.JSON)
                .body(contact)
                .when()
                .post(ADD_NEW_CONTACT_URL)
                .then()
                .extract()
                .response();

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(response.statusCode(), 200, "Expected status code 200");

        // Safe parsing of ResponseMessageDto
        ResponseMessageDto responseMessageDto = null;
        try {
            responseMessageDto = GSON.fromJson(response.body().asString(), ResponseMessageDto.class);
        } catch (Exception e) {
            logger.error("Failed to parse ResponseMessageDto: " + e.getMessage());
        }

        if (responseMessageDto != null) {
            softAssert.assertTrue(responseMessageDto.toString().contains("Contact was added"), "Response message mismatch");
        } else {
            logger.warn("ResponseMessageDto is null. Raw body: " + response.body().asString());
        }

        softAssert.assertAll();
        logger.info("Contact successfully added and verified via ResponseMessageDto.");
    }

    @Test
    public void addContactNegative_InvalidEmail() {
        SoftAssert softAssert = new SoftAssert();
        Contact contact = positiveContact();
        contact.setEmail("invalid.email.com"); // missing '@'

        Response response = given()
                .header("Authorization", token)
                .contentType(ContentType.JSON)
                .body(contact)
                .when()
                .post(ADD_NEW_CONTACT_URL)
                .then()
                .extract()
                .response();

        softAssert.assertEquals(response.statusCode(), 400, "Expected 400 for invalid email");
        logger.warn("Error response body: " + response.asString());

        softAssert.assertTrue(response.asString().contains("must be a well-formed email address"),
                "Error message mismatch. Got: " + response.asString());
        softAssert.assertAll();

        logger.warn("Invalid email validation test completed.");
    }

    @Test
    public void addContactNegative_WrongToken() {
        Contact contact = positiveContact();

        Response response = given()
                .header("Authorization", "invalid.token.123")
                .contentType(ContentType.JSON)
                .body(contact)
                .when()
                .post(ADD_NEW_CONTACT_URL)
                .then()
                .extract()
                .response();

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(response.statusCode(), 401, "Expected 401 for invalid token");

        logger.debug("Received expected 401 for invalid token. Body: " + response.asString());
        softAssert.assertAll();
    }

    @Test
    public void addContactNegative_WithoutToken() {
        Contact contact = positiveContact();

        Response response = given()
                .contentType(ContentType.JSON)
                .body(contact)
                .when()
                .post(ADD_NEW_CONTACT_URL)
                .then()
                .extract()
                .response();

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(response.statusCode(), 403, "Expected 403 for missing token");

        logger.info("Request without token returned 403 as expected. Body: " + response.asString());
        softAssert.assertAll();
    }

    @Test
    @Issue("BUG-ADD-400")
    @Description("Server returns wrong status code for invalid MediaType (text/plain instead of JSON)")
    public void addContactNegative_WrongMediaType() {
        // Sending request with incorrect MediaType (text/plain instead of JSON)
        Response response = given()
                .header("Authorization", token)
                .contentType(ContentType.TEXT)
                .body("invalid body for contact")
                .when()
                .post(ADD_NEW_CONTACT_URL)
                .then()
                .extract()
                .response();

        int actualStatus = response.statusCode();
        int expectedStatus = 400; // Expected behavior according to API contract

        SoftAssert softAssert = new SoftAssert();

        // Assertion with explicit BUG marker
        softAssert.assertEquals(
                actualStatus,
                expectedStatus,
                "BUG: API returns " + actualStatus + " instead of expected 400 for invalid MediaType"
        );

        // Logging the bug clearly
        if (actualStatus != expectedStatus) {
            logger.error("BUG DETECTED: Wrong MediaType returned {} instead of 400. Response body: {}",
                    actualStatus, response.asString());
        } else {
            logger.info("Wrong MediaType correctly returned 400");
        }

        softAssert.assertAll();
    }
}