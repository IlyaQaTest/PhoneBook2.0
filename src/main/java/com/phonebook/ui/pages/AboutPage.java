package com.phonebook.ui.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Represents the "About" page of the PhoneBook web application.
 * Initializes elements using AjaxElementLocatorFactory for dynamic loading.
 */
public class AboutPage extends BasePage {

    private static final Logger logger = LoggerFactory.getLogger(AboutPage.class);

    public AboutPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, 10), this);
        logger.info("AboutPage initialized successfully.");
    }
}