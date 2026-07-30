package com.phonebook.ui.pages;

import com.phonebook.model.Contact;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.*;
import org.openqa.selenium.support.*;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;
import java.util.List;

/**
 * Contact page object.
 * Handles contact list actions and edit form operations.
 */
public class ContactPage extends BasePage {

    public ContactPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, 10), this);
    }

    @FindBy(xpath = "//button[text()='Sign Out']")
    WebElement btnSignOut;

    @FindBy(xpath = "//*[text()='ADD']")
    WebElement btnAdd;

    @FindBy(xpath = "//h1[text()=' No Contacts here!']")
    WebElement contactPageMessage;

    @FindBy(className = "contact-item_card__2SOIM")
    List<WebElement> contactsList;

    @FindBy(xpath = "//div[@class='contact-item_card__2SOIM'][last()]")
    WebElement lastContact;

    @FindBy(xpath = "//div[@class='contact-item-detailed_card__50dTS']")
    WebElement itemDetailCard;

    @FindBy(xpath = "//button[text()='Remove']")
    WebElement btnRemove;

    @FindBy(xpath = "//div[@class='contact-page_leftdiv__yhyke']/div")
    WebElement divListContacts;

    @FindBy(xpath = "//button[text()='Edit']")
    WebElement btnEdit;

    @FindBy(xpath = "//input[@placeholder='Name']")
    WebElement inputName;

    @FindBy(xpath = "//input[@placeholder='Last Name']")
    WebElement inputLastName;

    @FindBy(xpath = "//input[@placeholder='Phone']")
    WebElement inputPhone;

    @FindBy(xpath = "//div[@class='form_form__FOqHs']/input[4]")
    WebElement inputEmail;

    @FindBy(xpath = "//div[@class='form_form__FOqHs']/input[5]")
    WebElement inputAddress;

    @FindBy(xpath = "//div[@class='form_form__FOqHs']/input[6]")
    WebElement inputDescription;

    @FindBy(xpath = "//button[text()='Save']")
    WebElement btnSave;

    // Get text from contact detail card
    public String getTextInContact() {
        waitForElementVisible(itemDetailCard);
        return itemDetailCard.getText();
    }

    // Check if contact is present in list
    public boolean isContactPresent(Contact contact) {
        wait.until(ExpectedConditions.visibilityOfAllElements(contactsList));
        for (WebElement element : contactsList) {
            if (element.getText().contains(contact.getName()) &&
                    element.getText().contains(contact.getPhone())) {
                return true;
            }
        }
        return false;
    }

    // Scroll to last contact in list
    public void scrollToLastContact() {
        if (!contactsList.isEmpty()) {
            int deltaY = divListContacts.getSize().getHeight();
            WheelInput.ScrollOrigin origin = WheelInput.ScrollOrigin.fromElement(contactsList.get(0));
            waitForElementVisible(divListContacts);
            new Actions(driver).scrollFromOrigin(origin, 0, deltaY).perform();
        }
    }

    // Click last contact in list
    public void clickLastContact() {
        waitForElementVisible(lastContact);
        lastContact.click();
    }

    // Get total number of contacts
    public int getCountOfContacts() {
        waitForElementVisible(divListContacts);
        return contactsList.size();
    }

    // Check text presence in page message
    public boolean isTextInContactPageMessagePresent(String text) {
        return isTextInElementPresent(contactPageMessage, text);
    }

    // Check text presence in Sign Out button
    public boolean isTextInBtnSignOutPresent(String text) {
        return isTextInElementPresent(btnSignOut, text);
    }

    // Check text presence in Add button
    public boolean isTextInBtnAddPresent(String text) {
        return isTextInElementPresent(btnAdd, text);
    }

    // Delete first contact from list
    public void deleteFirstContact() {
        int initialCount = getCountOfContacts();
        clickLastContact();
        btnRemove.click();
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(d -> getCountOfContacts() == initialCount - 1);
    }

    // Fill edit form and save changes
    public void typeEditForm(Contact contact) {
        wait.until(ExpectedConditions.elementToBeClickable(contactsList.get(0))).click();
        waitForElementVisible(btnEdit);
        btnEdit.click();

        waitForElementVisible(inputName);
        inputName.clear();
        inputName.sendKeys(contact.getName());

        inputLastName.clear();
        inputLastName.sendKeys(contact.getLastName());

        inputPhone.clear();
        inputPhone.sendKeys(contact.getPhone());

        inputEmail.click();
        inputEmail.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE);
        inputEmail.sendKeys(contact.getEmail());

        inputAddress.clear();
        inputAddress.sendKeys(contact.getAddress());

        waitForElementVisible(inputDescription);
        inputDescription.clear();
        inputDescription.sendKeys(contact.getDescription());

        wait.until(ExpectedConditions.elementToBeClickable(btnSave)).click();
        wait.until(ExpectedConditions.invisibilityOf(btnSave));
    }
}