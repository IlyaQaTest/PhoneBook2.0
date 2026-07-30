package com.phonebook.ui.manager;

import com.phonebook.ui.utils.WDListener;
import lombok.Getter;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.*;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.events.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.*;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

import static com.phonebook.core.config.PropertiesReader.getProperty;

/**
 * WebDriver manager for UI tests.
 * Handles browser setup, teardown, and screenshot capture.
 */
public class AppManager {

    @Getter
    private WebDriver driver;
    private final Logger logger = LoggerFactory.getLogger(AppManager.class);
    private static final String browser = System.getProperty("browser", "chrome");

    // Capture screenshot for failed tests
    public void ScreenshotUtils(String methodName) {
        if (getDriver() == null) return;

        File screenshot = new File("build/reports/tests/smoke_tests/screenshots/screenshot-" + methodName + ".png");
        File parentDir = screenshot.getParentFile();
        if (parentDir != null && !parentDir.exists() && !parentDir.mkdirs()) {
            logger.warn("Could not create directory: {}", parentDir.getAbsolutePath());
        }

        File tmp = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);
        try {
            java.nio.file.Files.copy(tmp.toPath(), screenshot.toPath(), java.nio.file.StandardCopyOption.REPLACE_EXISTING);
            logger.info("Screenshot saved to: {}", screenshot.getAbsolutePath());
        } catch (IOException e) {
            logger.error("Failed to save screenshot: {}", e.getMessage());
        }
    }

    @BeforeMethod(alwaysRun = true)
    public void setup() {
        // Initialize WebDriver
        String targetUrl = getProperty("base.properties", "baseUrl");
        if (targetUrl == null) {
            logger.error("URL is null! Check base.properties file.");
            throw new RuntimeException("Target baseUrl from base.properties is null");
        }

        switch (browser.toLowerCase()) {
            case "firefox":
                driver = new FirefoxDriver();
                break;
            case "edge":
                driver = new EdgeDriver();
                break;
            case "chrome":
            default:
                ChromeOptions options = new ChromeOptions();
                // Check: running in GitHub Actions OR via the -Dheadless=true flag from the console
                boolean isHeadless = System.getenv("GITHUB_ACTIONS") != null || Boolean.getBoolean("headless");

                if (isHeadless) {
                    options.addArguments(
                            "--headless=new",
                            "--no-sandbox",
                            "--disable-dev-shm-usage",
                            "--window-size=1920,1080",
                            "--disable-gpu",
                            "--remote-allow-origins=*"
                    );
                }
                driver = new ChromeDriver(options);
                break;
        }

        WebDriverListener listener = new WDListener();
        driver = new EventFiringDecorator<>(listener).decorate(driver);
        driver.manage().window().maximize();
        driver.get(targetUrl);
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(20));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        if (driver != null) driver.quit();
    }
}