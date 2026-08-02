package com.phonebook.tests.mobile;

import com.phonebook.mobile.screens.ContactListScreen;
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
 * Mobile tests for user logout functionality.
 * Verifies that user can log out successfully and return to the authentication screen.
 */
@org.testng.annotations.Test(enabled = false)
public class LogoutTests extends TestBase {

    private static final Logger logger = LoggerFactory.getLogger(LogoutTests.class);
    private static final String APP_PACKAGE = "com.sheygam.contactapp";

    @BeforeMethod
    @Step("Open login/registration screen before each test")
    public void openAuthScreen() {
        loginRegistrationScreen = new LoginRegistrationScreen(driver);
    }

    @AfterMethod(alwaysRun = true)
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

    @Test(enabled = false, description = "Positive test: Login and logout flow verification")
    @Step("Login with valid credentials, then logout and verify authentication screen is displayed")
    public void logoutPositiveTest() {
        logger.info("Starting test: logoutPositiveTest");

        User user = new User(
                getProperty("base.properties", "login"),
                getProperty("base.properties", "password")
        );

        if (loginRegistrationScreen.isLoginRegistrationFormDisplayed()) {
            loginRegistrationScreen.typeLoginRegistrationForm(user);
            loginRegistrationScreen.clickBtnLogin();
        }

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
