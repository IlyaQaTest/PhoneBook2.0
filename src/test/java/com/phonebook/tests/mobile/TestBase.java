package com.phonebook.tests.mobile;

import com.phonebook.mobile.config.AppiumConfig;
import com.phonebook.mobile.screens.ContactListScreen;
import com.phonebook.mobile.screens.LoginRegistrationScreen;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.time.Duration;

public class TestBase {

    protected static final Logger logger = LoggerFactory.getLogger(TestBase.class);

    protected AndroidDriver driver;
    protected WebDriverWait wait;
    protected LoginRegistrationScreen loginRegistrationScreen;
    protected ContactListScreen contactListScreen;

    @BeforeMethod
    public void setup() {
        logger.info("Initializing Appium driver...");

        String configFile = System.getProperty("configFile", "pixel.properties");
        driver = AppiumConfig.createAppiumDriver(configFile);

        // Increased timeout for slow CI emulator
        wait = new WebDriverWait(driver, Duration.ofSeconds(25));

        loginRegistrationScreen = new LoginRegistrationScreen(driver);
        contactListScreen = new ContactListScreen(driver);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        if (driver != null) {
            logger.info("Closing Appium session and quitting driver...");
            try {
                driver.quit();
            } catch (Exception e) {
                logger.warn("Error during driver.quit(): {}", e.getMessage());
            } finally {
                driver = null;
            }
        }
    }

    public String getAlertTextAndClose() {
        logger.debug("Waiting for alert to appear...");
        Alert alert = new WebDriverWait(driver, Duration.ofSeconds(20))
                .until(ExpectedConditions.alertIsPresent());
        String text = alert.getText();
        alert.accept();
        logger.info("Alert closed. Text: {}", text);
        return text;
    }

    public boolean isToastPresent(String toastText, int timeout) {
        try {
            logger.info("Waiting for Toast message containing: '{}'", toastText);
            WebDriverWait customWait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
            WebElement toastElement = customWait.until(ExpectedConditions.presenceOfElementLocated(
                    By.xpath("//*[contains(@text,'" + toastText + "')]")
            ));
            return toastElement != null;
        } catch (Exception e) {
            logger.warn("Toast message with text '{}' was not found within {} seconds", toastText, timeout);
            return false;
        }
    }
}
