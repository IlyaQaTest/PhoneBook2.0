package com.phonebook.utils.ui;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Utility class for capturing and saving screenshots during test execution.
 * Screenshots are stored in the test_logs/screenshots directory with timestamped filenames.
 */
public class TakeScreenShot {

    private static final Logger logger = LoggerFactory.getLogger(TakeScreenShot.class);
    private static final String SCREENSHOT_PATH = "src/test/resources/test_logs/screenshots/";

    /**
     * Captures a screenshot and saves it to the predefined directory.
     *
     * @param screenshot the TakesScreenshot instance (usually WebDriver).
     */
    public static void takeScreenShot(TakesScreenshot screenshot) {
        String fileName = createFileName();
        File scrFile = screenshot.getScreenshotAs(OutputType.FILE);
        Path destPath = Paths.get(fileName);

        try {
            if (destPath.getParent() != null) {
                Files.createDirectories(destPath.getParent());
            }
            Files.copy(scrFile.toPath(), destPath);
            logger.info("Screenshot saved: {}", fileName);
        } catch (IOException e) {
            logger.error("Failed to save screenshot: {}", fileName, e);
            throw new RuntimeException("Failed to save screenshot: " + fileName, e);
        }
    }

    private static String createFileName() {
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
        return SCREENSHOT_PATH + "scr-" + timeStamp + ".png";
    }
}