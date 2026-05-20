package com.phonebook.mobile.screens;

import com.phonebook.model.Contact;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Represents the "Add New Contact" screen in the mobile application.
 * Provides methods to fill in contact details and submit the form.
 */
public class AddNewContactScreen extends BaseScreen {

    private static final Logger logger = LoggerFactory.getLogger(AddNewContactScreen.class);

    public AddNewContactScreen(AppiumDriver driver) {
        super(driver);
    }

    @AndroidFindBy(id = "com.sheygam.contactapp:id/inputName")
    private WebElement inputName;

    @AndroidFindBy(id = "com.sheygam.contactapp:id/inputLastName")
    private WebElement inputLastName;

    @AndroidFindBy(id = "com.sheygam.contactapp:id/inputEmail")
    private WebElement inputEmail;

    @AndroidFindBy(id = "com.sheygam.contactapp:id/inputPhone")
    private WebElement inputPhone;

    @AndroidFindBy(id = "com.sheygam.contactapp:id/inputAddress")
    private WebElement inputAddress;

    @AndroidFindBy(id = "com.sheygam.contactapp:id/inputDesc")
    private WebElement inputDescription;

    @AndroidFindBy(id = "com.sheygam.contactapp:id/createBtn")
    private WebElement btnCreate;

    /**
     * Fills the contact form with data from the provided Contact object.
     *
     * @param contact the contact data to input
     */
    public void typeContactForm(Contact contact) {
        logger.info("Typing contact form for: {}", contact.getName());
        inputName.sendKeys(contact.getName());
        inputLastName.sendKeys(contact.getLastName());
        inputEmail.sendKeys(contact.getEmail());
        inputPhone.sendKeys(contact.getPhone());
        inputAddress.sendKeys(contact.getAddress());
        inputDescription.sendKeys(contact.getDescription());
    }

    /**
     * Clicks the "Create" button to submit the form.
     */
    public void clickBtnCreate() {
        logger.info("Clicking 'Create' button to add new contact");
        btnCreate.click();
    }
}