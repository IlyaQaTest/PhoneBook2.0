package com.phonebook.model;

import lombok.*;

/**
 * Represents a contact entity in the PhoneBook application.
 * Used across API, mobile, and UI layers.
 */
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class Contact {
    private String id;
    private String name;
    private String lastName;
    private String phone;
    private String email;
    private String address;
    private String description ;
}
