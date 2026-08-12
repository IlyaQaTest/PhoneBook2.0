package com.phonebook.mobile.screens;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

import com.phonebook.mobile.config.MobileTimeouts;

/**
 * Base class for all mobile screens.
 * Provides common initialization and utility methods for element interactions.
 */
public abstract class BaseScreen {

    protected AppiumDriver driver;
    private static final Logger logger = LoggerFactory.getLogger(BaseScreen.class);

    public BaseScreen(AppiumDriver driver) {
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver, Duration.ofSeconds(MobileTimeouts.DEFAULT)), this);
        logger.info("Initialized {} with Appium driver", this.getClass().getSimpleName());
    }

    /**
     * Safely checks if the specified text appears in the given element within time limits.
     *
     * @param element the WebElement to check
     * @param text    the expected text
     * @param time    timeout in seconds
     * @return true if the text is present, false if timeout occurs
     */
    public boolean isTextInElementPresent(WebElement element, String text, int time) {
        logger.debug("Waiting for text '{}' to appear in element", text);
        try {
            return new WebDriverWait(driver, Duration.ofSeconds(time))
                    .until(ExpectedConditions.textToBePresentInElement(element, text));
        } catch (TimeoutException e) {
            logger.warn("Text '{}' was not present in element within {} seconds", text, time);
            return false;
        }
    }

    /**
     * Safely checks if the element becomes visible on the screen within time limits.
     *
     * @param element the WebElement to check
     * @param time    timeout in seconds
     * @return true if the element is visible, false if timeout occurs
     */
    public boolean isElementPresent(WebElement element, int time) {
        logger.debug("Waiting for element visibility within {} seconds", time);
        try {
            new WebDriverWait(driver, Duration.ofSeconds(time))
                    .until(ExpectedConditions.visibilityOf(element));
            return true;
        } catch (TimeoutException e) {
            logger.warn("Element was not visible within {} seconds", time);
            return false;
        }
    }

    /**
     * Clicks on the specified element after waiting for it to be clickable.
     *
     * @param element the WebElement to click
     */
    public void click(WebElement element) {
        logger.debug("Waiting for element to be clickable before clicking: {}", element);
        new WebDriverWait(driver, Duration.ofSeconds(MobileTimeouts.CLICK))
                .until(ExpectedConditions.elementToBeClickable(element));
        element.click();
    }

    /**
     * Types text into the specified input element after clearing existing text.
     *
     * @param element the input field
     * @param text    text to enter
     */
    public void type(WebElement element, String text) {
        if (text != null) {
            click(element);
            element.clear();
            element.sendKeys(text);
            logger.debug("Entered text: {}", text);
        }
    }

    /**
     * Pauses execution for the specified duration in milliseconds.
     *
     * @param millis time to sleep in milliseconds
     */
    public void pause(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.warn("Pause interrupted: {}", e.getMessage());
        }
    }
}
