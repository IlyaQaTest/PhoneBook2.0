package com.phonebook.mobile.screens;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Represents the "Update Contact" screen in the mobile application.
 * Provides methods to modify contact details and confirm updates.
 */
public class UpdateContactScreen extends BaseScreen {

    private static final Logger logger = LoggerFactory.getLogger(UpdateContactScreen.class);

    public UpdateContactScreen(AppiumDriver driver) {
        super(driver);
    }

    @AndroidFindBy(id = "com.sheygam.contactapp:id/updateBtn")
    private WebElement btnUpdate;

    @AndroidFindBy(id = "com.sheygam.contactapp:id/inputName")
    private WebElement inputName;

    /**
     * Clears the name field before entering a new value.
     */
    public void clearName() {
        logger.info("Clearing contact name field");
        inputName.clear();
    }

    /**
     * Types a new name into the contact name field.
     *
     * @param name the new contact name
     */
    public void typeName(String name) {
        logger.info("Typing new contact name: {}", name);
        inputName.sendKeys(name);
    }

    /**
     * Clicks the "Update" button to save changes.
     */
    public void clickUpdateBtn() {
        logger.info("Clicking 'Update' button to save contact changes");
        btnUpdate.click();
    }
}