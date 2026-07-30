package com.phonebook.mydb.config;

import com.phonebook.core.config.PropertiesReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Configuration class for API tests related to the database.
 * Reads API parameters from base.properties using PropertiesReader.
 */
public class ApiConfigBD {

    public static final Logger logger = LoggerFactory.getLogger(ApiConfigBD.class);

    public static final String API_BASE_URL =
            PropertiesReader.getProperty("base.properties", "api.baseUrl");

    public static final String API_REGISTRATION_URL =
            PropertiesReader.getProperty("base.properties", "api.registration");

    public static final String API_LOGIN_URL =
            PropertiesReader.getProperty("base.properties", "api.login");

    public static final String API_CONTACTS_URL =
            PropertiesReader.getProperty("base.properties", "api.contacts");

    public static final String API_USER =
            PropertiesReader.getProperty("base.properties", "api.user");

    public static final String API_PASSWORD =
            PropertiesReader.getProperty("base.properties", "api.password");

    // DB-specific constants
    public static final String BASE_DB_URL = API_BASE_URL;
    public static final String AUTH_DB = "Authorization";
    public static final String GET_ALL_CONTACTS_DB_URL = API_CONTACTS_URL;
    public static final String ADD_CONTACT_DB_URL = API_CONTACTS_URL;
    public static final String DELETE_CONTACT_DB_URL = API_CONTACTS_URL + "/";

    static {
        logger.info("API configuration loaded successfully for DB integration tests.");
        logger.info("Base URL: {}", API_BASE_URL);
    }
}
