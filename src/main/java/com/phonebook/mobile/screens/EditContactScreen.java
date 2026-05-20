package com.phonebook.mobile.screens;

import com.phonebook.model.Contact;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

/**
 * Represents the "Edit Contact" screen in the mobile application.
 * Provides methods to update existing contact details.
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
     * Fills the edit contact form with updated data.
     *
     * @param contact the contact object containing new details
     */
    public void typeEditContactForm(Contact contact) {
        logger.info("Editing contact: {}", contact.getName());
        inputName.sendKeys(contact.getName());
        inputLastName.sendKeys(contact.getLastName());
        inputEmail.sendKeys(contact.getEmail());
        inputPhone.sendKeys(contact.getPhone());
        inputAddress.sendKeys(contact.getAddress());
        inputDescription.sendKeys(contact.getDescription());
    }

    /**
     * Clicks the "Update" button to save changes.
     */
    public void clickBtnUpdate() {
        logger.info("Clicking 'Update' button to save contact changes");
        btnUpdate.click();
    }

    public void clickFieldInputName() {
        inputName.click();
    }


    @Step("Check if error message '{expectedText}' is displayed on screen")
    public boolean isErrorMessageDisplayed(String expectedText) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement errorElement = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(AppiumBy.id("android:id/message"))
            );
            String actualText = errorElement.getText();
            logger.info("Error message displayed: {}", actualText);
            return actualText.toLowerCase().contains(expectedText.toLowerCase());
        } catch (Exception e) {
            logger.warn("Error message not found or not visible: {}", e.getMessage());
            return false;
        }
    }

    @Step("Check if Edit Contact screen is still displayed")
    public boolean isEditScreenDisplayed() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement updateButton = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(AppiumBy.id("com.sheygam.contactapp:id/buttonUpdate"))
            );
            return updateButton.isDisplayed();
        } catch (Exception e) {
            logger.warn("Edit Contact screen not visible: {}", e.getMessage());
            return false;
        }
    }

    @Step("Clear the Name field on Edit Contact screen")
    public void clearName() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement nameField = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(
                            AppiumBy.id("com.sheygam.contactapp:id/inputName")
                    )
            );
            nameField.clear();
            logger.info("Name field cleared successfully");
        } catch (Exception e) {
            logger.error("Failed to clear Name field: {}", e.getMessage());
            throw new RuntimeException("Unable to clear Name field", e);
        }
    }
}