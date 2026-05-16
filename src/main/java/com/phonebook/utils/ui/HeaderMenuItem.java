package com.phonebook.utils.ui;

import lombok.Getter;

/**
 * Enum representing header menu items in the PhoneBook web application.
 * Each item stores its corresponding XPath locator for navigation.
 */
@Getter
public enum HeaderMenuItem {

    HOME("//a[text()='HOME']"),
    ABOUT("//a[text()='ABOUT']"),
    CONTACTS("//a[text()='CONTACTS']"),
    ADD("//a[text()='ADD']"),
    LOGIN("//a[text()='LOGIN']"),
    SIGN_OUT("//button[text()='Sign Out']");

    private final String locator;

    HeaderMenuItem(String locator) {
        this.locator = locator;
    }
}