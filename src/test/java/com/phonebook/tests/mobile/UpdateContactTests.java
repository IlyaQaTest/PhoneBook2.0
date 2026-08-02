package com.phonebook.tests.mobile;

import com.phonebook.mobile.screens.AddNewContactScreen;
import com.phonebook.mobile.screens.ContactListScreen;
import com.phonebook.mobile.screens.LoginRegistrationScreen;
import com.phonebook.mobile.screens.UpdateContactScreen;
import com.phonebook.model.Contact;
import com.phonebook.model.User;
import io.qameta.allure.Step;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.phonebook.core.config.PropertiesReader.getProperty;
import static com.phonebook.model.factory.ContactFactory.positiveContact;

/**
 * Mobile test verifying contact update functionality via UI only (no API calls).
 */
public class UpdateContactTests extends TestBase {

    private static final Logger logger = LoggerFactory.getLogger(UpdateContactTests.class);

    private AddNewContactScreen addNewContactScreen;
    private UpdateContactScreen updateContactScreen;

    @BeforeMethod
    @Step("Login before each test and navigate to contact list screen")
    public void login() {
        User user = new User(
                getProperty("base.properties", "login"),
                getProperty("base.properties", "password")
        );

        logger.info("Logging in via mobile UI...");
        loginRegistrationScreen = new LoginRegistrationScreen(driver);

        if (loginRegistrationScreen.isLoginRegistrationFormDisplayed()) {
            loginRegistrationScreen.typeLoginRegistrationForm(user);
            loginRegistrationScreen.clickBtnLogin();
        }

        contactListScreen = new ContactListScreen(driver);
        addNewContactScreen = new AddNewContactScreen(driver);
    }

    @Test(description = "Positive test: Update contact name via mobile UI")
    @Step("Update first contact name and verify changes on screen")
    public void updateContactPositiveTest() {
        logger.info("Starting test: updateContactPositiveTest");

        // Create contact if list is empty
        if (contactListScreen.isContactListEmpty()) {
            logger.info("No contacts found — creating one for test setup");
            contactListScreen.clickBtnPlus();

            Contact contact = positiveContact();
            addNewContactScreen.typeContactForm(contact);
            addNewContactScreen.clickBtnCreate();
        }

        // Ensure list is fully loaded before interacting
        contactListScreen.waitForContactListNotEmpty();

        String oldName = contactListScreen.getContactName(0);
        logger.info("Old contact name: {}", oldName);

        // CI emulator needs time before swipe
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        contactListScreen.swipeInsideElementUpdate(driver, contactListScreen.getContact(0));
        updateContactScreen = new UpdateContactScreen(driver);

        String newName = "UpdatedName_" + System.currentTimeMillis();
        updateContactScreen.clearName();
        updateContactScreen.typeName(newName);
        updateContactScreen.clickUpdateBtn();

        // CI emulator needs time to refresh list
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        String updatedName = contactListScreen.getContactName(0);
        logger.info("Updated contact name: {}", updatedName);

        Assert.assertTrue(updatedName.startsWith(newName),
                "Contact name was not updated correctly!");
    }
}