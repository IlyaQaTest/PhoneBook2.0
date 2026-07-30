package com.phonebook.tests.ui;

import com.phonebook.model.User;
import com.phonebook.ui.manager.AppManager;
import com.phonebook.ui.pages.ContactPage;
import com.phonebook.ui.pages.HomePage;
import com.phonebook.ui.pages.LoginPage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import io.qameta.allure.Description;
import io.qameta.allure.Issue;
import java.util.Random;

import static com.phonebook.model.factory.UserFactory.positiveUser;

/**
 * UI tests for user registration.
 * Covers positive and negative registration scenarios.
 */
public class RegistrationTests extends AppManager {

    private static final Logger logger = LoggerFactory.getLogger(RegistrationTests.class);
    LoginPage loginPage;

    @BeforeMethod(alwaysRun = true)
    public void goToRegistrationPage() {
        new HomePage(getDriver()).clickBtnLogin();
        loginPage = new LoginPage(getDriver());
    }

    @Test
    public void registrationPositiveTest() {
        int i = new Random().nextInt(1000);
        User user = new User("mir" + i + "@gmail.com", "Password123!");
        loginPage.typeLoginRegistrationFormWithUser(user);
        loginPage.clickBtnRegistrationForm();
        Assert.assertTrue(new ContactPage(getDriver()).isTextInContactPageMessagePresent("No Contacts here!"));
    }

    @Test
    public void registrationPositiveTest_WithFaker() {
        User user = positiveUser();
        logger.info("Starting registration test with user: {}", user);
        loginPage.typeLoginRegistrationFormWithUser(user);
        loginPage.clickBtnRegistrationForm();
        boolean isSuccess = new ContactPage(getDriver()).isTextInContactPageMessagePresent("No Contacts here!");
        Assert.assertTrue(isSuccess);
        logger.info("Registration successful for user: {}", user.getUsername());
    }

    @Test
    public void registrationNegativeTestInvalidEmail() {
        int i = new Random().nextInt(1000);
        User user = new User("mir" + i + "gmail.com", "Password123$");
        logger.debug("Testing invalid email format: {}", user.getUsername());
        loginPage.typeLoginRegistrationFormWithUser(user);
        loginPage.clickBtnRegistrationForm();
        String alertText = loginPage.getAlertTextAndClose();
        logger.info("Alert text received: {}", alertText);
        Assert.assertTrue(alertText.contains("Wrong email or password format"));
    }

    @Test
    @Issue("BUG-REG-001")
    @Description("BUG: Registration succeeds with invalid email domain format (missing dot before TLD like @gmailcom)")
    public void registrationNegativeTestInvalidEmail1() {
        int i = new Random().nextInt(1000);
        User user = new User("mir" + i + "@gmailcom", "Password123$");
        logger.info("Running test for known bug BUG-REG-001 with email: {}", user.getUsername());

        loginPage.typeLoginRegistrationFormWithUser(user);
        loginPage.clickBtnRegistrationForm();

        // Фиксируем фактическое багованное поведение: приложение регистрирует пользователя
        // и перенаправляет на ContactPage вместо показа алерта с ошибкой.
        boolean isRegisteredDueToBug = new ContactPage(getDriver()).isTextInContactPageMessagePresent("No Contacts here!");

        Assert.assertTrue(isRegisteredDueToBug,
                "BUG-REG-001: Expected registration to fail with alert 'Wrong email or password format', but server accepts email without dot before TLD");
    }

    @Test
    public void registrationNegativeTestInvalidPassword() {
        int i = new Random().nextInt(1000);
        User user = new User("mir" + i + "gmail.com", "");
        loginPage.typeLoginRegistrationFormWithUser(user);
        loginPage.clickBtnRegistrationForm();
        Assert.assertTrue(loginPage.getAlertTextAndClose().contains("Wrong email or password format"));
    }

    @Test
    public void registrationNegativeTestInvalidPassword1() {
        int i = new Random().nextInt(1000);
        User user = new User("mir" + i + "gmail.com", "Password321");
        loginPage.typeLoginRegistrationFormWithUser(user);
        loginPage.clickBtnRegistrationForm();
        Assert.assertTrue(loginPage.getAlertTextAndClose().contains("Wrong email or password format"));
    }

    @Test
    public void registrationNegativeTestUser() {
        User user = new User("family@mail.ru", "Family123!");
        loginPage.typeLoginRegistrationFormWithUser(user);
        loginPage.clickBtnRegistrationForm();
        Assert.assertTrue(loginPage.getAlertTextAndClose().contains("User already exist"));
    }

    @Test
    public void registrationNegativeTestEmptyPasswordEmptyEmail() {
        User user = new User("", "");
        loginPage.typeLoginRegistrationFormWithUser(user);
        loginPage.clickBtnRegistrationForm();
        Assert.assertTrue(loginPage.getAlertTextAndClose().contains("Wrong email or password format"));
    }

    @Test
    public void registrationNegativeTestEmptyPassword() {
        User user = new User("family@mail.ru", "");
        loginPage.typeLoginRegistrationFormWithUser(user);
        loginPage.clickBtnRegistrationForm();
        Assert.assertTrue(loginPage.getAlertTextAndClose().contains("Wrong email or password format"));
    }

    @Test
    public void registrationNegativeTestEmptyEmail() {
        User user = new User("", "Family123!");
        loginPage.typeLoginRegistrationFormWithUser(user);
        loginPage.clickBtnRegistrationForm();
        Assert.assertTrue(loginPage.getAlertTextAndClose().contains("Wrong email or password format"));
    }
}