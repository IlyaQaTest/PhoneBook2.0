package com.phonebook.mydb.tests;

import com.phonebook.mydb.DBHelper;
import org.testng.Assert;
import org.testng.annotations.Test;

public class DatabaseConnectionTest {

    @Test
    public void testInsertAndDeleteContact() {
        String email = "test@db.com";

        DBHelper.insertContact("Test", "User", email, "123456789", "Tel Aviv", "Test contact");
        Assert.assertTrue(DBHelper.isContactExists(email), "Contact should exist after insert");

        DBHelper.deleteContact(email);
        Assert.assertFalse(DBHelper.isContactExists(email), "Contact should be deleted");
    }
}
