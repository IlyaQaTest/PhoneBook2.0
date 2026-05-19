package com.phonebook.api.client;

import com.google.gson.Gson;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;

/**
 * Base API configuration interface.
 * Contains shared constants and reusable objects for all API clients.
 */
public interface BaseApi {

    // Base URL for the backend service
    String BASE_URL = "https://contactapp-telran-backend.herokuapp.com";

    // Endpoints
    String REGISTRATION_URL = "/v1/user/registration/usernamepassword";
    String LOGIN_URL = "/v1/user/login/usernamepassword";
    String ADD_NEW_CONTACT_URL = "/v1/contacts";
    String GET_ALL_CONTACTS_URL = "/v1/contacts";
    String EDIT_CONTACT_URL = "/v1/contacts";
    String DELETE_CONTACT_URL = "/v1/contacts/";

    // Common utilities
    Gson GSON = new Gson();
    OkHttpClient OK_HTTP_CLIENT = new OkHttpClient();

    // Media types
    MediaType JSON = MediaType.get("application/json");
    MediaType TEXT = MediaType.get("text/plain");

    // Header keys
    String AUTH = "Authorization";
}