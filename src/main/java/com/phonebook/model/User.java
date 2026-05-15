package com.phonebook.model;

import lombok.*;

/**
 * Represents a user entity used for authentication.
 */
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private String username;
    private String password;
}