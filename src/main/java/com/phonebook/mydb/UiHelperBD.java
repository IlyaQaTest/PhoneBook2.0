package com.phonebook.mydb;

/**
 * Helper class for UI integration tests with PhoneBook database.
 * Provides simplified methods for UI validation against DB.
 */
public final class UiHelperBD {

    private UiHelperBD() {}

    public static boolean isContactVisibleInUi(String email) {
        return DBHelper.isContactExists(email);
    }

    public static void cleanUpUiContact(String email) {
        DBHelper.deleteContact(email);
    }
}
