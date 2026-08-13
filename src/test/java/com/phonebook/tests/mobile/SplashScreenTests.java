package com.phonebook.tests.mobile;

import com.phonebook.mobile.screens.SplashScreen;
import io.qameta.allure.Step;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Mobile test verifying splash screen behavior and app version display.
 */
@org.testng.annotations.Test(enabled = false)
public class SplashScreenTests extends TestBase {

    private static final Logger logger = LoggerFactory.getLogger(SplashScreenTests.class);

    @Test(enabled = false, description = "Positive test: Verify splash screen displays correct app version")
    @Step("Validate that splash screen shows expected version text")
    public void splashScreenPositiveTest() {
        logger.info("Starting test: splashScreenPositiveTest");

        SplashScreen splashScreen = new SplashScreen(driver);

        // CI emulator needs more time to render splash screen
        boolean isVersionDisplayed = splashScreen.validateVersionApp("1.0.0", 15);

        logger.info("Version text displayed correctly: {}", isVersionDisplayed);
        Assert.assertTrue(isVersionDisplayed, "Splash screen should display correct app version");
    }
}