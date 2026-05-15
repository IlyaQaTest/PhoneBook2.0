package com.phonebook.mobile;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Screen object representing system-level error dialogs or crash screens.
 * Provides validation utilities for detecting error messages.
 */
public class ErrorScreen extends BaseScreen {

    private static final Logger logger = LoggerFactory.getLogger(ErrorScreen.class);

    public ErrorScreen(AppiumDriver driver) {
        super(driver);
    }

    @AndroidFindBy(id = "android:id/message")
    private WebElement textError;

    @AndroidFindBy(id = "android:id/aerr_restart")
    private WebElement crashScreenBtn;

    @AndroidFindBy(id = "android:id/title_template")
    private WebElement appStop;

    /**
     * Validates that the error dialog contains the expected text.
     *
     * @param text Expected error message
     * @param timeoutSeconds Timeout in seconds
     * @return true if text is present, false otherwise
     */
    public boolean validateTextInError(String text, int timeoutSeconds) {
        logger.info("Validating error text: {}", text);
        return isTextInElementPresent(textError, text, timeoutSeconds);
    }

    /**
     * Validates that the crash screen contains the expected text.
     *
     * @param text Expected crash message
     * @param timeoutSeconds Timeout in seconds
     * @return true if text is present, false otherwise
     */
    public boolean validateTextInCrashScreen(String text, int timeoutSeconds) {
        logger.info("Validating crash screen text: {}", text);
        return isTextInElementPresent(crashScreenBtn, text, timeoutSeconds);
    }

    /**
     * Checks whether the "App has stopped" dialog is displayed.
     *
     * @return true if dialog is visible, false otherwise
     */
    public boolean isAppStopDisplay() {
        logger.info("Checking if 'App has stopped' dialog is displayed");
        return isElementPresent(appStop, 5);
    }
}