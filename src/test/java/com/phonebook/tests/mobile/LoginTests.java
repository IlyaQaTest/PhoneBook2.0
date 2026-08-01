package com.phonebook.tests.mobile;

import com.phonebook.mobile.screens.ContactListScreen;
import com.phonebook.mobile.screens.ErrorScreen;
import com.phonebook.mobile.screens.LoginRegistrationScreen;
import com.phonebook.model.User;
import io.qameta.allure.Step;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.phonebook.core.config.PropertiesReader.getProperty;

/**
 * Mobile tests for user login functionality.
 * Includes positive and negative scenarios for authentication.
 */
public class LoginTests extends TestBase {

    private static final Logger logger = LoggerFactory.getLogger(LoginTests.class);
    private static final String APP_PACKAGE = "com.sheygam.contactapp";

    @BeforeMethod
    @Step("Open login/registration screen before each test")
    public void openAuthScreen() {
        loginRegistrationScreen = new LoginRegistrationScreen(driver);
    }

    @AfterMethod
    @Step("Reset application state after each test")
    public void postCondition() {
        if (driver != null) {
            try {
                driver.terminateApp(APP_PACKAGE);
                driver.activateApp(APP_PACKAGE);

                // CI emulator needs time to stabilize after restart
                try {
                    Thread.sleep(1500);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }

                logger.info("Application restarted successfully in postCondition");
            } catch (Exception e) {
                logger.warn("Failed to restart app in postCondition: {}", e.getMessage());
            }
        }
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

        contactListScreen = new ContactListScreen(driver);

        // CI emulator needs time to load contact list
        contactListScreen.waitForContactListNotEmpty();

        boolean isLoaded = contactListScreen.isContactListDisplayed();
        logger.info("Contact list screen displayed: {}", isLoaded);

        Assert.assertTrue(isLoaded, "Contact List screen should be displayed after login");
    }

    @Test(description = "Positive test: Verify '+' button is visible after login")
    @Step("Login and check if '+' button is present on contact list screen")
    public void loginPositiveBtnPlusTest() {
        logger.info("Starting test: loginPositiveBtnPlusTest");
        User user = new User(
                getProperty("base.properties", "login"),
                getProperty("base.properties", "password")
        );

        loginRegistrationScreen.typeLoginRegistrationForm(user);
        loginRegistrationScreen.clickBtnLogin();

        contactListScreen = new ContactListScreen(driver);

        // CI emulator needs time to load contact list
        contactListScreen.waitForContactListNotEmpty();

        Assert.assertTrue(contactListScreen.isBtnPlusPresent(),
                "Plus button should be visible after login");
    }

    @Test(description = "Negative test: Empty password")
    @Step("Attempt login with empty password and verify error message")
    public void loginNegativeEmptyPasswordTest() {
        User user = new User(getProperty("base.properties", "login"), "");
        logger.debug("Testing login with empty password for user: {}", user.getUsername());

        loginRegistrationScreen.typeLoginRegistrationForm(user);
        loginRegistrationScreen.clickBtnLogin();

        Assert.assertTrue(new ErrorScreen(driver)
                        .validateTextInError("Login or Password incorrect", 10),
                "Error message not displayed for empty password");
    }

    @Test(description = "Negative test: Empty login field")
    @Step("Attempt login with empty login and verify error message")
    public void loginNegativeEmptyLoginTest() {
        User user = new User("", getProperty("base.properties", "password"));
        loginRegistrationScreen.typeLoginRegistrationForm(user);
        loginRegistrationScreen.clickBtnLogin();

        Assert.assertTrue(new ErrorScreen(driver)
                        .validateTextInError("Login or Password incorrect", 10),
                "Error message not displayed for empty login");
    }

    @Test(description = "Negative test: Empty fields")
    @Step("Attempt login with empty fields and verify error message")
    public void loginNegativeEmptyFieldsTest() {
        loginRegistrationScreen.clickBtnLogin();

        Assert.assertTrue(new ErrorScreen(driver)
                        .validateTextInError("Login or Password incorrect", 10),
                "Error message not displayed for empty fields");
    }

    @Test(description = "Negative test: Wrong email with space")
    @Step("Attempt login with email containing space and verify error message")
    public void loginNegativeWrongEmailWithSpaceTest() {
        User user = new User(" ", getProperty("base.properties", "password"));
        loginRegistrationScreen.typeLoginRegistrationForm(user);
        loginRegistrationScreen.clickBtnLogin();

        Assert.assertTrue(new ErrorScreen(driver)
                        .validateTextInError("Login or Password incorrect", 10),
                "Error message not displayed for invalid email format");
    }
}
