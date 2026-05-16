package com.phonebook.utils.ui;

import com.phonebook.model.User;
import net.datafaker.Faker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Factory class for generating User objects with randomized valid data.
 * Uses DataFaker to create realistic test users for positive scenarios.
 */
public class UserFactory {

    private static final Logger logger = LoggerFactory.getLogger(UserFactory.class);
    private static final Faker faker = new Faker();

    /**
     * Generates a valid User object with randomized email and a predefined secure password.
     *
     * @return a User instance populated with realistic test data.
     */
    public static User positiveUser() {
        User user = new User(faker.internet().emailAddress(), "Qwerty145$");
        logger.info("Generated new user: {}", user.getUsername());
        return user;
    }
}