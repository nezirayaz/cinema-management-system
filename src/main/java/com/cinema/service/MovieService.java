package com.cinema.service;

import com.cinema.exception.CinemaException;
import com.cinema.exception.EntityNotFoundException;
import com.cinema.models.Movie;
import com.cinema.repository.MovieRepository;

import java.util.List;
import java.util.logging.Logger;

/**
 * Service layer for Movie business logic.
 */
public class MovieService {
    
    private static final Logger logger = Logger.getLogger(MovieService.class.getName());
    private final MovieRepository movieRepository;
    
    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }
    
    /**
     * Creates a new movie.
     */
    public Movie createMovie(String title, String genre, int durationMinutes, String director, String language) 
            throws CinemaException {
        Movie movie = new Movie(null, title, genre, durationMinutes, director, language);
        return movieRepository.save(movie);
    }
    
    /**
     * Retrieves a movie by ID.
     */
    public Movie getMovieById(Long id) throws EntityNotFoundException {
        return movieRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Movie", id));
    }
    
    /**
     * Retrieves all movies.
     */
    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }
    
    /**
     * Updates movie information.
     */
    public Movie updateMovie(Long id, String title, String genre, Integer durationMinutes, 
                            String director, String language, Double rating, String description) 
            throws CinemaException {
        
        Movie movie = getMovieById(id);
        
        if (title != null) movie.setTitle(title);
        if (genre != null) movie.setGenre(genre);
        if (durationMinutes != null) movie.setDurationMinutes(durationMinutes);
        if (director != null) movie.setDirector(director);
        if (language != null) movie.setLanguage(language);
        if (rating != null) movie.setRating(rating);
        if (description != null) movie.setDescription(description);
        
        return movieRepository.update(movie);
    }
    
    /**
     * Deletes a movie by ID.
     */
    public void deleteMovie(Long id) throws CinemaException {
        if (!movieRepository.deleteById(id)) {
            throw new EntityNotFoundException("Movie", id);
        }
    }
    
    /**
     * Searches movies by title.
     */
    public List<Movie> searchMovies(String query) {
        return movieRepository.searchByTitle(query);
    }
    
    /**
     * Finds movies by genre.
     */
    public List<Movie> getMoviesByGenre(String genre) {
        return movieRepository.findByGenre(genre);
    }
    
    /**
     * Finds movies by director.
     */
    public List<Movie> getMoviesByDirector(String director) {
        return movieRepository.findByDirector(director);
    }
    
    /**
     * Updates movie rating.
     */
    public Movie updateRating(Long movieId, double rating) throws CinemaException {
        Movie movie = getMovieById(movieId);
        movie.setRating(rating);
        return movieRepository.update(movie);
    }
    
    /**
     * Gets total number of movies.
     */
    public long getMovieCount() {
        return movieRepository.count();
    }
}
