package com.phonebook.utils.api;

import com.google.gson.Gson;

/**
 * Centralized API configuration and constants for PhoneBook backend.
 * Contains base URL, endpoints, headers, and shared serializers.
 */
public final class BaseApi {

    private BaseApi() {
        // Prevent instantiation
    }

    public static final String BASE_URL = "https://contactapp-telran-backend.herokuapp.com";

    // User endpoints
    public static final String REGISTRATION_URL = "/v1/user/registration/usernamepassword";
    public static final String LOGIN_URL = "/v1/user/login/usernamepassword";

    // Contact endpoints
    public static final String CONTACTS_URL = "/v1/contacts";
    public static final String DELETE_CONTACT_URL = "/v1/contacts/"; // + {id}

    // Headers
    public static final String AUTH = "Authorization";

    // Shared JSON serializer
    public static final Gson GSON = new Gson();
}