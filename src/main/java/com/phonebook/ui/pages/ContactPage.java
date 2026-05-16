package com.phonebook.ui.pages;

import com.phonebook.model.Contact;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.WheelInput;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.List;

/**
 * Represents the Contacts page of the PhoneBook web application.
 * Provides actions for adding, editing, removing and validating contacts.
 */
public class ContactPage extends BasePage {

    private static final Logger logger = LoggerFactory.getLogger(ContactPage.class);

    public ContactPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, 10), this);
        logger.info("ContactPage initialized successfully.");
    }

    @FindBy(xpath = "//button[text()='Sign Out']")
    private WebElement btnSignOut;

    @FindBy(xpath = "//*[text()='ADD']")
    private WebElement btnAdd;

    @FindBy(xpath = "//h1[text()=' No Contacts here!']")
    private WebElement contactPageMessage;

    @FindBy(className = "contact-item_card__2SOIM")
    private List<WebElement> contactsList;

    @FindBy(xpath = "//div[@class='contact-item_card__2SOIM'][last()]")
    private WebElement lastContact;

    @FindBy(xpath = "//div[@class='contact-item-detailed_card__50dTS']")
    private WebElement itemDetailCard;

    @FindBy(xpath = "//button[text()='Remove']")
    private WebElement btnRemove;

    @FindBy(xpath = "//div[@class='contact-page_leftdiv__yhyke']/div")
    private WebElement divListContacts;

    @FindBy(xpath = "//button[text()='Edit']")
    private WebElement btnEdit;

    @FindBy(xpath = "//input[@placeholder='Name']")
    private WebElement inputName;

    @FindBy(xpath = "//input[@placeholder='Last Name']")
    private WebElement inputLastName;

    @FindBy(xpath = "//input[@placeholder='Phone']")
    private WebElement inputPhone;

    @FindBy(xpath = "//div[@class='form_form__FOqHs']/input[4]")
    private WebElement inputEmail;

    @FindBy(xpath = "//div[@class='form_form__FOqHs']/input[5]")
    private WebElement inputAddress;

    @FindBy(xpath = "//div[@class='form_form__FOqHs']/input[6]")
    private WebElement inputDescription;

    // -----------------------------
    // ACTION METHODS
    // -----------------------------

    public void clickAddButton() {
        wait.until(ExpectedConditions.elementToBeClickable(btnAdd)).click();
        logger.info("Clicked ADD button.");
    }

    public void clickLastContact() {
        wait.until(ExpectedConditions.elementToBeClickable(lastContact)).click();
        logger.info("Opened last contact details.");
    }

    public void removeContact() {
        wait.until(ExpectedConditions.elementToBeClickable(btnRemove)).click();
        logger.info("Clicked Remove button.");
    }

    public void clickEditButton() {
        wait.until(ExpectedConditions.elementToBeClickable(btnEdit)).click();
        logger.info("Clicked Edit button.");
    }

    public void fillEditForm(Contact contact) {
        clearAndType(inputName, contact.getName());
        clearAndType(inputLastName, contact.getLastName());
        clearAndType(inputPhone, contact.getPhone());
        clearAndType(inputEmail, contact.getEmail());
        clearAndType(inputAddress, contact.getAddress());
        clearAndType(inputDescription, contact.getDescription());
        logger.info("Filled edit form for contact: {} {}", contact.getName(), contact.getLastName());
    }

    private void clearAndType(WebElement element, String text) {
        wait.until(ExpectedConditions.visibilityOf(element));
        element.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        element.sendKeys(text);
    }

    // -----------------------------
    // VALIDATION METHODS
    // -----------------------------

    public boolean isNoContactsMessageDisplayed() {
        return contactPageMessage.isDisplayed();
    }

    public int getContactsCount() {
        return contactsList.size();
    }

    public boolean isContactDetailsVisible() {
        return itemDetailCard.isDisplayed();
    }

    public boolean isSignOutButtonVisible() {
        return btnSignOut.isDisplayed();
    }
}