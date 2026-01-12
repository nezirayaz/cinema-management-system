package com.cinema.exception;

/**
 * Exception thrown when an entity is not found in the system.
 */
public class EntityNotFoundException extends CinemaException {
    
    public EntityNotFoundException(String entityType, Long id) {
        super(String.format("%s with ID %d not found", entityType, id));
    }
    
    public EntityNotFoundException(String message) {
        super(message);
    }
}
