package com.phonebook.tests.mobile;

import com.phonebook.mobile.screens.ContactListScreen;
import com.phonebook.mobile.screens.EditContactScreen;
import com.phonebook.mobile.screens.ErrorScreen;
import com.phonebook.mobile.screens.LoginRegistrationScreen;
import com.phonebook.model.Contact;
import com.phonebook.model.User;
import io.qameta.allure.Issue;
import io.qameta.allure.Step;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.phonebook.core.config.PropertiesReader.getProperty;
import static com.phonebook.model.factory.ContactFactory.positiveContact;

/**
 * Mobile tests for editing contacts via UI.
 * Verifies that contact updates are correctly reflected in the app.
 */
@org.testng.annotations.Test(enabled = false)
public class EditContactTests extends TestBase {

    private static final Logger logger = LoggerFactory.getLogger(EditContactTests.class);

    private EditContactScreen editContactScreen;

    @BeforeMethod
    @Step("Login before each test and navigate to contact list")
    public void login() {
        loginRegistrationScreen = new LoginRegistrationScreen(driver);
        User user = new User(
                getProperty("base.properties", "login"),
                getProperty("base.properties", "password")
        );

        logger.info("Logging in with user: {}", user.getUsername());
        if (loginRegistrationScreen.isLoginRegistrationFormDisplayed()) {
            loginRegistrationScreen.typeLoginRegistrationForm(user);
            loginRegistrationScreen.clickBtnLogin();
        }

        contactListScreen = new ContactListScreen(driver);

        // CI emulator needs time to load contact list
        contactListScreen.waitForContactListNotEmpty();
    }

    @Test(enabled = false, description = "Positive test: Edit first contact and verify update message")
    @Step("Edit first contact and verify success message")
    public void editFirstContactPositiveTest() {
        Contact contact = positiveContact();
        logger.info("Editing first contact with new data: {}", contact);

        contactListScreen.waitForContactListNotEmpty();
        contactListScreen.editFirstContact();

        editContactScreen = new EditContactScreen(driver);
        editContactScreen.typeEditContactForm(contact);
        editContactScreen.clickBtnUpdate();

        boolean isUpdated = contactListScreen.isTextInMessageContactWasUpdatedPresent("Contact was updated!", 15);
        logger.info("Contact update message displayed: {}", isUpdated);

        Assert.assertTrue(isUpdated, "Contact update confirmation message not displayed");
    }

    @Test(enabled = false, description = "Negative test: Attempt to edit contact with empty name and verify error message")
    @Step("Try to update contact with empty name and verify validation error")
    @Issue("BUG-210")
    public void editContactNegativeEmptyNameTest() {
        logger.info("Starting negative test: edit contact with empty name");

        contactListScreen.waitForContactListNotEmpty();
        contactListScreen.editFirstContact();

        editContactScreen = new EditContactScreen(driver);
        editContactScreen.clearName();
        editContactScreen.clickBtnUpdate();

        Assert.assertTrue(new ErrorScreen(driver).validateTextInError("not be blank", 15),
                "Error message for empty name not displayed");

        Assert.assertFalse(editContactScreen.isEditScreenDisplayed(),
                "Edit screen should close after validation error");
    }
}
