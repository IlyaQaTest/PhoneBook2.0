package com.phonebook.mydb;

/**
 * Helper class for Mobile integration tests with PhoneBook database.
 * Ensures mobile actions reflect correctly in DB.
 */
public final class MobileHelperBD {

    private MobileHelperBD() {}

    public static boolean isContactSyncedToDb(String email) {
        return DBHelper.isContactExists(email);
    }

    public static void removeMobileContact(String email) {
        DBHelper.deleteContact(email);
    }
}
