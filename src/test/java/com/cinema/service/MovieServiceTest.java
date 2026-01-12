package com.cinema.service;

import com.cinema.exception.CinemaException;
import com.cinema.exception.EntityNotFoundException;
import com.cinema.models.Movie;
import com.cinema.repository.MovieRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;

import java.io.File;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for MovieService.
 */
class MovieServiceTest {
    
    private MovieService movieService;
    private MovieRepository movieRepository;
    private static final String TEST_FILE = "src/test/resources/test-movies.json";
    
    @BeforeEach
    void setUp() {
        movieRepository = new MovieRepository(TEST_FILE);
        movieService = new MovieService(movieRepository);
    }
    
    @AfterEach
    void tearDown() throws CinemaException {
        movieRepository.deleteAll();
        new File(TEST_FILE).delete();
    }
    
    @Test
    void testCreateMovie() throws CinemaException {
        Movie movie = movieService.createMovie("The Matrix", "Sci-Fi", 136, "Wachowski Brothers", "English");
        
        assertNotNull(movie);
        assertNotNull(movie.getId());
        assertEquals("The Matrix", movie.getTitle());
        assertEquals("Sci-Fi", movie.getGenre());
        assertEquals(136, movie.getDurationMinutes());
    }
    
    @Test
    void testGetMovieById() throws CinemaException {
        Movie created = movieService.createMovie("The Matrix", "Sci-Fi", 136, "Wachowski Brothers", "English");
        Movie retrieved = movieService.getMovieById(created.getId());
        
        assertEquals(created.getId(), retrieved.getId());
        assertEquals("The Matrix", retrieved.getTitle());
    }
    
    @Test
    void testGetMovieByIdNotFound() {
        assertThrows(EntityNotFoundException.class, () -> {
            movieService.getMovieById(999L);
        });
    }
    
    @Test
    void testGetAllMovies() throws CinemaException {
        movieService.createMovie("The Matrix", "Sci-Fi", 136, "Wachowski Brothers", "English");
        movieService.createMovie("Inception", "Sci-Fi", 148, "Christopher Nolan", "English");
        
        List<Movie> movies = movieService.getAllMovies();
        assertEquals(2, movies.size());
    }
    
    @Test
    void testUpdateMovie() throws CinemaException {
        Movie movie = movieService.createMovie("The Matrix", "Sci-Fi", 136, "Wachowski Brothers", "English");
        
        Movie updated = movieService.updateMovie(movie.getId(), "The Matrix Reloaded", null, null, null, null, null, null);
        
        assertEquals("The Matrix Reloaded", updated.getTitle());
        assertEquals("Sci-Fi", updated.getGenre());
    }
    
    @Test
    void testDeleteMovie() throws CinemaException {
        Movie movie = movieService.createMovie("The Matrix", "Sci-Fi", 136, "Wachowski Brothers", "English");
        
        movieService.deleteMovie(movie.getId());
        
        assertThrows(EntityNotFoundException.class, () -> {
            movieService.getMovieById(movie.getId());
        });
    }
    
    @Test
    void testSearchMovies() throws CinemaException {
        movieService.createMovie("The Matrix", "Sci-Fi", 136, "Wachowski Brothers", "English");
        movieService.createMovie("The Matrix Reloaded", "Sci-Fi", 138, "Wachowski Brothers", "English");
        movieService.createMovie("Inception", "Sci-Fi", 148, "Christopher Nolan", "English");
        
        List<Movie> results = movieService.searchMovies("Matrix");
        assertEquals(2, results.size());
    }
    
    @Test
    void testGetMoviesByGenre() throws CinemaException {
        movieService.createMovie("The Matrix", "Sci-Fi", 136, "Wachowski Brothers", "English");
        movieService.createMovie("Inception", "Sci-Fi", 148, "Christopher Nolan", "English");
        movieService.createMovie("The Godfather", "Crime", 175, "Francis Ford Coppola", "English");
        
        List<Movie> sciFiMovies = movieService.getMoviesByGenre("Sci-Fi");
        assertEquals(2, sciFiMovies.size());
    }
    
    @Test
    void testUpdateRating() throws CinemaException {
        Movie movie = movieService.createMovie("The Matrix", "Sci-Fi", 136, "Wachowski Brothers", "English");
        
        Movie updated = movieService.updateRating(movie.getId(), 8.7);
        assertEquals(8.7, updated.getRating());
    }
    
    @Test
    void testGetMovieCount() throws CinemaException {
        assertEquals(0, movieService.getMovieCount());
        
        movieService.createMovie("The Matrix", "Sci-Fi", 136, "Wachowski Brothers", "English");
        assertEquals(1, movieService.getMovieCount());
    }
}
