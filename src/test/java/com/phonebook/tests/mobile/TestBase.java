package com.phonebook.tests.mobile;

import com.phonebook.mobile.config.AppiumConfig;
import com.phonebook.mobile.screens.ContactListScreen;
import com.phonebook.mobile.screens.LoginRegistrationScreen;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.Alert;
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

    protected static AppiumDriver driver;
    protected WebDriverWait wait;
    protected LoginRegistrationScreen loginRegistrationScreen;
    protected ContactListScreen contactListScreen;

    @BeforeMethod
    public void setup() {
        logger.info("Initializing Appium driver...");
        driver = AppiumConfig.createAppiumDriver("pixel.properties");

        logger.info("Driver created. Waiting 2 seconds for SplashActivity to stabilize...");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.warn("Setup wait interrupted: {}", e.getMessage());
        }

        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        loginRegistrationScreen = new LoginRegistrationScreen(driver);
        contactListScreen = new ContactListScreen(driver);
    }

    @AfterMethod(enabled = false)
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
}