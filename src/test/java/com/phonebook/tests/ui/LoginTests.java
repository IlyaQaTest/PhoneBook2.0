package com.phonebook.tests.ui;

import com.phonebook.model.User;
import com.phonebook.tests.utils_tests.listeners.TestNGListener;
import com.phonebook.tests.utils_tests.retry.RetryAnalyser;
import com.phonebook.ui.manager.AppManager;
import com.phonebook.ui.pages.ContactPage;
import com.phonebook.ui.pages.HomePage;
import com.phonebook.ui.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import static com.phonebook.core.config.PropertiesReader.getProperty;

/**
 * UI tests for user login.
 * Covers positive and negative login scenarios.
 */
@Listeners(TestNGListener.class)
public class LoginTests extends AppManager {

    @Test(retryAnalyzer = RetryAnalyser.class)
    public void loginPositiveTest() {
        HomePage homePage = new HomePage(getDriver());
        homePage.clickBtnLogin();

        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.typeLoginRegistrationForm(
                getProperty("base.properties", "login"),
                getProperty("base.properties", "password")
        );
        loginPage.clickBtnLoginForm();

        Assert.assertTrue(new ContactPage(getDriver()).isTextInBtnAddPresent("ADD"));
    }

    @Test(groups = "smoke")
    public void loginPositiveTestWithUser() {
        User user = new User(
                getProperty("base.properties", "login"),
                getProperty("base.properties", "password")
        );

        HomePage homePage = new HomePage(getDriver());
        homePage.clickBtnLogin();

        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.typeLoginRegistrationFormWithUser(user);
        loginPage.clickBtnLoginForm();

        Assert.assertTrue(new ContactPage(getDriver()).isTextInBtnSignOutPresent("Sign Out"));
    }

    @Test
    public void loginNegativeTest_WrongEmail() {
        User user = new User("familymail.ru", "Family123!");
        HomePage homePage = new HomePage(getDriver());
        homePage.clickBtnLogin();

        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.typeLoginRegistrationFormWithUser(user);
        loginPage.clickBtnLoginForm();

        Assert.assertEquals(loginPage.getAlertTextAndClose(), "Wrong email or password");
    }
}