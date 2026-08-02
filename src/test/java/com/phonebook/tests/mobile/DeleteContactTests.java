package com.phonebook.tests.mobile;

import com.phonebook.api.client.AuthenticationController;
import com.phonebook.api.client.BaseApi;
import com.phonebook.api.client.ContactController;
import com.phonebook.api.dto.TokenDto;
import com.phonebook.mobile.screens.AddNewContactScreen;
import com.phonebook.mobile.screens.ContactListScreen;
import com.phonebook.mobile.screens.LoginRegistrationScreen;
import com.phonebook.model.Contact;
import com.phonebook.model.ContactsList;
import com.phonebook.model.User;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.phonebook.core.config.PropertiesReader.getProperty;
import static com.phonebook.model.factory.ContactFactory.positiveContact;

/**
 * Mobile tests for deleting contacts via UI with API verification.
 */
public class DeleteContactTests extends TestBase {

    private static final Logger logger = LoggerFactory.getLogger(DeleteContactTests.class);

    private AddNewContactScreen addNewContactScreen;
    private TokenDto tokenDto;
    private ContactsList contactsListBeforeDelete;

    @BeforeMethod
    @Step("Authenticate via API, ensure pre-conditions, and log in via Mobile UI")
    public void login() {
        User user = new User(
                getProperty("base.properties", "login"),
                getProperty("base.properties", "password")
        );
        Contact contact = positiveContact();

        logger.info("Authenticating user via API...");
        tokenDto = AuthenticationController.requestRegLogin(user, BaseApi.LOGIN_URL).as(TokenDto.class);

        Response response = ContactController.requestGetAllUserContacts(tokenDto.getToken());
        logger.info("API response status: {}", response.getStatusLine());

        if (response.getStatusCode() == 200) {
            contactsListBeforeDelete = response.as(ContactsList.class);
            if (contactsListBeforeDelete.getContacts().isEmpty()) {
                logger.info("No contacts found — adding one via API for test setup");
                ContactController.requestAddNewContact(contact, tokenDto.getToken());
                contactsListBeforeDelete = ContactController
                        .requestGetAllUserContacts(tokenDto.getToken())
                        .as(ContactsList.class);
            }
        }

        loginRegistrationScreen = new LoginRegistrationScreen(driver);
        if (loginRegistrationScreen.isLoginRegistrationFormDisplayed()) {
            loginRegistrationScreen.typeLoginRegistrationForm(user);
            loginRegistrationScreen.clickBtnLogin();
        }

        contactListScreen = new ContactListScreen(driver);
        addNewContactScreen = new AddNewContactScreen(driver);

        // CI emulator needs time to load contact list
        contactListScreen.waitForContactListNotEmpty();
    }

    @Test(description = "Delete middle contact and verify via API")
    @Step("Delete middle contact from list via UI and verify count decrease via API")
    public void deleteMiddleContactTest() {
        int sizeBeforeDelete = contactsListBeforeDelete.getContacts().size();
        logger.info("Initial contact count: {}", sizeBeforeDelete);

        contactListScreen.waitForContactListNotEmpty();
        contactListScreen.deleteContactMiddle();

        try {
            Thread.sleep(1500); // CI needs time to process deletion and sync with backend
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        int sizeAfterDelete = ContactController
                .requestGetAllUserContacts(tokenDto.getToken())
                .as(ContactsList.class)
                .getContacts()
                .size();

        logger.info("Contact count after deletion: {}", sizeAfterDelete);
        Assert.assertEquals(sizeAfterDelete, sizeBeforeDelete - 1,
                "Contact count in API should decrease by 1 after middle contact deletion");
    }

    @Test(description = "Delete first contact and verify via API")
    @Step("Delete first contact from list via UI and verify count decrease via API")
    public void deleteFirstContactTest() {
        int sizeBeforeDelete = contactsListBeforeDelete.getContacts().size();
        logger.info("Initial contact count: {}", sizeBeforeDelete);

        contactListScreen.waitForContactListNotEmpty();
        contactListScreen.deleteFirstContact();

        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        int sizeAfterDelete = ContactController
                .requestGetAllUserContacts(tokenDto.getToken())
                .as(ContactsList.class)
                .getContacts()
                .size();

        logger.info("Contact count after deletion: {}", sizeAfterDelete);
        Assert.assertEquals(sizeAfterDelete, sizeBeforeDelete - 1,
                "Contact count in API should decrease by 1 after first contact deletion");
    }

    @Test(description = "Delete last contact and verify via API")
    @Step("Add contact via UI, delete last contact, and verify count decrease via API")
    public void deleteLastContactTest() {
        contactListScreen.clickBtnPlus();

        Contact contact = positiveContact();
        addNewContactScreen.typeContactForm(contact);
        addNewContactScreen.clickBtnCreate();

        contactListScreen.waitForContactListNotEmpty();

        int sizeBeforeDelete = ContactController
                .requestGetAllUserContacts(tokenDto.getToken())
                .as(ContactsList.class)
                .getContacts()
                .size();

        contactListScreen.deleteLastContact();

        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        int sizeAfterDelete = ContactController
                .requestGetAllUserContacts(tokenDto.getToken())
                .as(ContactsList.class)
                .getContacts()
                .size();

        logger.info("Contact count after deletion: {}", sizeAfterDelete);
        Assert.assertEquals(sizeAfterDelete, sizeBeforeDelete - 1,
                "Contact count in API should decrease by 1 after last contact deletion");
    }
}