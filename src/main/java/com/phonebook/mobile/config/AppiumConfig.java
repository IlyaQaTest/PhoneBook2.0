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
 * Configures and initializes the Appium driver using parameters from properties
 * or system environment overrides for CI/CD compatibility.
 */
public class AppiumConfig {

    private static final Logger logger = LoggerFactory.getLogger(AppiumConfig.class);

    /**
     * Creates and returns an AppiumDriver instance.
     * Allows system properties to override configuration file values for GitHub Actions.
     *
     * @param fileName the name of the properties file located in resources/properties/
     * @return an initialized AppiumDriver instance
     */
    public static AppiumDriver createAppiumDriver(String fileName) {
        String appiumUrl = getValue(fileName, "appiumUrl");
        String platformName = getValue(fileName, "os");
        String automationName = getValue(fileName, "automationName");
        String deviceName = getValue(fileName, "deviceName");
        String appPackage = getValue(fileName, "appPackage");
        String appActivity = getValue(fileName, "appActivity");
        String appPath = getValueOptional(fileName, "appPath");

        logger.info("Connecting to Appium server at: {}", appiumUrl);

        UiAutomator2Options options = new UiAutomator2Options()
                .setPlatformName(platformName)
                .setAutomationName(automationName)
                .setDeviceName(deviceName)
                .setAppPackage(appPackage)
                .setAppActivity(appActivity)
                .setNewCommandTimeout(Duration.ofSeconds(120))
                .setAndroidInstallTimeout(Duration.ofSeconds(120))
                .setNoReset(false);

        // Optional: If APK path is specified for CI/CD installation
        if (appPath != null && !appPath.isEmpty()) {
            options.setApp(appPath);
        }

        try {
            return new AppiumDriver(new URL(appiumUrl), options);
        } catch (MalformedURLException e) {
            logger.error("Invalid Appium URL: {}", appiumUrl, e);
            throw new RuntimeException("Bad Appium URL: " + appiumUrl, e);
        }
    }

    /**
     * Checks System property first, then falls back to properties file.
     */
    private static String getValue(String fileName, String key) {
        String sysValue = System.getProperty(key);
        if (sysValue != null && !sysValue.isEmpty()) {
            return sysValue;
        }
        String fileValue = getProperty(fileName, key);
        if (fileValue == null) {
            throw new RuntimeException("Missing required property: " + key + " in " + fileName);
        }
        return fileValue;
    }

    /**
     * Optional property retriever without throwing exception.
     */
    private static String getValueOptional(String fileName, String key) {
        String sysValue = System.getProperty(key);
        if (sysValue != null && !sysValue.isEmpty()) {
            return sysValue;
        }
        return getProperty(fileName, key);
    }
}