package com.cinema.exception;

/**
 * Exception thrown when entity validation fails.
 */
public class ValidationException extends CinemaException {
    
    public ValidationException(String message) {
        super(message);
    }
}
