package com.phonebook.mobile;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

/**
 * Base class for all mobile screens.
 * Provides driver initialization and common wait utilities.
 */
public abstract class BaseScreen {

    protected final AppiumDriver driver;
    private static final Logger logger = LoggerFactory.getLogger(BaseScreen.class);

    public BaseScreen(AppiumDriver driver) {
        this.driver = driver;

        PageFactory.initElements(
                new AppiumFieldDecorator(driver, Duration.ofSeconds(15)),
                this
        );

        logger.debug("Initialized screen: {}", this.getClass().getSimpleName());
    }

    /**
     * Waits until the element contains the specified text.
     *
     * @param element WebElement to check
     * @param text Expected text
     * @param timeoutSeconds Timeout in seconds
     * @return true if text is present, false otherwise
     */
    public boolean isTextInElementPresent(WebElement element, String text, int timeoutSeconds) {
        try {
            return new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds))
                    .until(ExpectedConditions.textToBePresentInElement(element, text));
        } catch (Exception e) {
            logger.debug("Text '{}' not found in element within {} seconds", text, timeoutSeconds);
            return false;
        }
    }

    /**
     * Checks if the element becomes visible within the given timeout.
     *
     * @param element WebElement to check
     * @param timeoutSeconds Timeout in seconds
     * @return true if element is visible, false otherwise
     */
    public boolean isElementPresent(WebElement element, int timeoutSeconds) {
        try {
            return new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds))
                    .until(ExpectedConditions.visibilityOf(element))
                    .isDisplayed();
        } catch (Exception e) {
            logger.debug("Element not visible within {} seconds", timeoutSeconds);
            return false;
        }
    }
}