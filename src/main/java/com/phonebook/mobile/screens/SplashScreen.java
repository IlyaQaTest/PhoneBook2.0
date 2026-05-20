package com.phonebook.mobile.screens;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Represents the splash screen of the mobile application.
 * Provides a method to validate the displayed app version.
 */
public class SplashScreen extends BaseScreen {

    private static final Logger logger = LoggerFactory.getLogger(SplashScreen.class);

    public SplashScreen(AppiumDriver driver) {
        super(driver);
    }

    @AndroidFindBy(xpath = "//*[contains(@text, '1.0.0')]")
    private WebElement versionApp;

    /**
     * Validates that the specified version text is displayed on the splash screen.
     *
     * @param text  the expected version text
     * @param time  timeout in seconds
     * @return true if the version text is present, false otherwise
     */
    public boolean validateVersionApp(String text, int time) {
        logger.info("Validating app version text: '{}'", text);
        return isTextInElementPresent(versionApp, text, time);
    }
}