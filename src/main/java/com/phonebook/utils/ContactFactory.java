package com.phonebook.utils;

import com.phonebook.model.Contact;
import net.datafaker.Faker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Factory class for generating random Contact objects for testing.
 * Uses DataFaker to produce realistic test data.
 */
public final class ContactFactory {

    private static final Logger logger = LoggerFactory.getLogger(ContactFactory.class);
    private static final Faker faker = new Faker();

    private ContactFactory() {
        // Prevent instantiation
    }

    /**
     * Generates a valid Contact object with realistic random data.
     *
     * @return Contact with valid fields
     */
    public static Contact positiveContact() {
        Contact contact = Contact.builder()
                .name(faker.name().firstName())
                .lastName(faker.name().lastName())
                .phone(faker.phoneNumber().cellPhone().replaceAll("[^0-9+]", ""))
                .email(faker.internet().emailAddress())
                .address(faker.address().fullAddress())
                .description("My work")
                .build();

        logger.debug("Generated random contact: {}", contact);
        return contact;
    }
}