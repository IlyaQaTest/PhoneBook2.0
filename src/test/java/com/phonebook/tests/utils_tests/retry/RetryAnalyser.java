package com.phonebook.tests.utils_tests.retry;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

/**
 * Retry analyzer for TestNG.
 * Retries failed tests up to a maximum number of attempts.
 */
public class RetryAnalyser implements IRetryAnalyzer {

    private int retryCount = 0;
    private static final int MAX_TRY_VALUE = 3;

    @Override
    public boolean retry(ITestResult result) {
        if (retryCount < MAX_TRY_VALUE) {
            retryCount++;
            return true;
        }
        return false;
    }
}