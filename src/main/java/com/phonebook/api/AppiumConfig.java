package com.phonebook.api;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.Properties;

public class AppiumConfig {

    private static final Logger logger = LoggerFactory.getLogger(AppiumConfig.class);

    /**
     * Creates and configures an AppiumDriver instance using properties from a file.
     */
    public static AppiumDriver createAppiumDriver(Properties config) {

        String appiumUrl = getRequired(config, "appiumUrl");

        logger.info("Connecting to Appium server at: {}", appiumUrl);

        UiAutomator2Options options = new UiAutomator2Options()
                .setPlatformName(getRequired(config, "os"))
                .setAutomationName(getRequired(config, "automationName"))
                .setDeviceName(getRequired(config, "deviceName"))
                .setAppPackage(getRequired(config, "appPackage"))
                .setAppActivity(getRequired(config, "appActivity"))
                .setNewCommandTimeout(Duration.ofSeconds(120))
                .setAndroidInstallTimeout(Duration.ofSeconds(120))
                .setNoReset(false);

        try {
            return new AppiumDriver(new URL(appiumUrl), options);
        } catch (MalformedURLException e) {
            logger.error("Invalid Appium URL: {}", appiumUrl, e);
            throw new RuntimeException("Invalid Appium URL: " + appiumUrl, e);
        }
    }

    /**
     * Reads a required property from the config file.
     */
    private static String getRequired(Properties config, String key) {
        String value = config.getProperty(key);
        if (value == null || value.isBlank()) {
            throw new RuntimeException("Missing required property: " + key);
        }
        return value;
    }
}