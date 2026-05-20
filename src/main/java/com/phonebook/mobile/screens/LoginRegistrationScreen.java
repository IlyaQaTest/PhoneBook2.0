package com.phonebook.mobile.screens;

import com.phonebook.model.User;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

/**
 * Represents the login and registration screen in the mobile application.
 * Provides methods to enter user credentials and perform authentication actions.
 */
public class LoginRegistrationScreen extends BaseScreen {

    private static final Logger logger = LoggerFactory.getLogger(LoginRegistrationScreen.class);

    public LoginRegistrationScreen(AppiumDriver driver) {
        super(driver);
    }

    @AndroidFindBy(id = "com.sheygam.contactapp:id/inputEmail")
    private WebElement inputEmail;

    @AndroidFindBy(id = "com.sheygam.contactapp:id/inputPassword")
    private WebElement inputPassword;

    @AndroidFindBy(id = "com.sheygam.contactapp:id/regBtn")
    private WebElement btnRegistration;

    @AndroidFindBy(id = "com.sheygam.contactapp:id/loginBtn")
    private WebElement btnLogin;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='Authentication']")
    private WebElement auth;

    /**
     * Checks if the authentication title is displayed on the screen.
     *
     * @return true if the title is visible, false otherwise
     */
    public boolean isTextAuthenticationDisplayed() {
        try {
            return auth.isDisplayed();
        } catch (Exception e) {
            logger.warn("Authentication title not found: {}", e.getMessage());
            return false;
        }
    }

    /**
     * Fills the login or registration form with user credentials.
     *
     * @param user the user object containing username and password
     */
    public void typeLoginRegistrationForm(User user) {
        logger.info("Entering user credentials for: {}", user.getUsername());

        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            logger.debug("Waiting for Email field to appear...");
            wait.until(ExpectedConditions.visibilityOf(inputEmail));

            inputEmail.click();
            inputEmail.clear();
            inputEmail.sendKeys(user.getUsername());
            logger.info("Email entered successfully");

            inputPassword.sendKeys(user.getPassword());
            logger.info("Password entered successfully");

        } catch (Exception e) {
            logger.error("Error while entering user data: {}", e.getMessage());
            throw e;
        }
    }

    /**
     * Clicks the registration button to create a new account.
     */
    public void clickBtnRegistration() {
        logger.info("Clicking 'Registration' button");
        btnRegistration.click();
    }

    /**
     * Checks if the login/registration form is displayed.
     *
     * @return true if the form is visible, false otherwise
     */
    public boolean isLoginRegistrationFormDisplayed() {
        try {
            return inputEmail.isDisplayed();
        } catch (Exception e) {
            logger.warn("Login/Registration form not visible: {}", e.getMessage());
            return false;
        }
    }

    /**
     * Clicks the login button to authenticate the user.
     */
    public void clickBtnLogin() {
        logger.info("Clicking 'Login' button");
        btnLogin.click();
    }
}