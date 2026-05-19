package com.phonebook.core.config;

import java.io.IOException;
import java.util.Properties;

public class PropertiesReader {
    public static String getProperty(String fileName, String key) {
        try {
            Properties properties = new Properties();
            ClassLoader loader = PropertiesReader.class.getClassLoader();
            try (var inputStream = loader.getResourceAsStream("properties/" + fileName)) {
                if (inputStream == null) {
                    System.out.println("ERROR: Cannot find file in resources: properties/" + fileName);
                    return null;
                }
                properties.load(inputStream);
                return properties.getProperty(key);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to load properties file: " + fileName, e);
        }
    }
}