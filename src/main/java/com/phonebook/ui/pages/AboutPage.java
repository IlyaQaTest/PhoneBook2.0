package com.phonebook.ui.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

/**
 * About page object.
 * Initializes elements using AjaxElementLocatorFactory.
 */
public class AboutPage extends BasePage {

    public AboutPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, 10), this);
    }
}