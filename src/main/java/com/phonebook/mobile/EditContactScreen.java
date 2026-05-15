package com.phonebook.mobile;

import com.phonebook.model.Contact;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Screen object representing the Edit Contact screen.
 * Provides actions for modifying and updating an existing contact.
 */
public class EditContactScreen extends BaseScreen {

    private static final Logger logger = LoggerFactory.getLogger(EditContactScreen.class);

    public EditContactScreen(AppiumDriver driver) {
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

    @AndroidFindBy(id = "com.sheygam.contactapp:id/updateBtn")
    private WebElement btnUpdate;

    /**
     * Fills the edit contact form with new data.
     *
     * @param contact Updated contact data
     */
    public void typeEditContactForm(Contact contact) {
        logger.info("Editing contact with new data: {}", contact);

        clearFields();

        inputName.sendKeys(contact.getName());
        inputLastName.sendKeys(contact.getLastName());
        inputEmail.sendKeys(contact.getEmail());
        inputPhone.sendKeys(contact.getPhone());
        inputAddress.sendKeys(contact.getAddress());
        inputDescription.sendKeys(contact.getDescription());
    }

    /**
     * Clears all editable fields before typing new data.
     */
    private void clearFields() {
        logger.debug("Clearing contact form fields");

        inputName.clear();
        inputLastName.clear();
        inputEmail.clear();
        inputPhone.clear();
        inputAddress.clear();
        inputDescription.clear();
    }

    /**
     * Taps the Update button to save changes.
     */
    public void clickBtnUpdate() {
        logger.info("Clicking Update button");
        btnUpdate.click();
    }
}