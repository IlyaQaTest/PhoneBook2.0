package com.phonebook.core.helpers;

import io.appium.java_client.AppiumDriver;
import io.qameta.allure.Attachment;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Simple crash handler: takes screenshot and attaches it to Allure.
 */
public class CrashHandler {

    private static final Logger logger = LoggerFactory.getLogger(CrashHandler.class);

    // Main crash handler method
    public static void handleCrash(AppiumDriver driver, String screenshotName) {
        logger.error("App crash detected! Capturing screenshot...");

        if (driver == null) {
            logger.error("Driver is null — cannot capture screenshot.");
            return;
        }

        try {
            attachScreenshot(driver, screenshotName);
            logger.info("Crash screenshot attached to Allure: {}", screenshotName);
        } catch (Exception e) {
            logger.error("Failed to capture crash screenshot: {}", e.getMessage());
        }
    }

    // Attach screenshot to Allure
    @Attachment(value = "Crash Screenshot - {screenshotName}", type = "image/png")
    public static byte[] attachScreenshot(AppiumDriver driver, String screenshotName) {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }
}