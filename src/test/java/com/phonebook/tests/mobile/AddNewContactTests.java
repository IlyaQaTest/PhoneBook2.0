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

    private LoginRegistrationScreen loginRegistrationScreen;
    private ContactListScreen contactListScreen;
    private AddNewContactScreen addNewContactScreen;

    @BeforeMethod
    @Step("Login before each test and navigate to Add New Contact screen")
    public void login() {
        loginRegistrationScreen = new LoginRegistrationScreen(driver);
        User user = new User(
                getProperty("base.properties", "login_1"),
                getProperty("base.properties", "password_1")
        );

        logger.info("Logging in with user: {}", user.getUsername());
        loginRegistrationScreen.typeLoginRegistrationForm(user);
        loginRegistrationScreen.clickBtnLogin();

        contactListScreen = new ContactListScreen(driver);
        contactListScreen.clickBtnPlus();

        addNewContactScreen = new AddNewContactScreen(driver);
    }

    @Test(description = "Positive test: Add a new contact successfully")
    @Step("Add a new contact and verify success message")
    public void addNewContactTest() {
        Contact contact = positiveContact();
        addNewContactScreen.typeContactForm(contact);
        addNewContactScreen.clickBtnCreate();

        Assert.assertTrue(contactListScreen.isTextInMessageContactWasAddedPresent("Contact was added", 5),
                "Contact creation message not displayed");
        logger.info("Contact '{}' added successfully", contact.getName());
    }

    @Test(description = "Negative test: Invalid phone length")
    @Step("Try to add contact with invalid phone length and verify error message")
    public void addNewContactNegative_WrongLengthPhoneTest() {
        Contact contact = positiveContact();
        contact.setPhone("098765443");
        addNewContactScreen.typeContactForm(contact);
        addNewContactScreen.clickBtnCreate();

        Assert.assertTrue(new ErrorScreen(driver).validateTextInError("min 10, max 15!", 5),
                "Error message for invalid phone length not displayed");
        logger.warn("Validation failed for phone length");
    }

    @Test(description = "Negative test: Empty name field")
    @Step("Try to add contact with empty name and verify error message")
    public void addNewContactNegative_EmptyNameTest() {
        Contact contact = positiveContact();
        contact.setName("");
        addNewContactScreen.typeContactForm(contact);
        addNewContactScreen.clickBtnCreate();

        Assert.assertTrue(new ErrorScreen(driver).validateTextInError("not be blank", 5),
                "Error message for empty name not displayed");
        logger.warn("Validation failed for empty name");
    }

    @Test(description = "Negative test: Empty last name field")
    @Step("Try to add contact with empty last name and verify error message")
    public void addNewContactNegative_EmptyLastNameTest() {
        Contact contact = positiveContact();
        contact.setLastName("");
        addNewContactScreen.typeContactForm(contact);
        addNewContactScreen.clickBtnCreate();

        Assert.assertTrue(new ErrorScreen(driver).validateTextInError("not be blank", 5),
                "Error message for empty last name not displayed");
        logger.warn("Validation failed for empty last name");
    }

    @Test(description = "Negative test: Empty address field")
    @Step("Try to add contact with empty address and verify error message")
    public void addNewContactNegative_EmptyAddressTest() {
        Contact contact = positiveContact();
        contact.setAddress("");
        addNewContactScreen.typeContactForm(contact);
        addNewContactScreen.clickBtnCreate();

        Assert.assertTrue(new ErrorScreen(driver).validateTextInError("not be blank", 5),
                "Error message for empty address not displayed");
        logger.warn("Validation failed for empty address");
    }

    @Test(description = "Negative test: Empty phone field")
    @Step("Try to add contact with empty phone and verify error message")
    public void addContactNegative_EmptyPhoneTest() {
        Contact contact = Contact.builder()
                .name("Ivan")
                .lastName("Ivanov")
                .phone("")
                .email("ivan@mail.com")
                .address("Haifa")
                .description("QA")
                .build();

        addNewContactScreen.typeContactForm(contact);
        addNewContactScreen.clickBtnCreate();

        Assert.assertTrue(new ErrorScreen(driver).validateTextInError("min 10, max 15!", 5),
                "Error message for empty phone not displayed");
        logger.warn("Validation failed for empty phone");
    }

    @Test(description = "Negative test: Invalid email format")
    @Step("Try to add contact with invalid email and verify error message")
    public void addContactNegative_InvalidEmailTest() {
        Contact contact = Contact.builder()
                .name("Ivan")
                .lastName("Ivanov")
                .phone("1234567890")
                .email("ivan_at_mail.com")
                .address("USSR")
                .description("QA")
                .build();

        addNewContactScreen.typeContactForm(contact);
        addNewContactScreen.clickBtnCreate();

        Assert.assertTrue(new ErrorScreen(driver).validateTextInError("must be a well-formed email address", 5),
                "Error message for invalid email not displayed");
        logger.warn("Validation failed for invalid email format");
    }
}