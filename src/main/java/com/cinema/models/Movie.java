package com.cinema.models;

import com.cinema.exception.ValidationException;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

/**
 * Represents a movie in the cinema system.
 */
public class Movie extends BaseEntity {
    
    private String title;
    private String genre;
    private int durationMinutes;
    private String director;
    private String language;
    private double rating;
    private String description;
    
    public Movie() {
        super();
    }
    
    @JsonCreator
    public Movie(
            @JsonProperty("id") Long id,
            @JsonProperty("title") String title,
            @JsonProperty("genre") String genre,
            @JsonProperty("durationMinutes") int durationMinutes,
            @JsonProperty("director") String director,
            @JsonProperty("language") String language) {
        super(id);
        this.title = title;
        this.genre = genre;
        this.durationMinutes = durationMinutes;
        this.director = director;
        this.language = language;
        this.rating = 0.0;
    }
    
    @Override
    public void validate() throws ValidationException {
        if (title == null || title.trim().isEmpty()) {
            throw new ValidationException("Movie title cannot be empty");
        }
        if (genre == null || genre.trim().isEmpty()) {
            throw new ValidationException("Movie genre cannot be empty");
        }
        if (durationMinutes <= 0) {
            throw new ValidationException("Movie duration must be positive");
        }
        if (durationMinutes > 500) {
            throw new ValidationException("Movie duration seems unrealistic (max 500 minutes)");
        }
        if (rating < 0.0 || rating > 10.0) {
            throw new ValidationException("Movie rating must be between 0.0 and 10.0");
        }
    }
    
    /**
     * Gets formatted duration as hours and minutes.
     */
    public String getFormattedDuration() {
        int hours = durationMinutes / 60;
        int minutes = durationMinutes % 60;
        if (hours > 0) {
            return String.format("%dh %dmin", hours, minutes);
        }
        return String.format("%dmin", minutes);
    }
    
    // Getters and Setters
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getGenre() {
        return genre;
    }
    
    public void setGenre(String genre) {
        this.genre = genre;
    }
    
    public int getDurationMinutes() {
        return durationMinutes;
    }
    
    public void setDurationMinutes(int durationMinutes) {
        this.durationMinutes = durationMinutes;
    }
    
    public String getDirector() {
        return director;
    }
    
    public void setDirector(String director) {
        this.director = director;
    }
    
    public String getLanguage() {
        return language;
    }
    
    public void setLanguage(String language) {
        this.language = language;
    }
    
    public double getRating() {
        return rating;
    }
    
    public void setRating(double rating) {
        this.rating = rating;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    @Override
    public String toString() {
        return String.format("Movie[id=%d, title='%s', genre=%s, duration=%s, director=%s, rating=%.1f]",
                getId(), title, genre, getFormattedDuration(), director, rating);
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Movie movie = (Movie) o;
        return Objects.equals(title, movie.title) && Objects.equals(director, movie.director);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), title, director);
    }
}
