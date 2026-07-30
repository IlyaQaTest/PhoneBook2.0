package com.phonebook.ui.pages;

import com.phonebook.model.User;
import org.openqa.selenium.*;
import org.openqa.selenium.support.*;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

/**
 * Login and registration page object.
 * Handles user input, form actions, and alert interactions.
 */
public class LoginPage extends BasePage {

    public LoginPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, 10), this);
    }

    @FindBy(xpath = "//input[@name='email']")
    WebElement inputEmail;

    @FindBy(xpath = "//input[@placeholder='Password']")
    WebElement inputPassword;

    @FindBy(xpath = "//button[text()='Login']")
    WebElement btnLoginForm;

    @FindBy(css = "button[name='registration']")
    WebElement btnRegistrationForm;

    // Fill login or registration form
    public void typeLoginRegistrationForm(String email, String password) {
        inputEmail.sendKeys(email);
        inputPassword.sendKeys(password);
    }

    // Fill form using User model
    public void typeLoginRegistrationFormWithUser(User user) {
        typeLoginRegistrationForm(user.getUsername(), user.getPassword());
    }

    // Get alert text and close it
    public String closeAlertReturnText() {
        Alert alert = driver.switchTo().alert();
        String text = alert.getText();
        alert.accept();
        return text;
    }

    // Click Login button
    public void clickBtnLoginForm() {
        btnLoginForm.click();
    }

    // Click Registration button
    public void clickBtnRegistrationForm() {
        wait.until(ExpectedConditions.elementToBeClickable(btnRegistrationForm)).click();
    }
}