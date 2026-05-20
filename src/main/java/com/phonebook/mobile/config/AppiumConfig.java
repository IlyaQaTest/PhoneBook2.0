package com.phonebook.mobile.config;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

import static com.phonebook.core.config.PropertiesReader.getProperty;

/**
 * Configures and initializes the Appium driver using parameters from a properties file.
 * Provides a reusable method to create a driver instance for mobile automation.
 */
public class AppiumConfig {

    private static final Logger logger = LoggerFactory.getLogger(AppiumConfig.class);

    /**
     * Creates and returns an AppiumDriver instance based on the provided configuration file.
     *
     * @param fileName the name of the properties file located in resources/properties/
     * @return an initialized AppiumDriver instance
     */
    public static AppiumDriver createAppiumDriver(String fileName) {
        String appiumUrl = getProperty(fileName, "appiumUrl");
        if (appiumUrl == null) {
            throw new RuntimeException("ERROR: appiumUrl is missing in " + fileName);
        }

        logger.info("Connecting to Appium server at: {}", appiumUrl);

        UiAutomator2Options options = new UiAutomator2Options()
                .setPlatformName(getRequired(fileName, "os"))
                .setAutomationName(getRequired(fileName, "automationName"))
                .setDeviceName(getRequired(fileName, "deviceName"))
                .setAppPackage(getRequired(fileName, "appPackage"))
                .setAppActivity(getRequired(fileName, "appActivity"))
                .setNewCommandTimeout(Duration.ofSeconds(120))
                .setAndroidInstallTimeout(Duration.ofSeconds(120))
                .setNoReset(false);

        try {
            return new AppiumDriver(new URL(appiumUrl), options);
        } catch (MalformedURLException e) {
            logger.error("Invalid Appium URL: {}", appiumUrl, e);
            throw new RuntimeException("Bad Appium URL: " + appiumUrl, e);
        }
    }

    /**
     * Retrieves a required property value from the configuration file.
     *
     * @param fileName the name of the properties file
     * @param key      the property key
     * @return the property value
     */
    private static String getRequired(String fileName, String key) {
        String value = getProperty(fileName, key);
        if (value == null) {
            throw new RuntimeException("Missing required property: " + key + " in " + fileName);
        }
        return value;
    }
}