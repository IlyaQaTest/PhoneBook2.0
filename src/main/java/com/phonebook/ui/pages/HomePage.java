package com.phonebook.ui.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import com.phonebook.utils.ui.PropertiesReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Represents the Home page of the PhoneBook web application.
 * Initializes the base URL from properties and provides navigation to the Login page.
 */
public class HomePage extends BasePage {

    private static final Logger logger = LoggerFactory.getLogger(HomePage.class);

    @FindBy(xpath = "//a[text()='LOGIN']")
    private WebElement btnLogin;

    public HomePage(WebDriver driver) {
        super(driver);
        String url = PropertiesReader.getProperty("base.properties", "baseUrl");

        if (url == null || url.isEmpty()) {
            logger.error("Base URL is not found in properties file!");
            throw new RuntimeException("Base URL is not found in properties file!");
        }

        driver.get(url);
        logger.info("Navigated to Home page: {}", url);
    }

    /**
     * Clicks the Login button on the Home page.
     */
    public void clickLoginButton() {
        waitForElementVisible(btnLogin);
        btnLogin.click();
        logger.info("Clicked LOGIN button.");
    }
}