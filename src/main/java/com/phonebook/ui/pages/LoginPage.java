package com.phonebook.ui.pages;

import com.phonebook.model.User;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Represents the Login page of the PhoneBook web application.
 * Provides methods to fill in login or registration forms and perform related actions.
 */
public class LoginPage extends BasePage {

    private static final Logger logger = LoggerFactory.getLogger(LoginPage.class);

    @FindBy(xpath = "//input[@name='email']")
    private WebElement inputEmail;

    @FindBy(xpath = "//input[@placeholder='Password']")
    private WebElement inputPassword;

    @FindBy(xpath = "//button[text()='Login']")
    private WebElement btnLoginForm;

    @FindBy(css = "button[name='registration']")
    private WebElement btnRegistrationForm;

    public LoginPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, 10), this);
        logger.info("LoginPage initialized successfully.");
    }

    /**
     * Fills the login or registration form with provided email and password.
     */
    public void typeLoginRegistrationForm(String email, String password) {
        inputEmail.sendKeys(email);
        inputPassword.sendKeys(password);
        logger.info("Entered credentials for user: {}", email);
    }

    /**
     * Fills the login or registration form using a User object.
     */
    public void typeLoginRegistrationFormWithUser(User user) {
        typeLoginRegistrationForm(user.getUsername(), user.getPassword());
    }

    /**
     * Clicks the Login button on the form.
     */
    public void clickLoginButton() {
        btnLoginForm.click();
        logger.info("Clicked Login button.");
    }

    /**
     * Clicks the Registration button on the form.
     */
    public void clickRegistrationButton() {
        wait.until(ExpectedConditions.elementToBeClickable(btnRegistrationForm)).click();
        logger.info("Clicked Registration button.");
    }
}