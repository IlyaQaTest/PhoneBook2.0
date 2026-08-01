package com.phonebook.tests.mobile;

import com.phonebook.mobile.screens.ContactListScreen;
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
 * Mobile tests for user logout functionality.
 * Verifies that user can log out successfully and return to the authentication screen.
 */
public class LogoutTests extends TestBase {

    private static final Logger logger = LoggerFactory.getLogger(LogoutTests.class);

    @BeforeMethod
    @Step("Open login/registration screen before each test")
    public void openAuthScreen() {
        loginRegistrationScreen = new LoginRegistrationScreen(driver);
    }

    @Test(description = "Positive test: Login and logout flow verification")
    @Step("Login with valid credentials, then logout and verify authentication screen is displayed")
    public void logoutPositiveTest() {
        logger.info("Starting test: logoutPositiveTest");

        User user = new User(
                getProperty("base.properties", "login"),
                getProperty("base.properties", "password")
        );
        loginRegistrationScreen.typeLoginRegistrationForm(user);
        loginRegistrationScreen.clickBtnLogin();

        contactListScreen = new ContactListScreen(driver);
        boolean isLoaded = contactListScreen.isContactListDisplayed();
        logger.info("Contact list screen displayed: {}", isLoaded);
        Assert.assertTrue(isLoaded, "Contact List screen should be displayed after login");

        logger.info("Performing logout...");
        contactListScreen.clickMoreOptions();
        contactListScreen.clickBtnLogout();

        boolean isAuthDisplayed = loginRegistrationScreen.isTextAuthenticationDisplayed();
        logger.info("Authentication screen displayed after logout: {}", isAuthDisplayed);
        Assert.assertTrue(isAuthDisplayed, "Authentication screen should be displayed after logout");
    }
}