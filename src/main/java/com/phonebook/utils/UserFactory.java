package com.phonebook.utils;

import com.phonebook.model.User;
import net.datafaker.Faker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Factory class for generating random User objects for testing.
 * Uses DataFaker to produce realistic test data.
 */
public final class UserFactory {

    private static final Logger logger = LoggerFactory.getLogger(UserFactory.class);
    private static final Faker faker = new Faker();

    private UserFactory() {
        // Prevent instantiation
    }

    /**
     * Generates a valid User object with random email and strong password.
     *
     * @return User with valid credentials
     */
    public static User positiveUser() {
        User user = User.builder()
                .username(faker.internet().emailAddress())
                .password("Password123!")
                .build();

        logger.debug("Generated random user: {}", user);
        return user;
    }
}