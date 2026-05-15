package com.phonebook.model;

import lombok.*;

/**
 * Represents a simple API response containing a message string.
 * Used for success or error messages returned by the server.
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@EqualsAndHashCode
public class ResponseMessageDto {

    private String message;

    /**
     * Checks whether the response message matches the expected value.
     *
     * @param expected Expected message text
     * @return true if messages match, false otherwise
     */
    public boolean isMessage(String expected) {
        return expected != null && expected.equals(message);
    }
}