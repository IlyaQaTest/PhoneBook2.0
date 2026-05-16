package com.phonebook.ui.pages;

import com.phonebook.model.Contact;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Represents the "Add Contact" page of the PhoneBook web application.
 * Provides methods to fill in and submit the contact creation form.
 */
public class AddPage extends BasePage {

    private static final Logger logger = LoggerFactory.getLogger(AddPage.class);

    @FindBy(xpath = "//div[@class='add_form__2rsm2']/input[1]")
    private WebElement inputName;

    @FindBy(xpath = "//div[@class='add_form__2rsm2']/input[2]")
    private WebElement inputLastName;

    @FindBy(xpath = "//div[@class='add_form__2rsm2']/input[3]")
    private WebElement inputPhone;

    @FindBy(xpath = "//div[@class='add_form__2rsm2']/input[4]")
    private WebElement inputEmail;

    @FindBy(xpath = "//div[@class='add_form__2rsm2']/input[5]")
    private WebElement inputAddress;

    @FindBy(xpath = "//div[@class='add_form__2rsm2']/input[6]")
    private WebElement inputDescription;

    @FindBy(xpath = "//b[text()='Save']/..")
    private WebElement btnSave;

    public AddPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, 10), this);
        logger.info("AddPage initialized successfully.");
    }

    /**
     * Fills the contact form with data from the provided Contact object and clicks Save.
     */
    public void typeContactForm(Contact contact) {
        inputName.sendKeys(contact.getName());
        inputLastName.sendKeys(contact.getLastName());
        inputPhone.sendKeys(contact.getPhone());
        inputEmail.sendKeys(contact.getEmail());
        inputAddress.sendKeys(contact.getAddress());
        inputDescription.sendKeys(contact.getDescription());
        btnSave.click();
        logger.info("Contact form submitted for: {} {}", contact.getName(), contact.getLastName());
    }

    /**
     * Checks if the Save button is visible on the page.
     */
    public boolean isButtonSaveVisible() {
        return btnSave.isDisplayed();
    }
}