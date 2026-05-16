package com.phonebook.utils.ui;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

/**
 * Implements a retry mechanism for failed TestNG tests.
 * Retries a test up to a defined maximum number of attempts.
 */
public class RetryAnalyser implements IRetryAnalyzer {

    private int retryCount = 0;
    private static final int MAX_RETRY_COUNT = 3;

    @Override
    public boolean retry(ITestResult result) {
        if (retryCount < MAX_RETRY_COUNT) {
            retryCount++;
            return true;
        }
        return false;
    }
}