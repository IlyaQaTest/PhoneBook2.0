package com.phonebook.core.helpers;

import io.qameta.allure.Allure;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;

/**
 * Utility class for handling app/browser crashes and attaching screenshots to Allure reports.
 */
public class CrashHandler {

    private static final Logger logger = LoggerFactory.getLogger(CrashHandler.class);

    private CrashHandler() {
        // Prevent instantiation of utility class
    }

    /**
     * Main crash handler method.
     * Works with any WebDriver instance (Web Selenium & Mobile Appium).
     */
    public static void handleCrash(WebDriver driver, String screenshotName) {
        logger.error("Crash detected! Capturing screenshot for: {}", screenshotName);

        if (driver == null) {
            logger.error("Driver instance is null — cannot capture screenshot.");
            return;
        }

        try {
            attachScreenshot(driver, screenshotName);
            logger.info("Crash screenshot successfully attached to Allure: {}", screenshotName);
        } catch (Exception e) {
            logger.error("Failed to capture crash screenshot for '{}': {}", screenshotName, e.getMessage(), e);
        }
    }

    /**
     * Captures and attaches screenshot to Allure report.
     */
    public static void attachScreenshot(WebDriver driver, String screenshotName) {
        if (driver instanceof TakesScreenshot takesScreenshot) {
            byte[] screenshot = takesScreenshot.getScreenshotAs(OutputType.BYTES);
            Allure.addAttachment(
                    "Crash Screenshot - " + screenshotName,
                    "image/png",
                    new ByteArrayInputStream(screenshot),
                    "png"
            );
        } else {
            logger.warn("Driver instance does not support taking screenshots.");
        }
    }
}