package com.cinema.repository;

import com.cinema.exception.CinemaException;
import com.cinema.models.BaseEntity;

import java.util.List;
import java.util.Optional;

/**
 * Generic repository interface for CRUD operations.
 * @param <T> Entity type extending BaseEntity
 */
public interface Repository<T extends BaseEntity> {
    
    /**
     * Saves an entity to the repository.
     */
    T save(T entity) throws CinemaException;
    
    /**
     * Finds an entity by its ID.
     */
    Optional<T> findById(Long id);
    
    /**
     * Retrieves all entities from the repository.
     */
    List<T> findAll();
    
    /**
     * Updates an existing entity.
     */
    T update(T entity) throws CinemaException;
    
    /**
     * Deletes an entity by its ID.
     */
    boolean deleteById(Long id) throws CinemaException;
    
    /**
     * Checks if an entity exists by ID.
     */
    boolean existsById(Long id);
    
    /**
     * Counts total number of entities.
     */
    long count();
    
    /**
     * Deletes all entities from the repository.
     */
    void deleteAll() throws CinemaException;
}
