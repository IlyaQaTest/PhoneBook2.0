package com.phonebook.tests.mobile;

import com.phonebook.mobile.screens.AddNewContactScreen;
import com.phonebook.mobile.screens.ContactListScreen;
import com.phonebook.mobile.screens.ErrorScreen;
import com.phonebook.mobile.screens.LoginRegistrationScreen;
import com.phonebook.model.Contact;
import com.phonebook.model.User;
import io.qameta.allure.Step;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.phonebook.core.config.PropertiesReader.getProperty;
import static com.phonebook.model.factory.ContactFactory.positiveContact;

/**
 * Test suite for adding new contacts in the mobile application.
 * Includes positive and negative test cases for contact creation.
 */
public class AddNewContactTests extends TestBase {

    private static final Logger logger = LoggerFactory.getLogger(AddNewContactTests.class);
    private static final String APP_PACKAGE = "com.sheygam.contactapp";

    private AddNewContactScreen addNewContactScreen;

    @BeforeMethod
    @Step("Ensure user is logged in and navigate to Add New Contact screen")
    public void setUp() {
        try {
            Thread.sleep(500); // Give CI emulator time to render
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        ErrorScreen errorScreen = new ErrorScreen(driver);
        if (errorScreen.isErrorDisplayed()) {
            logger.info("Dismissing leftover error dialog before starting test");
            errorScreen.clickBtnErrorOk();
        }

        loginRegistrationScreen = new LoginRegistrationScreen(driver);
        contactListScreen = new ContactListScreen(driver);

        if (loginRegistrationScreen.isLoginRegistrationFormDisplayed()) {
            User user = new User(
                    getProperty("base.properties", "login_1"),
                    getProperty("base.properties", "password_1")
            );

            logger.info("Logging in with user: {}", user.getUsername());
            loginRegistrationScreen.typeLoginRegistrationForm(user);
            loginRegistrationScreen.clickBtnLogin();
        }

        contactListScreen.isContactListDisplayed();
        contactListScreen.clickBtnPlus();
        addNewContactScreen = new AddNewContactScreen(driver);
    }

    @AfterMethod(alwaysRun = true)
    @Step("Reset application state after each test")
    public void postCondition() {
        if (driver != null) {
            try {
                driver.terminateApp(APP_PACKAGE);
                driver.activateApp(APP_PACKAGE);

                try {
                    Thread.sleep(1500); // CI emulator needs time to stabilize
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }

                logger.info("Application successfully restarted in postCondition");
            } catch (Exception e) {
                logger.warn("Failed to restart application in postCondition: {}", e.getMessage());
            }
        }
    }

    @Test(description = "Positive test: Add a new contact successfully")
    @Step("Add a new contact and verify success message")
    public void addNewContactTest() {
        Contact contact = positiveContact();
        addNewContactScreen.typeContactForm(contact);
        addNewContactScreen.clickBtnCreate();

        Assert.assertTrue(contactListScreen.isTextInMessageContactWasAddedPresent("Contact was added", 15),
                "Contact creation message not displayed");
    }

    @Test(description = "Negative test: Invalid phone length")
    @Step("Try to add contact with invalid phone length and verify error message")
    public void addNewContactNegative_WrongLengthPhoneTest() {
        Contact contact = positiveContact();
        contact.setPhone("098765443");
        addNewContactScreen.typeContactForm(contact);
        addNewContactScreen.clickBtnCreate();

        ErrorScreen errorScreen = new ErrorScreen(driver);
        Assert.assertTrue(errorScreen.validateTextInError("min 10, max 15!", 10),
                "Error message for invalid phone length not displayed");
        errorScreen.clickBtnErrorOk();
    }

    @Test(description = "Negative test: Empty name field")
    @Step("Try to add contact with empty name and verify error message")
    public void addNewContactNegative_EmptyNameTest() {
        Contact contact = positiveContact();
        contact.setName("");
        addNewContactScreen.typeContactForm(contact);
        addNewContactScreen.clickBtnCreate();

        ErrorScreen errorScreen = new ErrorScreen(driver);
        Assert.assertTrue(errorScreen.validateTextInError("not be blank", 10),
                "Error message for empty name not displayed");
        errorScreen.clickBtnErrorOk();
    }

    @Test(description = "Negative test: Empty last name field")
    @Step("Try to add contact with empty last name and verify error message")
    public void addNewContactNegative_EmptyLastNameTest() {
        Contact contact = positiveContact();
        contact.setLastName("");
        addNewContactScreen.typeContactForm(contact);
        addNewContactScreen.clickBtnCreate();

        ErrorScreen errorScreen = new ErrorScreen(driver);
        Assert.assertTrue(errorScreen.validateTextInError("not be blank", 10),
                "Error message for empty last name not displayed");
        errorScreen.clickBtnErrorOk();
    }

    @Test(description = "Negative test: Empty address field")
    @Step("Try to add contact with empty address and verify error message")
    public void addNewContactNegative_EmptyAddressTest() {
        Contact contact = positiveContact();
        contact.setAddress("");
        addNewContactScreen.typeContactForm(contact);
        addNewContactScreen.clickBtnCreate();

        ErrorScreen errorScreen = new ErrorScreen(driver);
        Assert.assertTrue(errorScreen.validateTextInError("not be blank", 10),
                "Error message for empty address not displayed");
        errorScreen.clickBtnErrorOk();
    }

    @Test(description = "Negative test: Empty phone field")
    @Step("Try to add contact with empty phone and verify error message")
    public void addContactNegative_EmptyPhoneTest() {
        Contact contact = positiveContact();
        contact.setPhone("");
        addNewContactScreen.typeContactForm(contact);
        addNewContactScreen.clickBtnCreate();

        ErrorScreen errorScreen = new ErrorScreen(driver);
        Assert.assertTrue(errorScreen.validateTextInError("min 10, max 15!", 10),
                "Error message for empty phone not displayed");
        errorScreen.clickBtnErrorOk();
    }

    @Test(description = "Negative test: Invalid email format")
    @Step("Try to add contact with invalid email and verify error message")
    public void addContactNegative_InvalidEmailTest() {
        Contact contact = positiveContact();
        contact.setEmail("invalid_email_format");
        addNewContactScreen.typeContactForm(contact);
        addNewContactScreen.clickBtnCreate();

        ErrorScreen errorScreen = new ErrorScreen(driver);
        Assert.assertTrue(errorScreen.validateTextInError("must be a well-formed email address", 10),
                "Error message for invalid email not displayed");
        errorScreen.clickBtnErrorOk();
    }
}