package com.phonebook.utils.ui;

import org.openqa.selenium.*;
import org.openqa.selenium.support.events.WebDriverListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Custom WebDriver listener for logging browser interactions.
 * Captures navigation, clicks, element searches, alerts, and errors.
 */
public class WDListener implements WebDriverListener {

    private static final Logger logger = LoggerFactory.getLogger(WDListener.class);

    @Override
    public void beforeGet(WebDriver driver, String url) {
        logger.info("Before navigating to: {}", url);
    }

    @Override
    public void afterGet(WebDriver driver, String url) {
        logger.info("Opened page: {}", url);
    }

    @Override
    public void beforeClick(WebElement element) {
        try {
            logger.info("Before click on element: <{}>", element.getTagName());
        } catch (Exception e) {
            logger.info("Before click: element not accessible");
        }
    }

    @Override
    public void afterClick(WebElement element) {
        try {
            logger.info("After click on element: {}", element);
        } catch (Exception e) {
            logger.info("Element became stale after click");
        }
    }

    @Override
    public void afterFindElement(WebDriver driver, By locator, WebElement result) {
        logger.info("Element found using locator: {}", locator);
    }

    @Override
    public void afterSendKeys(WebElement element, CharSequence... keysToSend) {
        try {
            logger.info("SendKeys to <{}>: {}", element.getTagName(), String.valueOf(keysToSend));
        } catch (StaleElementReferenceException e) {
            logger.info("SendKeys executed, but element became stale");
        }
    }

    @Override
    public void afterAnyAlertCall(Alert alert, Method method, Object[] args, Object result) {
        try {
            logger.info("Alert interaction: {}", alert.getText());
        } catch (NoAlertPresentException e) {
            logger.info("Alert closed or no longer present");
        }
    }

    @Override
    public void onError(Object target, Method method, Object[] args, InvocationTargetException e) {
        Throwable cause = e.getTargetException();

        if (cause instanceof StaleElementReferenceException) return;
        if (cause instanceof NoAlertPresentException) return;

        logger.error("Exception in method {}: {}", method.getName(), cause.getMessage());
    }

    @Override
    public void afterMaximize(WebDriver.Window window) {
        logger.info("Window maximized. Size: {}", window.getSize());
    }
}