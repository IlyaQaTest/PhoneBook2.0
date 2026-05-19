package com.phonebook.api.dto;

import lombok.*;

/**
 * Data Transfer Object representing an authentication token.
 * Used for storing and passing the token between API requests.
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