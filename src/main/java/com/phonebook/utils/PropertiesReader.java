package com.phonebook.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Utility class for reading .properties files from the resources/properties directory.
 */
public final class PropertiesReader {

    private static final Logger logger = LoggerFactory.getLogger(PropertiesReader.class);
    private static final String PROPERTIES_FOLDER = "properties/";

    private PropertiesReader() {
        // Prevent instantiation
    }

    /**
     * Reads a property value from a .properties file located in resources/properties.
     *
     * @param fileName name of the properties file (e.g. "config.properties")
     * @param key      property key
     * @return property value or null if not found
     */
    public static String getProperty(String fileName, String key) {
        Properties properties = new Properties();
        String fullPath = PROPERTIES_FOLDER + fileName;

        try (InputStream inputStream = PropertiesReader.class.getClassLoader().getResourceAsStream(fullPath)) {

            if (inputStream == null) {
                logger.error("Properties file not found: {}", fullPath);
                return null;
            }

            properties.load(inputStream);
            String value = properties.getProperty(key);

            if (value == null) {
                logger.warn("Key '{}' not found in file {}", key, fullPath);
            }

            return value;

        } catch (IOException e) {
            logger.error("Failed to load properties file: {}", fullPath, e);
            throw new RuntimeException("Unable to read properties file: " + fullPath, e);
        }
    }
}