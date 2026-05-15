package com.phonebook.model;

import lombok.*;

/**
 * Represents an authentication token returned by the API.
 * Used for authorized requests across the test framework.
 */
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TokenDto {
    private String token;
}