package com.phonebook.ui.utils;

import org.openqa.selenium.*;
import org.openqa.selenium.support.events.WebDriverListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * WebDriver event listener.
 * Logs browser actions and handles common exceptions.
 */
public class WDListener implements WebDriverListener {

    private final Logger logger = LoggerFactory.getLogger(WDListener.class);

    @Override
    public void beforeGet(WebDriver driver, String url) {
        logger.info("Before Get: {}", url);
    }

    @Override
    public void afterGet(WebDriver driver, String url) {
        logger.info("Open page: {}", url);
    }

    @Override
    public void beforeClick(WebElement element) {
        try {
            logger.info("Before click: {}", element.getTagName());
        } catch (Exception e) {
            logger.info("Before click: element is not accessible");
        }
    }

    @Override
    public void afterClick(WebElement element) {
        try {
            logger.info("After click to element: {}", element);
        } catch (Exception e) {
            logger.info("Element is no longer available after click");
        }
    }

    @Override
    public void afterFindElement(WebDriver driver, By locator, WebElement result) {
        logger.info("Found element with locator: {}", locator);
    }

    @Override
    public void afterSendKeys(WebElement element, CharSequence... keysToSend) {
        try {
            logger.info("SendKeys to element <{}> value: {}", element.getTagName(), keysToSend);
        } catch (StaleElementReferenceException e) {
            logger.info("SendKeys: element became stale");
        }
    }

    @Override
    public void afterAnyAlertCall(Alert alert, Method method, Object[] args, Object result) {
        try {
            logger.info("Alert text: {}", alert.getText());
        } catch (NoAlertPresentException e) {
            logger.info("Alert closed or not present");
        }
    }

    @Override
    public void onError(Object target, Method method, Object[] args, InvocationTargetException e) {
        Throwable cause = e.getTargetException();
        if (cause instanceof StaleElementReferenceException || cause instanceof NoAlertPresentException) return;
        logger.error("Exception in method {}: {}", method.getName(), cause.getMessage());
    }

    @Override
    public void afterMaximize(WebDriver.Window window) {
        logger.info("After maximize: {}", window.getSize());
    }

    @Override
    public void afterQuit(WebDriver driver) {
        logger.info("Browser quit");
    }
}