package com.cinema.exception;

/**
 * Base exception class for all cinema-related exceptions.
 */
public class CinemaException extends Exception {
    
    public CinemaException(String message) {
        super(message);
    }
    
    public CinemaException(String message, Throwable cause) {
        super(message, cause);
    }
}
