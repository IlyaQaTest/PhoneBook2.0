package com.phonebook.mobile;

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
 * Screen object representing the Login & Registration screen.
 * Provides actions for entering user credentials and submitting authentication forms.
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
    private WebElement authTitle;

    /**
     * Checks whether the Authentication title is displayed.
     *
     * @return true if the screen is visible, false otherwise
     */
    public boolean isTextAuthenticationDisplayed() {
        try {
            return authTitle.isDisplayed();
        } catch (Exception e) {
            logger.debug("Authentication title not found");
            return false;
        }
    }

    /**
     * Fills the login/registration form with user credentials.
     *
     * @param user User model containing email and password
     */
    public void typeLoginRegistrationForm(User user) {
        logger.info("Entering user credentials: {}", user.getUsername());

        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            logger.debug("Waiting for Email field...");
            wait.until(ExpectedConditions.visibilityOf(inputEmail));

            inputEmail.click();
            inputEmail.clear();
            inputEmail.sendKeys(user.getUsername());
            logger.debug("Email entered");

            inputPassword.click();
            inputPassword.clear();
            inputPassword.sendKeys(user.getPassword());
            logger.debug("Password entered");

        } catch (Exception e) {
            logger.error("Error while entering login/registration data: {}", e.getMessage());
            throw e;
        }
    }

    /**
     * Taps the Registration button.
     */
    public void clickBtnRegistration() {
        logger.info("Clicking Registration button");
        btnRegistration.click();
    }

    /**
     * Checks whether the login/registration form is visible.
     *
     * @return true if form is displayed, false otherwise
     */
    public boolean isLoginRegistrationFormDisplayed() {
        try {
            return inputEmail.isDisplayed();
        } catch (Exception e) {
            logger.debug("Login/Registration form not visible");
            return false;
        }
    }

    /**
     * Taps the Login button.
     */
    public void clickBtnLogin() {
        logger.info("Clicking Login button");
        btnLogin.click();
    }
}