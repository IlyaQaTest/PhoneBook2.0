package com.phonebook.mydb.model;

import lombok.*;

/**
 * Model class representing a contact record in the PhoneBook database.
 * Used exclusively for DB integration tests.
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ContactDb {
    private String name;
    private String lastName;
    private String email;
    private String phone;
    private String address;
    private String description;

}
