package com.phonebook.ui.pages;

import com.phonebook.core.config.PropertiesReader;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;

/**
 * Home page object.
 * Opens base URL and handles navigation to login page.
 */
public class HomePage extends BasePage {

    public HomePage(WebDriver driver) {
        super(driver);
        String url = PropertiesReader.getProperty("base.properties", "baseUrl");
        if (url == null || url.isEmpty()) {
            throw new RuntimeException("Base URL is not found in properties file!");
        }
        driver.get(url);
    }

    @FindBy(xpath = "//a[text()='LOGIN']")
    WebElement btnLogin;

    // Click Login button
    public void clickBtnLogin() {
        waitForElementVisible(btnLogin);
        btnLogin.click();
    }
}