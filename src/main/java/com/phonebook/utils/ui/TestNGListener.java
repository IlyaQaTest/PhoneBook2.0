package com.phonebook.utils.ui;

import com.phonebook.api.manager.AppManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

/**
 * Custom TestNG listener for logging test lifecycle events and capturing screenshots on failures.
 * Integrates with AppManager to take screenshots automatically when a test fails.
 */
public class TestNGListener implements ITestListener {

    private static final Logger logger = LoggerFactory.getLogger(TestNGListener.class);

    @Override
    public void onTestStart(ITestResult result) {
        logger.info("Starting test: {} in class {}", result.getName(), result.getTestClass().getName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        logger.info("Test PASSED: {}", result.getName());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        logger.error("Test FAILED: {}", result.getName());
        logger.error("Error message: {}", result.getThrowable().getMessage());

        Object testInstance = result.getInstance();
        if (testInstance instanceof AppManager) {
            ((AppManager) testInstance).takeScreenshot(result.getName());
            logger.info("Screenshot captured for failed test: {}", result.getName());
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        logger.warn("Test SKIPPED: {}", result.getName());
    }

    @Override
    public void onStart(ITestContext context) {
        logger.info("=== STARTING TEST SUITE: {} ===", context.getName());
    }

    @Override
    public void onFinish(ITestContext context) {
        logger.info("=== FINISHED TEST SUITE: {} ===", context.getName());
    }
}