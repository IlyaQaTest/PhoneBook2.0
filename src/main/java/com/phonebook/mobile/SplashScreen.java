package com.phonebook.mobile;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Screen object representing the Splash screen.
 * Provides validation utilities for checking the displayed app version.
 */
public class SplashScreen extends BaseScreen {

    private static final Logger logger = LoggerFactory.getLogger(SplashScreen.class);

    public SplashScreen(AppiumDriver driver) {
        super(driver);
    }

    @AndroidFindBy(xpath = "//*[contains(@text, '1.0.0')]")
    private WebElement versionApp;

    /**
     * Validates that the displayed version text matches the expected value.
     *
     * @param expectedText Expected version string
     * @param timeoutSeconds Timeout in seconds
     * @return true if version text is present, false otherwise
     */
    public boolean validateVersionApp(String expectedText, int timeoutSeconds) {
        logger.info("Validating app version: {}", expectedText);
        return isTextInElementPresent(versionApp, expectedText, timeoutSeconds);
    }
}