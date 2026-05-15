package com.phonebook.mobile;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Screen object representing the Update Contact screen.
 * Provides actions for modifying the contact name and submitting updates.
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
     * Clears the name field before entering new data.
     */
    public void clearName() {
        logger.debug("Clearing name field");
        inputName.clear();
    }

    /**
     * Types a new name into the name field.
     *
     * @param name New contact name
     */
    public void typeName(String name) {
        logger.info("Typing new name: {}", name);
        inputName.sendKeys(name);
    }

    /**
     * Taps the Update button to save changes.
     */
    public void clickUpdateBtn() {
        logger.info("Clicking Update button");
        btnUpdate.click();
    }
}