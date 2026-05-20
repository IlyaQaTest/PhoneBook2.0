package com.phonebook.tests.mobile;

import com.phonebook.mobile.screens.ContactListScreen;
import com.phonebook.mobile.screens.ErrorScreen;
import com.phonebook.mobile.screens.LoginRegistrationScreen;
import com.phonebook.model.User;
import io.qameta.allure.Step;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.phonebook.core.config.PropertiesReader.getProperty;

/**
 * Mobile tests for user login functionality.
 * Includes positive and negative scenarios for authentication.
 */
public class LoginTests extends TestBase {

    private static final Logger logger = LoggerFactory.getLogger(LoginTests.class);
    private LoginRegistrationScreen loginRegistrationScreen;

    @BeforeMethod
    @Step("Open login/registration screen before each test")
    public void openAuthScreen() {
        loginRegistrationScreen = new LoginRegistrationScreen(driver);
    }

    @Test(description = "Positive test: Successful login and transition to contact list screen")
    @Step("Login with valid credentials and verify contact list is displayed")
    public void loginPositiveTest() {
        logger.info("Starting test: loginPositiveTest");
        User user = new User(
                getProperty("base.properties", "login"),
                getProperty("base.properties", "password")
        );

        loginRegistrationScreen.typeLoginRegistrationForm(user);
        loginRegistrationScreen.clickBtnLogin();

        ContactListScreen contactListScreen = new ContactListScreen(driver);
        boolean isLoaded = contactListScreen.isContactListDisplayed();

        logger.info("Contact list screen displayed: {}", isLoaded);
        Assert.assertTrue(isLoaded, "Contact List screen should be displayed after login");
        logger.info("Test completed successfully");
    }

    @Test(description = "Positive test: Verify '+' button is visible after login")
    @Step("Login and check if '+' button is present on contact list screen")
    public void loginPositiveBtnPlusTest() {
        User user = new User(
                getProperty("base.properties", "login"),
                getProperty("base.properties", "password")
        );

        loginRegistrationScreen.typeLoginRegistrationForm(user);
        loginRegistrationScreen.clickBtnLogin();

        ContactListScreen contactListScreen = new ContactListScreen(driver);
        Assert.assertTrue(contactListScreen.isBtnPlusPresent(),
                "Plus button should be visible after login");
        logger.info("Plus button verified successfully");
    }

    @Test(description = "Negative test: Empty password")
    @Step("Attempt login with empty password and verify alert message")
    public void loginNegativeEmptyPasswordTest() {
        User user = new User(getProperty("base.properties", "login"), "");
        logger.debug("Testing login with empty password for user: {}", user.getUsername());

        loginRegistrationScreen.typeLoginRegistrationForm(user);
        loginRegistrationScreen.clickBtnLogin();

        String alertText = getAlertTextAndClose();
        logger.info("Alert text received: {}", alertText);

        Assert.assertTrue(alertText.contains("Login or Password incorrect"),
                "Expected alert message not displayed");
    }

    @Test(description = "Negative test: Empty login field")
    @Step("Attempt login with empty login and verify error message")
    public void loginNegativeEmptyLoginTest() {
        User user = new User("", getProperty("base.properties", "password"));
        loginRegistrationScreen.typeLoginRegistrationForm(user);
        loginRegistrationScreen.clickBtnLogin();

        Assert.assertTrue(new ErrorScreen(driver)
                        .validateTextInError("Login or Password incorrect", 5),
                "Error message not displayed for empty login");
        logger.warn("Validation failed for empty login");
    }

    @Test(description = "Negative test: Empty fields")
    @Step("Attempt login with empty fields and verify error message")
    public void loginNegativeEmptyFieldsTest() {
        loginRegistrationScreen.clickBtnLogin();

        Assert.assertTrue(new ErrorScreen(driver)
                        .validateTextInError("Login or Password incorrect", 5),
                "Error message not displayed for empty fields");
        logger.warn("Validation failed for empty fields");
    }

    @Test(description = "Negative test: Wrong email with space")
    @Step("Attempt login with email containing space and verify error message")
    public void loginNegativeWrongEmailWithSpaceTest() {
        User user = new User(" ", getProperty("base.properties", "password"));
        loginRegistrationScreen.typeLoginRegistrationForm(user);
        loginRegistrationScreen.clickBtnLogin();

        Assert.assertTrue(new ErrorScreen(driver)
                        .validateTextInError("Login or Password incorrect", 5),
                "Error message not displayed for invalid email format");
        logger.warn("Validation failed for email with space");
    }
}