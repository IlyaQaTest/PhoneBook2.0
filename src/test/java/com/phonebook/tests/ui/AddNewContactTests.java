package com.phonebook.tests.ui;

import com.phonebook.model.Contact;
import com.phonebook.tests.utils_tests.data_providers.ContactDataProvider;
import com.phonebook.ui.manager.AppManager;
import com.phonebook.ui.pages.*;
import com.phonebook.ui.utils.HeaderMenuItem;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import static com.phonebook.model.factory.ContactFactory.positiveContact;

/**
 * UI tests for adding new contacts.
 * Covers positive and negative scenarios with data providers.
 */
public class AddNewContactTests extends AppManager {

    SoftAssert softAssert;
    HomePage homePage;
    LoginPage loginPage;
    ContactPage contactPage;
    AddPage addPage;
    int countOfContacts;

    @BeforeMethod(alwaysRun = true)
    public void login() {
        softAssert = new SoftAssert();
        homePage = new HomePage(getDriver());
        homePage.clickHeaderItem(HeaderMenuItem.LOGIN);

        loginPage = new LoginPage(getDriver());
        loginPage.typeLoginRegistrationForm("fuf@fuf.fuf", "000FuFu&^");
        loginPage.clickBtnLoginForm();

        contactPage = new ContactPage(getDriver());
        countOfContacts = contactPage.getCountOfContacts();

        contactPage.clickHeaderItem(HeaderMenuItem.ADD);
        addPage = new AddPage(getDriver());
    }

    @Test
    public void addNewContactPositiveTest() {
        addPage.typeContactForm(positiveContact());
        int countAfterAdd = contactPage.getCountOfContacts();
        Assert.assertEquals(countAfterAdd, countOfContacts + 1);
    }

    @Test(dataProvider = "dataProviderFromFile", dataProviderClass = ContactDataProvider.class)
    public void addNewContactPositiveTest_WithDataProvider(Contact contact) {
        addPage.typeContactForm(contact);
        int countAfterAdd = contactPage.getCountOfContacts();
        Assert.assertEquals(countAfterAdd, countOfContacts + 1);
    }

    @Test
    public void addNewContactPositiveTest_ClickLastContact() {
        Contact contact = positiveContact();
        addPage.typeContactForm(contact);
        Assert.assertTrue(contactPage.isContactPresent(contact));
    }

    @Test
    public void addNewContactPositiveTest_ScrollToLastContact() {
        Contact contact = positiveContact();
        addPage.typeContactForm(contact);
        contactPage.scrollToLastContact();
        contactPage.clickLastContact();
        String text = contactPage.getTextInContact();

        softAssert.assertTrue(text.contains(contact.getName()), "Validate Name in DetailCard");
        softAssert.assertTrue(text.contains(contact.getEmail()), "Validate Email in DetailCard");
        softAssert.assertTrue(text.contains(contact.getPhone()), "Validate Phone in DetailCard");
        softAssert.assertAll();
    }

    @Test(dataProvider = "dataProviderFromFile_WrongPhone", dataProviderClass = ContactDataProvider.class)
    public void addNewContactNegativeTest_WrongPhoneWithDP(Contact contact) {
        addPage.typeContactForm(contact);
        Assert.assertTrue(addPage.getAlertTextAndClose().contains("Phone not valid:"));
    }

    @Test(groups = "negative", dataProvider = "dataProviderFromFile_Wrong_EmptyField", dataProviderClass = ContactDataProvider.class)
    public void addNewContactNegativeTest_EmptyFieldWithDP(Contact contact) {
        addPage.typeContactForm(contact);
        Assert.assertTrue(addPage.isButtonSaveDisabled());
    }
}