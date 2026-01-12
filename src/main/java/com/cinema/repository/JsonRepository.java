package com.cinema.repository;

import com.cinema.exception.CinemaException;
import com.cinema.exception.EntityNotFoundException;
import com.cinema.models.BaseEntity;
import com.cinema.utils.JsonUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * JSON-based repository implementation for persistent storage.
 * @param <T> Entity type extending BaseEntity
 */
public abstract class JsonRepository<T extends BaseEntity> implements Repository<T> {
    
    protected final Logger logger = Logger.getLogger(getClass().getName());
    protected final String filePath;
    protected final Class<T> entityClass;
    protected final AtomicLong idGenerator;
    protected List<T> entities;
    
    protected JsonRepository(String filePath, Class<T> entityClass) {
        this.filePath = filePath;
        this.entityClass = entityClass;
        this.entities = new ArrayList<>();
        this.idGenerator = new AtomicLong(0);
        loadFromFile();
    }
    
    /**
     * Loads entities from JSON file.
     */
    protected void loadFromFile() {
        try {
            entities = JsonUtil.readFromFile(filePath, entityClass);
            
            // Update ID generator to max ID + 1
            long maxId = entities.stream()
                    .mapToLong(BaseEntity::getId)
                    .max()
                    .orElse(0L);
            idGenerator.set(maxId);
            
            logger.log(Level.INFO, "Loaded {0} entities from {1}", new Object[]{entities.size(), filePath});
        } catch (IOException e) {
            logger.log(Level.WARNING, "Could not load from file: {0}. Starting with empty repository.", e.getMessage());
            entities = new ArrayList<>();
        }
    }
    
    /**
     * Saves all entities to JSON file.
     */
    protected void saveToFile() throws CinemaException {
        try {
            JsonUtil.writeToFile(entities, filePath);
        } catch (IOException e) {
            throw new CinemaException("Failed to save to file: " + filePath, e);
        }
    }
    
    @Override
    public T save(T entity) throws CinemaException {
        if (entity.getId() == null) {
            entity.setId(idGenerator.incrementAndGet());
        }
        
        entity.validate();
        entities.add(entity);
        saveToFile();
        
        logger.log(Level.INFO, "Saved entity: {0}", entity);
        return entity;
    }
    
    @Override
    public Optional<T> findById(Long id) {
        return entities.stream()
                .filter(e -> e.getId().equals(id))
                .findFirst();
    }
    
    @Override
    public List<T> findAll() {
        return new ArrayList<>(entities);
    }
    
    @Override
    public T update(T entity) throws CinemaException {
        if (entity.getId() == null) {
            throw new CinemaException("Cannot update entity without ID");
        }
        
        entity.validate();
        
        Optional<T> existing = findById(entity.getId());
        if (!existing.isPresent()) {
            throw new EntityNotFoundException(entityClass.getSimpleName(), entity.getId());
        }
        
        entities = entities.stream()
                .map(e -> e.getId().equals(entity.getId()) ? entity : e)
                .collect(Collectors.toList());
        
        entity.touch();
        saveToFile();
        
        logger.log(Level.INFO, "Updated entity: {0}", entity);
        return entity;
    }
    
    @Override
    public boolean deleteById(Long id) throws CinemaException {
        boolean removed = entities.removeIf(e -> e.getId().equals(id));
        
        if (removed) {
            saveToFile();
            logger.log(Level.INFO, "Deleted entity with ID: {0}", id);
        }
        
        return removed;
    }
    
    @Override
    public boolean existsById(Long id) {
        return entities.stream().anyMatch(e -> e.getId().equals(id));
    }
    
    @Override
    public long count() {
        return entities.size();
    }
    
    @Override
    public void deleteAll() throws CinemaException {
        entities.clear();
        saveToFile();
        logger.log(Level.INFO, "Deleted all entities from repository");
    }
}
