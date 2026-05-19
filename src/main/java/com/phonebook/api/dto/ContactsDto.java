package com.phonebook.api.dto;

import com.phonebook.model.Contact;
import lombok.*;

import java.util.List;

/**
 * Data Transfer Object representing a list of contacts.
 * Used for API responses containing multiple contact entries.
 */
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContactsDto {
    private List<Contact> contacts;
}