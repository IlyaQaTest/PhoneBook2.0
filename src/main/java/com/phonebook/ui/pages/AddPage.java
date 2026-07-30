package com.phonebook.ui.pages;

import com.phonebook.model.Contact;
import org.openqa.selenium.*;
import org.openqa.selenium.support.*;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

/**
 * Add contact page object.
 * Handles contact form input and save action.
 */
public class AddPage extends BasePage {

    public AddPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, 10), this);
    }

    @FindBy(xpath = "//div[@class='add_form__2rsm2']/input[1]")
    WebElement inputName;

    @FindBy(xpath = "//div[@class='add_form__2rsm2']/input[2]")
    WebElement inputLastName;

    @FindBy(xpath = "//div[@class='add_form__2rsm2']/input[3]")
    WebElement inputPhone;

    @FindBy(xpath = "//div[@class='add_form__2rsm2']/input[4]")
    WebElement inputEmail;

    @FindBy(xpath = "//div[@class='add_form__2rsm2']/input[5]")
    WebElement inputAddress;

    @FindBy(xpath = "//div[@class='add_form__2rsm2']/input[6]")
    WebElement inputDescription;

    @FindBy(xpath = "//b[text()='Save']/..")
    WebElement btnSave;

    // Fill contact form and click Save
    public void typeContactForm(Contact contact) {
        inputName.sendKeys(contact.getName());
        inputLastName.sendKeys(contact.getLastName());
        inputPhone.sendKeys(contact.getPhone());
        inputEmail.sendKeys(contact.getEmail());
        inputAddress.sendKeys(contact.getAddress());
        inputDescription.sendKeys(contact.getDescription());
        btnSave.click();
    }

    // Check if Save button is visible
    public boolean isButtonSaveDisabled() {
        return btnSave.isDisplayed();
    }
}