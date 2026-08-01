package com.phonebook.tests.mobile;

import com.phonebook.mobile.config.AppiumConfig;
import com.phonebook.mobile.screens.ContactListScreen;
import com.phonebook.mobile.screens.LoginRegistrationScreen;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.time.Duration;

/**
 * Base class for all mobile tests.
 * Handles Appium driver setup, teardown, and common utilities such as screenshots and alert handling.
 */
public class TestBase {

    protected static final Logger logger = LoggerFactory.getLogger(TestBase.class);

    protected AndroidDriver driver;
    protected WebDriverWait wait;
    protected LoginRegistrationScreen loginRegistrationScreen;
    protected ContactListScreen contactListScreen;

    @BeforeMethod
    public void setup() {
        logger.info("Initializing Appium driver...");

        // Allows overriding configFile via system property -DconfigFile=pixel.properties
        String configFile = System.getProperty("configFile", "pixel.properties");
        driver = AppiumConfig.createAppiumDriver(configFile);

        wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        loginRegistrationScreen = new LoginRegistrationScreen(driver);
        contactListScreen = new ContactListScreen(driver);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        if (driver != null) {
            logger.info("Closing Appium session and quitting driver...");
            driver.quit();
        }
    }

    /**
     * Waits for an alert to appear, retrieves its text, and closes it.
     *
     * @return the alert message text
     */
    public String getAlertTextAndClose() {
        logger.debug("Waiting for alert to appear...");
        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        String text = alert.getText();
        alert.accept();
        logger.info("Alert closed. Text: {}", text);
        return text;
    }

    /**
     * Checks if a Toast message with the expected text is displayed on the screen.
     *
     * @param toastText expected text in the Toast notification
     * @param timeout   wait timeout in seconds
     * @return true if the Toast is present, false otherwise
     */
    public boolean isToastPresent(String toastText, int timeout) {
        try {
            logger.info("Waiting for Toast message containing: '{}'", toastText);
            WebDriverWait customWait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
            return customWait.until(ExpectedConditions.presenceOfElementLocated(
                    By.xpath("//android.widget.Toast[contains(@text,'" + toastText + "')]")
            )).isDisplayed();
        } catch (Exception e) {
            logger.warn("Toast message with text '{}' was not found within {} seconds", toastText, timeout);
            return false;
        }
    }
}