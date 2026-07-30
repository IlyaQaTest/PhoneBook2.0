package com.phonebook.mydb.tests;

import com.phonebook.api.client.ILogin;
import com.phonebook.api.dto.TokenDto;
import com.phonebook.mydb.helpers.ApiHelperBD;
import com.phonebook.mydb.model.ContactDb;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import java.io.IOException;

/**
 * Integration tests for PhoneBook DB and API synchronization.
 * Validates that API operations reflect correctly in the database.
 */
public class dbAPITests implements ILogin {

    private TokenDto token;

    @BeforeClass
    public void setup() {
        token = login_get_token();
    }

    @Test
    public void addContactAndVerifyInDb() throws IOException {
        SoftAssert softAssert = new SoftAssert();

        ContactDb contactDb = new ContactDb(
                "David",
                "Levi",
                "david@test.com",
                "0501234567",
                "Rishon LeZion",
                "API test contact"
        );

        boolean isAdded = ApiHelperBD.addContactViaApiAndVerify(contactDb, token);
        softAssert.assertTrue(isAdded, "Contact should exist in DB after API creation");

        softAssert.assertAll();
    }

    @Test
    public void deleteContactAndVerifyRemoval() throws IOException {
        SoftAssert softAssert = new SoftAssert();

        ContactDb contactDb = new ContactDb(
                "Test",
                "Delete",
                "delete@test.com",
                "0507654321",
                "Tel Aviv",
                "API delete test"
        );

        boolean isAdded = ApiHelperBD.addContactViaApiAndVerify(contactDb, token);
        softAssert.assertTrue(isAdded, "Contact should exist in DB before deletion");

        boolean isDeleted = ApiHelperBD.deleteContactViaApiAndVerify(contactDb, token);
        softAssert.assertTrue(isDeleted, "Contact should be removed from DB after deletion");

        softAssert.assertAll();
    }
}
