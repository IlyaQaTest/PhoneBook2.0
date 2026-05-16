package com.phonebook.tests.utils.listeners;

import io.qameta.allure.Attachment;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestListener;
import org.testng.ITestResult;

/**
 * TestNG listener for capturing screenshots on test failures and attaching them to Allure reports.
 */
public class AllureTestListener implements ITestListener {

    /**
     * Saves a screenshot as an Allure attachment.
     *
     * @param driver the WebDriver instance used for capturing the screenshot.
     * @return the screenshot as a byte array.
     */
    @Attachment(value = "Screenshot", type = "image/png")
    public byte[] saveScreenshot(WebDriver driver) {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }

    /**
     * Captures a screenshot when a test fails and attaches it to the Allure report.
     *
     * @param result the ITestResult containing test context and metadata.
     */
    @Override
    public void onTestFailure(ITestResult result) {
        Object driver = result.getTestContext().getAttribute("driver");
        if (driver instanceof WebDriver) {
            saveScreenshot((WebDriver) driver);
        }
    }
}