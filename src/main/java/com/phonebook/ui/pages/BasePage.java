package com.phonebook.ui.pages;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.phonebook.utils.ui.HeaderMenuItem;

import java.time.Duration;

/**
 * Base class for all page objects in the PhoneBook web application.
 * Provides common functionality such as waits, text checks, header navigation, and alert handling.
 */
public abstract class BasePage {

    protected final WebDriver driver;
    protected final WebDriverWait wait;

    protected BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    public void waitForElementVisible(WebElement element) {
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    public boolean isTextInElementPresent(WebElement element, String text) {
        wait.until(ExpectedConditions.visibilityOf(element));
        return element.getText().contains(text);
    }

    public void clickHeaderItem(HeaderMenuItem item) {
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(item.getLocator()))).click();
    }

    public String getAlertTextAndClose() {
        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        String text = alert.getText();
        alert.accept();
        return text;
    }
}