package com.cinema.models;

import com.cinema.exception.ValidationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for Movie model.
 */
class MovieTest {
    
    private Movie movie;
    
    @BeforeEach
    void setUp() {
        movie = new Movie(1L, "The Matrix", "Sci-Fi", 136, "Wachowski Brothers", "English");
    }
    
    @Test
    void testMovieCreation() {
        assertNotNull(movie);
        assertEquals(1L, movie.getId());
        assertEquals("The Matrix", movie.getTitle());
        assertEquals("Sci-Fi", movie.getGenre());
        assertEquals(136, movie.getDurationMinutes());
        assertEquals("Wachowski Brothers", movie.getDirector());
        assertEquals("English", movie.getLanguage());
        assertEquals(0.0, movie.getRating());
    }
    
    @Test
    void testGetFormattedDuration() {
        assertEquals("2h 16min", movie.getFormattedDuration());
        
        Movie shortMovie = new Movie(2L, "Short Film", "Drama", 45, "Director", "English");
        assertEquals("45min", shortMovie.getFormattedDuration());
    }
    
    @Test
    void testSetRating() {
        movie.setRating(8.7);
        assertEquals(8.7, movie.getRating());
    }
    
    @Test
    void testValidateSuccess() {
        assertDoesNotThrow(() -> movie.validate());
    }
    
    @Test
    void testValidateEmptyTitle() {
        movie.setTitle("");
        assertThrows(ValidationException.class, () -> movie.validate());
    }
    
    @Test
    void testValidateEmptyGenre() {
        movie.setGenre("");
        assertThrows(ValidationException.class, () -> movie.validate());
    }
    
    @Test
    void testValidateNegativeDuration() {
        movie.setDurationMinutes(-10);
        assertThrows(ValidationException.class, () -> movie.validate());
    }
    
    @Test
    void testValidateExcessiveDuration() {
        movie.setDurationMinutes(600);
        assertThrows(ValidationException.class, () -> movie.validate());
    }
    
    @Test
    void testValidateInvalidRating() {
        movie.setRating(11.0);
        assertThrows(ValidationException.class, () -> movie.validate());
        
        movie.setRating(-1.0);
        assertThrows(ValidationException.class, () -> movie.validate());
    }
    
    @Test
    void testToString() {
        String str = movie.toString();
        assertTrue(str.contains("The Matrix"));
        assertTrue(str.contains("Sci-Fi"));
    }
}
