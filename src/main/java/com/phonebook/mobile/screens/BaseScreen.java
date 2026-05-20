package com.phonebook.mobile.screens;

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
 * Provides common initialization and utility methods for element interactions.
 */
public class BaseScreen {

    protected static AppiumDriver driver;
    private static final Logger logger = LoggerFactory.getLogger(BaseScreen.class);

    public BaseScreen(AppiumDriver driver) {
        BaseScreen.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver, Duration.ofSeconds(15)), this);
        logger.info("Initialized BaseScreen with Appium driver");
    }

    /**
     * Waits until the specified text appears in the given element.
     *
     * @param element the WebElement to check
     * @param text    the expected text
     * @param time    timeout in seconds
     * @return true if the text is present, false otherwise
     */
    public boolean isTextInElementPresent(WebElement element, String text, int time) {
        logger.debug("Waiting for text '{}' to appear in element {}", text, element);
        return new WebDriverWait(driver, Duration.ofSeconds(time))
                .until(ExpectedConditions.textToBePresentInElement(element, text));
    }

    /**
     * Waits until the element becomes visible on the screen.
     *
     * @param element the WebElement to check
     * @param time    timeout in seconds
     * @return true if the element is visible, false otherwise
     */
    public boolean isElementPresent(WebElement element, int time) {
        logger.debug("Waiting for element visibility: {}", element);
        return new WebDriverWait(driver, Duration.ofSeconds(time))
                .until(ExpectedConditions.visibilityOf(element))
                .isDisplayed();
    }
}