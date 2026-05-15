package com.phonebook.model;

import lombok.*;

/**
 * Represents an error message returned by the API.
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ErrorMessageDto {
    private String message;
}