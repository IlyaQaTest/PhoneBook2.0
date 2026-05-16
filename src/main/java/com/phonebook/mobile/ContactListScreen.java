package com.phonebook.mobile;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.phonebook.utils.api.Direction;
import com.phonebook.utils.api.SwipeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.List;

/**
 * Screen object representing the Contact List screen.
 * Provides actions for navigating, validating, and modifying contacts.
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

    @AndroidFindBy(xpath = "(//*[@resource-id='com.sheygam.contactapp:id/rowContainer'])")
    private List<WebElement> contactListScreen;

    @AndroidFindBy(xpath = "//android.widget.Toast[@text='Contact was updated!']")
    private WebElement messageContactWasUpdated;

    @AndroidFindBy(xpath = "//android.widget.TextView[@resource-id='com.sheygam.contactapp:id/title' and @text='Logout']")
    private WebElement btnLogout;

    @AndroidFindBy(xpath = "//android.widget.TextView[@resource-id='com.sheygam.contactapp:id/title' and @text='Date picker']")
    private WebElement datePicker;

    // -------------------- ACTIONS --------------------

    public void clickDatePicker() {
        logger.info("Opening Date Picker");
        datePicker.click();
    }

    public void clickBtnLogout() {
        logger.info("Logging out");
        btnLogout.click();
    }

    public void clickMoreOptions() {
        logger.info("Opening More Options");
        moreOptions.click();
    }

    public void clickBtnPlus() {
        logger.info("Clicking Add Contact button");
        addContactBtn.click();
    }

    // -------------------- VALIDATION --------------------

    public boolean isBtnPlusPresent() {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(10))
                    .until(ExpectedConditions.visibilityOf(addContactBtn));
            return true;
        } catch (Exception e) {
            logger.debug("Add Contact button not found");
            return false;
        }
    }

    public boolean isContactListDisplayed() {
        try {
            return new WebDriverWait(driver, Duration.ofSeconds(15))
                    .until(ExpectedConditions.visibilityOf(title))
                    .isDisplayed();
        } catch (Exception e) {
            logger.debug("Contact list title not visible");
            return false;
        }
    }

    public boolean isContactListEmpty() {
        return contactRows.isEmpty();
    }

    public boolean validateTextInContactListScreenAfterRegistration(String expectedText, int timeout) {
        try {
            WebElement element = new WebDriverWait(driver, Duration.ofSeconds(timeout))
                    .until(ExpectedConditions.visibilityOfElementLocated(
                            By.xpath("//*[@text='" + expectedText + "']")
                    ));
            return element.isDisplayed();
        } catch (Exception e) {
            logger.debug("Text '{}' not found on screen", expectedText);
            return false;
        }
    }

    public boolean isTextInMessageContactWasAddedPresent(String text, int time) {
        return isTextInElementPresent(messageContactWasAdded, text, time);
    }

    public boolean isTextInMessageContactWasUpdatedPresent(String text, int time) {
        return isTextInElementPresent(messageContactWasUpdated, text, time);
    }

    // -------------------- CONTACT MANIPULATION --------------------

    public void deleteContactMiddle() {
        logger.info("Deleting middle contact");
        waitForPlusButton();
        swipeScreen(driver, Direction.RIGHT);
        btnYes.click();
    }

    public void deleteFirstContact() {
        logger.info("Deleting first contact");
        waitForPlusButton();
        swipeInsideElementDelete(driver, contactRows.get(0));
        btnYes.click();
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

    public WebElement getContact(int index) {
        return contactRows.get(index);
    }

    public void editFirstContact() {
        logger.info("Editing first contact");
        waitForPlusButton();
        swipeInsideElement(driver, contactListScreen.get(0), Direction.LEFT);
    }

    // -------------------- UTILS --------------------

    public void waitForContactListNotEmpty() {
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(d -> !contactRows.isEmpty());
    }

    private void waitForPlusButton() {
        new WebDriverWait(driver, Duration.ofSeconds(2))
                .until(ExpectedConditions.visibilityOf(btnPlus));
    }

    public void refresh() {
        logger.info("Refreshing contact list");
        swipeScreen(driver, Direction.DOWN);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException ignored) {}
    }
}