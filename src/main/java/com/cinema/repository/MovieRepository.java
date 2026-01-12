package com.cinema.repository;

import com.cinema.models.Movie;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Repository for Movie entities with additional search capabilities.
 */
public class MovieRepository extends JsonRepository<Movie> {
    
    private static final String DEFAULT_FILE_PATH = "src/main/resources/movies.json";
    
    public MovieRepository() {
        super(DEFAULT_FILE_PATH, Movie.class);
    }
    
    public MovieRepository(String filePath) {
        super(filePath, Movie.class);
    }
    
    /**
     * Finds movies by genre (case-insensitive).
     */
    public List<Movie> findByGenre(String genre) {
        return entities.stream()
                .filter(m -> m.getGenre().equalsIgnoreCase(genre))
                .collect(Collectors.toList());
    }
    
    /**
     * Finds movies by director (case-insensitive).
     */
    public List<Movie> findByDirector(String director) {
        return entities.stream()
                .filter(m -> director.equalsIgnoreCase(m.getDirector()))
                .collect(Collectors.toList());
    }
    
    /**
     * Searches movies by title (contains query, case-insensitive).
     */
    public List<Movie> searchByTitle(String query) {
        String lowerQuery = query.toLowerCase();
        return entities.stream()
                .filter(m -> m.getTitle().toLowerCase().contains(lowerQuery))
                .collect(Collectors.toList());
    }
    
    /**
     * Finds movies with rating greater than or equal to the specified value.
     */
    public List<Movie> findByMinRating(double minRating) {
        return entities.stream()
                .filter(m -> m.getRating() >= minRating)
                .collect(Collectors.toList());
    }
    
    /**
     * Finds movies by language.
     */
    public List<Movie> findByLanguage(String language) {
        return entities.stream()
                .filter(m -> language.equalsIgnoreCase(m.getLanguage()))
                .collect(Collectors.toList());
    }
}
