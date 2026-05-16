package com.phonebook.utils.ui;

import com.phonebook.model.Contact;
import net.datafaker.Faker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Factory class for generating Contact objects with randomized valid data.
 * Uses DataFaker to create realistic test data for positive scenarios.
 */
public class ContactFactory {

    private static final Logger logger = LoggerFactory.getLogger(ContactFactory.class);
    private static final Faker faker = new Faker();

    /**
     * Generates a valid Contact object with randomized data.
     *
     * @return a Contact instance populated with realistic test data.
     */
    public static Contact positiveContact() {
        Contact contact = Contact.builder()
                .name(faker.name().firstName())
                .lastName(faker.name().lastName())
                .phone(faker.number().digits(13))
                .email(faker.internet().emailAddress())
                .address(faker.address().fullAddress())
                .description("My work")
                .build();

        logger.info("Generated new contact: {} {}", contact.getName(), contact.getLastName());
        return contact;
    }
}