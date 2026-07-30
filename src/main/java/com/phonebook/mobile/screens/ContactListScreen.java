package com.phonebook.mobile.screens;

import com.phonebook.core.enums.Direction;
import com.phonebook.mobile.helpers.SwipeUtils;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.List;

/**
 * Represents the Contact List screen in the mobile application.
 * Provides methods for managing contacts, refreshing the list, and performing swipe actions.
 */
public class ContactListScreen extends BaseScreen implements SwipeUtils {

    private static final Logger logger = LoggerFactory.getLogger(ContactListScreen.class);

    public ContactListScreen(AppiumDriver driver) {
        super(driver);
    }

    @AndroidFindBy(xpath = "//android.widget.ImageView[@content-desc='More options']")
    private WebElement moreOptions;

    @AndroidFindBy(xpath = "//*[@text='Contact list']")
    private WebElement title;

    @AndroidFindBy(id = "com.sheygam.contactapp:id/add_contact_btn")
    private WebElement addContactBtn;

    @AndroidFindBy(id = "com.sheygam.contactapp:id/rowContainer")
    private List<WebElement> contactRows;

    @AndroidFindBy(xpath = "//android.widget.Toast[@text='Contact was added!']")
    private WebElement messageContactWasAdded;

    @AndroidFindBy(xpath = "//android.widget.ImageButton[@content-desc='add']")
    private WebElement btnPlus;

    @AndroidFindBy(id = "android:id/button1")
    private WebElement btnYes;

    @AndroidFindBy(id = "com.sheygam.contactapp:id/emptyTxt")
    private WebElement noContacts;


    @AndroidFindBy(xpath = "//android.widget.Toast[@text='Contact was updated!']")
    private WebElement messageContactWasUpdated;

    @AndroidFindBy(xpath = "//android.widget.TextView[@resource-id='com.sheygam.contactapp:id/title' and @text='Logout']")
    private WebElement btnLogout;

    @AndroidFindBy(xpath = "//android.widget.TextView[@resource-id='com.sheygam.contactapp:id/title' and @text='Date picker']")
    private WebElement datePicker;

    @AndroidFindBy(id = "com.sheygam.contactapp:id/rowName")
    private WebElement rowName;

    public void clickRowName(String rowName) {
        logger.info("Clicking row name {}", rowName);
        WebElement rowNameElement = driver.findElement(By.id(rowName));
        rowNameElement.click();
    }

    public void clickDatePicker() {
        logger.info("Clicking 'Date Picker' button");
        datePicker.click();
    }

    public void clickBtnLogout() {
        logger.info("Clicking 'Logout' button");
        btnLogout.click();
    }

    public void clickMoreOptions() {
        logger.info("Opening 'More Options' menu");
        moreOptions.click();
    }

    public void clickBtnPlus() {
        logger.info("Clicking 'Add Contact' button");
        addContactBtn.click();
    }

    public boolean isBtnPlusPresent() {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(10))
                    .until(ExpectedConditions.visibilityOf(addContactBtn));
            return true;
        } catch (Exception e) {
            logger.warn("Add Contact button not found: {}", e.getMessage());
            return false;
        }
    }

    public boolean isContactListDisplayed() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            return wait.until(ExpectedConditions.visibilityOf(title)).isDisplayed();
        } catch (Exception e) {
            logger.warn("Contact list title not visible: {}", e.getMessage());
            return false;
        }
    }

    public boolean isContactListEmpty() {
        return contactRows.isEmpty();
    }

    public boolean validateTextInContactListScreenAfterRegistration(String expectedText, int timeout) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//*[@text='" + expectedText + "']")
            ));
            return element.isDisplayed();
        } catch (Exception e) {
            logger.warn("Text '{}' not found on Contact List screen: {}", expectedText, e.getMessage());
            return false;
        }
    }

    public boolean isTextInMessageContactWasAddedPresent(String text, int time) {
        return isTextInElementPresent(messageContactWasAdded, text, time);
    }

    public void deleteContactMiddle() {
        logger.info("Deleting middle contact");
        new WebDriverWait(driver, Duration.ofSeconds(2))
                .until(ExpectedConditions.visibilityOf(btnPlus));
        swipeScreen(driver, Direction.RIGHT);
        btnYes.click();
    }

    public void deleteFirstContact() {
        logger.info("Deleting first contact");
        new WebDriverWait(driver, Duration.ofSeconds(2))
                .until(ExpectedConditions.visibilityOf(btnPlus));
        swipeInsideElementDelete(driver, contactRows.get(0));
        btnYes.click();
    }

    public WebElement getContact(int index) {
        return contactRows.get(index);
    }

    public void deleteLastContact() {
        if (contactRows.isEmpty()) {
            throw new RuntimeException("No contacts found");
        }
        logger.info("Deleting last contact");
        WebElement last = contactRows.get(contactRows.size() - 1);
        swipeInsideElementDelete(driver, last);
        btnYes.click();
    }

    public void waitForContactListNotEmpty() {
        logger.debug("Waiting for contact list to be populated");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(d -> !contactRows.isEmpty());
    }



    public boolean isTextInMessageContactWasUpdatedPresent(String text, int time) {
        return isTextInElementPresent(messageContactWasUpdated, text, time);
    }

    public void editFirstContact() {
        logger.info("Editing first contact");
        new WebDriverWait(driver, Duration.ofSeconds(2))
                .until(ExpectedConditions.visibilityOf(btnPlus));
        swipeInsideElement(driver, contactRows.get(0), Direction.LEFT);
    }
    /**
     * Returns the name of the contact at the specified index.
     * Used for UI validation in mobile tests.
     */
    public String getContactName(int index) {
        logger.info("Getting contact name at index {}", index);
        By contactNameLocator = By.id("com.sheygam.contactapp:id/rowName");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        List<WebElement> contacts = wait.until(
                ExpectedConditions.visibilityOfAllElementsLocatedBy(contactNameLocator)
        );
        return contacts.get(index).getText();
    }
}