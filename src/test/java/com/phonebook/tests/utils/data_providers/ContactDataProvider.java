package com.phonebook.tests.utils.data_providers;

import com.phonebook.model.Contact;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.DataProvider;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Provides contact test data from external CSV files.
 * Used for parameterized TestNG tests.
 */
public class ContactDataProvider {

    private static final Logger logger = LoggerFactory.getLogger(ContactDataProvider.class);
    private static final String CONTACTS_CSV_PATH = "src/main/resources/data_csv/data_contacts.csv";

    /**
     * Reads contacts from CSV file and returns them as an iterator for TestNG.
     *
     * @return Iterator of Contact objects
     */
    @DataProvider
    public Iterator<Contact> dataProviderFromFile() {
        List<Contact> contacts = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(CONTACTS_CSV_PATH))) {

            logger.info("Loading contacts from CSV: {}", CONTACTS_CSV_PATH);

            String line;
            while ((line = reader.readLine()) != null) {

                // Skip empty lines
                if (line.trim().isEmpty()) {
                    logger.debug("Skipping empty line in CSV");
                    continue;
                }

                String[] split = line.split(",");

                if (split.length < 6) {
                    logger.warn("Invalid CSV line (expected 6 fields): {}", line);
                    continue;
                }

                Contact contact = Contact.builder()
                        .name(split[0].trim())
                        .lastName(split[1].trim())
                        .email(split[2].trim())
                        .phone(split[3].trim())
                        .address(split[4].trim())
                        .description(split[5].trim())
                        .build();

                contacts.add(contact);
            }

        } catch (IOException e) {
            logger.error("Failed to read contacts CSV file", e);
            throw new RuntimeException("Unable to load contact test data", e);
        }

        logger.info("Loaded {} contacts from CSV", contacts.size());
        return contacts.iterator();
    }
}