package com.phonebook.utils.api;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.Collections;

/**
 * Utility interface providing swipe gestures for Appium-based mobile tests.
 * Supports full-screen swipes and element-specific swipes.
 */
public interface SwipeUtils {

    Logger logger = LoggerFactory.getLogger(SwipeUtils.class);

    /**
     * Performs a swipe gesture across the entire screen in the specified direction.
     *
     * @param driver    Appium driver instance
     * @param direction swipe direction (UP, DOWN, LEFT, RIGHT)
     */
    default void swipeScreen(AppiumDriver driver, Direction direction) {
        Dimension size = driver.manage().window().getSize();
        int middleX = size.width / 2;
        int middleY = size.height / 2;

        int startX, endX, startY, endY;

        switch (direction) {
            case RIGHT -> {
                startX = (int) (size.width * 0.2);
                endX = (int) (size.width * 0.8);
                startY = endY = middleY;
            }
            case LEFT -> {
                startX = (int) (size.width * 0.8);
                endX = (int) (size.width * 0.2);
                startY = endY = middleY;
            }
            case UP -> {
                startY = (int) (size.height * 0.9);
                endY = (int) (size.height * 0.1);
                startX = endX = middleX;
            }
            case DOWN -> {
                startY = (int) (size.height * 0.1);
                endY = (int) (size.height * 0.9);
                startX = endX = middleX;
            }
            default -> throw new IllegalArgumentException("Invalid direction: " + direction);
        }

        logger.debug("Swiping screen {} from ({},{}) to ({},{})",
                direction, startX, startY, endX, endY);

        performSwipe(driver, startX, startY, endX, endY);
    }

    /**
     * Performs a horizontal swipe inside a specific element.
     *
     * @param driver  Appium driver instance
     * @param element target element
     * @param direction LEFT or RIGHT
     */
    default void swipeInsideElement(AppiumDriver driver, WebElement element, Direction direction) {
        Rectangle rect = element.getRect();
        int middleY = rect.y + rect.height / 2;

        int startX, endX;

        switch (direction) {
            case RIGHT -> {
                startX = rect.x + (int) (rect.width * 0.2);
                endX = rect.x + (int) (rect.width * 0.8);
            }
            case LEFT -> {
                startX = rect.x + (int) (rect.width * 0.8);
                endX = rect.x + (int) (rect.width * 0.2);
            }
            default -> throw new IllegalArgumentException("Only LEFT/RIGHT allowed for element swipe");
        }

        logger.debug("Swiping inside element {} from ({},{}) to ({},{})",
                direction, startX, middleY, endX, middleY);

        performSwipe(driver, startX, middleY, endX, middleY);
    }

    /**
     * Swipe from right edge toward center (used for Update button reveal).
     */
    default void swipeInsideElementUpdate(AppiumDriver driver, WebElement element) {
        Rectangle rect = element.getRect();

        int startX = rect.x + rect.width - 20;
        int endX = rect.x + rect.width / 2;
        int middleY = rect.y + rect.height / 2;

        logger.debug("Swiping inside element for UPDATE from ({},{}) to ({},{})",
                startX, middleY, endX, middleY);

        performSwipe(driver, startX, middleY, endX, middleY);
    }

    /**
     * Swipe from left edge toward center (used for Delete button reveal).
     */
    default void swipeInsideElementDelete(AppiumDriver driver, WebElement element) {
        Rectangle rect = element.getRect();

        int startX = rect.x + 20;
        int endX = rect.x + rect.width / 2;
        int middleY = rect.y + rect.height / 2;

        logger.debug("Swiping inside element for DELETE from ({},{}) to ({},{})",
                startX, middleY, endX, middleY);

        performSwipe(driver, startX, middleY, endX, middleY);
    }

    /**
     * Core swipe executor used by all swipe methods.
     */
    private void performSwipe(AppiumDriver driver, int startX, int startY, int endX, int endY) {
        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");

        Sequence swipe = new Sequence(finger, 1)
                .addAction(finger.createPointerMove(Duration.ZERO,
                        PointerInput.Origin.viewport(), startX, startY))
                .addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()))
                .addAction(finger.createPointerMove(Duration.ofMillis(500),
                        PointerInput.Origin.viewport(), endX, endY))
                .addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

        driver.perform(Collections.singletonList(swipe));
    }
}