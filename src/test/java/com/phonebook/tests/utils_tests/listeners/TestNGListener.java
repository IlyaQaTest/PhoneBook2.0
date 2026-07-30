package com.phonebook.tests.utils_tests.listeners;

import com.phonebook.ui.manager.AppManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

/**
 * TestNG listener.
 * Logs test lifecycle events and captures screenshots on failure.
 */
public class TestNGListener implements ITestListener {

    private static final Logger logger = LoggerFactory.getLogger(TestNGListener.class);

    @Override
    public void onTestStart(ITestResult result) {
        logger.info("Start test --> {} in class {}", result.getName(), result.getTestClass().getName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        logger.info("Success test --> {}", result.getName());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        logger.error("Test FAILED --> {}", result.getName());
        logger.error("Error message: {}", result.getThrowable().getMessage());

        Object testInstance = result.getInstance();
        if (testInstance instanceof AppManager) {
            ((AppManager) testInstance).ScreenshotUtils(result.getName());
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        logger.warn("Test SKIPPED --> {}", result.getName());
    }

    @Override
    public void onStart(ITestContext context) {
        logger.info("--- STARTING TEST SUITE: {} ---", context.getName());
    }

    @Override
    public void onFinish(ITestContext context) {
        logger.info("--- FINISHED TEST SUITE: {} ---", context.getName());
    }
}