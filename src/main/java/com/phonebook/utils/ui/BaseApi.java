package com.phonebook.utils.ui;

import com.google.gson.Gson;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;

/**
 * Defines base constants and shared objects for API interactions in the PhoneBook application.
 * Includes endpoints, media types, and reusable HTTP client configuration.
 */
public interface BaseApi {

    String BASE_URL = "https://contactapp-telran-backend.herokuapp.com";

    String REGISTRATION_URL = "/v1/user/registration/usernamepassword";
    String LOGIN_URL = "/v1/user/login/usernamepassword";
    String ADD_NEW_CONTACT_URL = "/v1/contacts";
    String GET_ALL_CONTACTS_URL = "/v1/contacts";
    String EDIT_CONTACT_URL = "/v1/contacts";
    String DELETE_CONTACT_URL = "/v1/contacts/";

    Gson GSON = new Gson();
    OkHttpClient OK_HTTP_CLIENT = new OkHttpClient();

    MediaType JSON = MediaType.get("application/json");
    MediaType TEXT = MediaType.get("text/plain");

    String AUTH = "Authorization";
}