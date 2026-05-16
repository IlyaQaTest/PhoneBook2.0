package com.phonebook.api.manager;

import lombok.Getter;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.events.EventFiringDecorator;
import org.openqa.selenium.support.events.WebDriverListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.phonebook.utils.ui.WDListener;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import static com.phonebook.utils.api.PropertiesReader.getProperty;

/**
 * Manages WebDriver initialization, configuration, and teardown for UI tests.
 */
public class AppManager {

    @Getter
    private WebDriver driver;
    private static final Logger logger = LoggerFactory.getLogger(AppManager.class);
    private static final String browser = System.getProperty("browser", "chrome");
    private static final Duration PAGE_LOAD_TIMEOUT = Duration.ofSeconds(20);
    private static final Duration IMPLICIT_WAIT_TIMEOUT = Duration.ofSeconds(15);

    /**
     * Takes a screenshot and saves it to the reports directory.
     */
    public void takeScreenshot(String methodName) {
        if (driver == null) return;

        File screenshot = new File("build/reports/tests/smoke_tests/screenshots/screenshot-" + methodName + ".png");
        File parentDir = screenshot.getParentFile();
        if (parentDir != null && !parentDir.exists() && !parentDir.mkdirs()) {
            logger.warn("Could not create directory: {}", parentDir.getAbsolutePath());
        }

        File tmp = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {
            Files.copy(tmp.toPath(), screenshot.toPath(), StandardCopyOption.REPLACE_EXISTING);
            logger.info("Screenshot saved to: {}", screenshot.getAbsolutePath());
        } catch (IOException e) {
            logger.error("Failed to save screenshot: {}", e.getMessage(), e);
        }
    }

    @BeforeMethod(alwaysRun = true)
    public void setup() {
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
                if (System.getenv("GITHUB_ACTIONS") != null) {
                    options.addArguments("--headless=new", "--no-sandbox", "--disable-dev-shm-usage",
                            "--window-size=1920,1080", "--disable-gpu", "--remote-allow-origins=*");
                }
                driver = new ChromeDriver(options);
                break;
        }

        WebDriverListener listener = new WDListener();
        driver = new EventFiringDecorator<>(listener).decorate(driver);
        driver.manage().window().maximize();
        driver.get(targetUrl);
        driver.manage().timeouts().pageLoadTimeout(PAGE_LOAD_TIMEOUT);
        driver.manage().timeouts().implicitlyWait(IMPLICIT_WAIT_TIMEOUT);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}