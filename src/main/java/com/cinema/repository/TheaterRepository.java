package com.cinema.repository;

import com.cinema.models.Theater;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Repository for Theater entities with additional search capabilities.
 */
public class TheaterRepository extends JsonRepository<Theater> {
    
    private static final String DEFAULT_FILE_PATH = "src/main/resources/theaters.json";
    
    public TheaterRepository() {
        super(DEFAULT_FILE_PATH, Theater.class);
    }
    
    public TheaterRepository(String filePath) {
        super(filePath, Theater.class);
    }
    
    /**
     * Finds theaters by screen type (case-insensitive).
     */
    public List<Theater> findByScreenType(String screenType) {
        return entities.stream()
                .filter(t -> t.getScreenType().equalsIgnoreCase(screenType))
                .collect(Collectors.toList());
    }
    
    /**
     * Finds theaters with capacity greater than or equal to the specified value.
     */
    public List<Theater> findByMinCapacity(int minCapacity) {
        return entities.stream()
                .filter(t -> t.getCapacity() >= minCapacity)
                .collect(Collectors.toList());
    }
    
    /**
     * Finds theaters with wheelchair access.
     */
    public List<Theater> findWithWheelchairAccess() {
        return entities.stream()
                .filter(Theater::isHasWheelchairAccess)
                .collect(Collectors.toList());
    }
    
    /**
     * Finds theaters showing a specific movie.
     */
    public List<Theater> findByMovieId(Long movieId) {
        return entities.stream()
                .filter(t -> t.getCurrentMovieIds().contains(movieId))
                .collect(Collectors.toList());
    }
    
    /**
     * Searches theaters by name (contains query, case-insensitive).
     */
    public List<Theater> searchByName(String query) {
        String lowerQuery = query.toLowerCase();
        return entities.stream()
                .filter(t -> t.getName().toLowerCase().contains(lowerQuery))
                .collect(Collectors.toList());
    }
}
