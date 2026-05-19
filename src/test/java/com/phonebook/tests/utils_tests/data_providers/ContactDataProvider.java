package com.phonebook.tests.utils_tests.data_providers;


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

import static com.phonebook.model.factory.ContactFactory.positiveContact;


@SuppressWarnings("LoggingSimilarMessage")
public class ContactDataProvider {
    private static final Logger logger = LoggerFactory.getLogger(ContactDataProvider.class);

    @DataProvider
    public Iterator<Contact> dataProviderFromFile() {
        List<Contact> list = new ArrayList<>();
        try (BufferedReader bufferedReader =
                     new BufferedReader(new FileReader
                             ("src/main/resources/data_csv/data_contacts.csv"))) {
            String line = bufferedReader.readLine();
            while (line != null) {
                String[] splitArray = line.split(",");
                list.add(Contact.builder()
                        .name(splitArray[0])
                        .lastName(splitArray[1])
                        .email(splitArray[2])
                        .phone(splitArray[3])
                        .address(splitArray[4])
                        .description(splitArray[5])
                        .build());
                line = bufferedReader.readLine();
            }

        } catch (IOException e) {
            // Исправлено: используем логгер вместо printStackTrace
            logger.error("Error reading file: {}", e.getMessage());
            throw new RuntimeException("IO exception", e);
        }
        return list.iterator();
    }
    @DataProvider
    public Iterator<Contact> dataProviderFromFileWrongPhone() {
        List<Contact> list = new ArrayList<>();
        Contact contact = positiveContact();
        // Используем путь к тестовым ресурсам, если файл там
        try (BufferedReader bufferedReader =
                     new BufferedReader(new FileReader
                             ("src/test/resources/data_csv/dp_wrong_phone.csv"))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                list.add(Contact.builder()
                        .name(contact.getName())
                        .lastName(contact.getLastName())
                        .email(contact.getEmail())
                        .phone(line)
                        .address(contact.getAddress())
                        .description(contact.getDescription())
                        .build());
            }
        } catch (IOException e) {
            logger.error("Error reading wrong phone file: {}", e.getMessage());
            throw new RuntimeException("IO exception", e);
        }
        return list.iterator();
    }
    @DataProvider
    public Iterator<Contact> dataProviderFromFile_WrongPhone() {
        List<Contact> list = new ArrayList<>();
        Contact contact = positiveContact();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("src/test/resources/data_csv/dp_wrong_phone.csv"))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                list.add(Contact.builder()
                        .name(contact.getName())
                        .lastName(contact.getLastName())
                        .email(contact.getEmail())
                        .phone(line)
                        .address(contact.getAddress())
                        .description(contact.getDescription())
                        .build());
            }
        } catch (IOException e) {
            logger.error("Error reading wrong phone file: {}", e.getMessage());
            throw new RuntimeException("IO exception", e);
        }
        return list.iterator();
    }

    @DataProvider
    public Iterator<Contact> dataProviderFromFile_Wrong_EmptyField() {
        List<Contact> list = new ArrayList<>();
        Contact contact = positiveContact();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("src/main/resources/data_csv/dp_empty_field.csv"))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] splitArray = line.split(",");
                list.add(Contact.builder()
                        .name(splitArray[0])
                        .lastName(splitArray[1])
                        .email(contact.getEmail())
                        .phone(contact.getPhone())
                        .address(splitArray[2])
                        .description(contact.getDescription())
                        .build());
            }
        } catch (IOException e) {
            logger.error("Error reading empty field file: {}", e.getMessage());
            throw new RuntimeException("IO exception", e);
        }
        return list.iterator();
    }
}
