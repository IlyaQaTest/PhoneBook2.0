package com.phonebook.utils.ui;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Utility class for reading configuration properties from resource files.
 * Provides a static method to retrieve property values by key.
 */
public class PropertiesReader {

    private static final Logger logger = LoggerFactory.getLogger(PropertiesReader.class);

    /**
     * Reads a property value from a specified properties file located in the resources/properties directory.
     *
     * @param fileName the name of the properties file.
     * @param key      the property key to retrieve.
     * @return the property value, or null if an error occurs.
     */
    public static String getProperty(String fileName, String key) {
        Properties properties = new Properties();

        try (InputStream is = PropertiesReader.class.getClassLoader()
                .getResourceAsStream("properties/" + fileName)) {

            if (is == null) {
                logger.error("Configuration file not found: properties/{}", fileName);
                throw new RuntimeException("File not found: " + fileName);
            }

            properties.load(is);
            return properties.getProperty(key);

        } catch (IOException e) {
            logger.error("Error reading properties file: {}", fileName, e);
            return null;
        }
    }
}