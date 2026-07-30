package com.phonebook.ui.pages;

import com.phonebook.ui.utils.HeaderMenuItem;
import org.openqa.selenium.*;
import org.openqa.selenium.support.*;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;

/**
 * Base page object.
 * Provides common actions and wait utilities for all pages.
 */
public abstract class BasePage {

    protected final WebDriver driver;
    protected final WebDriverWait wait;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    // Wait until element is visible
    public void waitForElementVisible(WebElement element) {
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    // Check if text is present in element
    public boolean isTextInElementPresent(WebElement element, String text) {
        wait.until(ExpectedConditions.visibilityOf(element));
        return element.getText().contains(text);
    }

    // Click header menu item
    public void clickHeaderItem(HeaderMenuItem item) {
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(item.getLocator()))).click();
    }

    // Get alert text and close it
    public String getAlertTextAndClose() {
        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        String text = alert.getText();
        alert.accept();
        return text;
    }
}