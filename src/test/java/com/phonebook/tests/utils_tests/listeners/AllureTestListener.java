package com.phonebook.tests.utils_tests.listeners;

import io.qameta.allure.Attachment;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * TestNG listener for capturing screenshots on test failure and attaching them to Allure reports.
 */
public class AllureTestListener implements ITestListener {

    private static final Logger logger = LoggerFactory.getLogger(AllureTestListener.class);

    @Attachment(value = "Screenshot", type = "image/png")
    public byte[] saveScreenshot(WebDriver driver) {
        logger.info("Capturing screenshot for failed test...");
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }

    @Override
    public void onTestFailure(ITestResult result) {
        Object driver = result.getTestContext().getAttribute("driver");
        if (driver instanceof WebDriver) {
            logger.warn("Test failed: {}. Capturing screenshot...", result.getName());
            saveScreenshot((WebDriver) driver);
        } else {
            logger.error("WebDriver instance not found in test context for {}", result.getName());
        }
    }
}