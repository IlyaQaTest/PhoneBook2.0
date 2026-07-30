package com.phonebook.tests.ui;

import com.phonebook.ui.manager.AppManager;
import com.phonebook.ui.pages.ContactPage;
import com.phonebook.ui.pages.HomePage;
import com.phonebook.ui.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.phonebook.core.config.PropertiesReader.getProperty;
import static com.phonebook.ui.utils.HeaderMenuItem.LOGIN;

/**
 * UI tests for deleting contacts.
 * Validates contact removal and count update.
 */
public class DeleteContactTests extends AppManager {

    HomePage homePage;
    LoginPage loginPage;
    ContactPage contactPage;
    int countOfContacts;

    @BeforeMethod(alwaysRun = true)
    public void login() {
        homePage = new HomePage(getDriver());
        homePage.clickHeaderItem(LOGIN);
        loginPage = new LoginPage(getDriver());
        loginPage.typeLoginRegistrationForm(
                getProperty("base.properties", "login"),
                getProperty("base.properties", "password")
        );
        loginPage.clickBtnLoginForm();
        contactPage = new ContactPage(getDriver());
        countOfContacts = contactPage.getCountOfContacts();
    }

    @Test
    public void deleteFirstContactPositiveTest() {
        contactPage.deleteFirstContact();
        int countAfterDelete = contactPage.getCountOfContacts();
        Assert.assertEquals(countAfterDelete, countOfContacts - 1,
                "The number of contacts did not decrease after deletion");
    }
}