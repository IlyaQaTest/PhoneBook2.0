package com.phonebook.tests.ui;

import com.phonebook.model.Contact;
import com.phonebook.model.factory.ContactFactory;
import com.phonebook.ui.manager.AppManager;
import com.phonebook.ui.pages.ContactPage;
import com.phonebook.ui.pages.HomePage;
import com.phonebook.ui.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import static com.phonebook.core.config.PropertiesReader.getProperty;
import static com.phonebook.ui.utils.HeaderMenuItem.LOGIN;

/**
 * UI tests for editing contacts.
 * Validates contact update and detail card content.
 */
public class EditContactTests extends AppManager {

    SoftAssert softAssert;
    HomePage homePage;
    LoginPage loginPage;
    ContactPage contactPage;

    @BeforeMethod(alwaysRun = true)
    public void login() {
        softAssert = new SoftAssert();
        homePage = new HomePage(getDriver());
        homePage.clickHeaderItem(LOGIN);
        loginPage = new LoginPage(getDriver());
        loginPage.typeLoginRegistrationForm(
                getProperty("base.properties", "login"),
                getProperty("base.properties", "password")
        );
        loginPage.clickBtnLoginForm();
        contactPage = new ContactPage(getDriver());
    }

    @Test(groups = {"smoke", "contact"})
    public void editFirstContactPositiveTest() {
        Contact contact = ContactFactory.positiveContact();
        contactPage.typeEditForm(contact);
        Assert.assertTrue(contactPage.isContactPresent(contact),
                "Contact with updated data not found in the list");
    }

    @Test
    public void editFirstContactPositiveTest_WithCardOfContact() {
        Contact contact = ContactFactory.positiveContact();
        contactPage.typeEditForm(contact);
        String text = contactPage.getTextInContact();
        softAssert.assertTrue(text.contains(contact.getName()), "Validate Name in DetailCard");
        softAssert.assertTrue(text.contains(contact.getEmail()), "Validate Email in DetailCard");
        softAssert.assertTrue(text.contains(contact.getPhone()), "Validate Phone in DetailCard");
        softAssert.assertAll();
    }
}