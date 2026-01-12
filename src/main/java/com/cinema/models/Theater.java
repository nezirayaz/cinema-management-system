package com.cinema.models;

import com.cinema.exception.ValidationException;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents a theater/hall in the cinema.
 */
public class Theater extends BaseEntity {
    
    private String name;
    private int capacity;
    private String screenType; // e.g., "Standard", "IMAX", "3D", "4DX"
    private boolean hasWheelchairAccess;
    private List<Long> currentMovieIds;
    
    public Theater() {
        super();
        this.currentMovieIds = new ArrayList<>();
        this.hasWheelchairAccess = true;
    }
    
    @JsonCreator
    public Theater(
            @JsonProperty("id") Long id,
            @JsonProperty("name") String name,
            @JsonProperty("capacity") int capacity,
            @JsonProperty("screenType") String screenType) {
        super(id);
        this.name = name;
        this.capacity = capacity;
        this.screenType = screenType;
        this.currentMovieIds = new ArrayList<>();
        this.hasWheelchairAccess = true;
    }
    
    @Override
    public void validate() throws ValidationException {
        if (name == null || name.trim().isEmpty()) {
            throw new ValidationException("Theater name cannot be empty");
        }
        if (capacity <= 0) {
            throw new ValidationException("Theater capacity must be positive");
        }
        if (capacity > 1000) {
            throw new ValidationException("Theater capacity seems unrealistic (max 1000 seats)");
        }
        if (screenType == null || screenType.trim().isEmpty()) {
            throw new ValidationException("Theater screen type cannot be empty");
        }
    }
    
    /**
     * Adds a movie to this theater's schedule.
     */
    public void addMovie(Long movieId) {
        if (movieId != null && !currentMovieIds.contains(movieId)) {
            currentMovieIds.add(movieId);
            touch();
        }
    }
    
    /**
     * Removes a movie from this theater's schedule.
     */
    public void removeMovie(Long movieId) {
        if (currentMovieIds.remove(movieId)) {
            touch();
        }
    }
    
    /**
     * Checks if theater is available (has capacity).
     */
    public boolean hasAvailableSeats(int requestedSeats) {
        return requestedSeats <= capacity;
    }
    
    // Getters and Setters
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public int getCapacity() {
        return capacity;
    }
    
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
    
    public String getScreenType() {
        return screenType;
    }
    
    public void setScreenType(String screenType) {
        this.screenType = screenType;
    }
    
    public boolean isHasWheelchairAccess() {
        return hasWheelchairAccess;
    }
    
    public void setHasWheelchairAccess(boolean hasWheelchairAccess) {
        this.hasWheelchairAccess = hasWheelchairAccess;
    }
    
    public List<Long> getCurrentMovieIds() {
        return new ArrayList<>(currentMovieIds);
    }
    
    public void setCurrentMovieIds(List<Long> currentMovieIds) {
        this.currentMovieIds = currentMovieIds != null ? new ArrayList<>(currentMovieIds) : new ArrayList<>();
    }
    
    @Override
    public String toString() {
        return String.format("Theater[id=%d, name='%s', capacity=%d, screenType=%s, movies=%d]",
                getId(), name, capacity, screenType, currentMovieIds.size());
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Theater theater = (Theater) o;
        return Objects.equals(name, theater.name);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name);
    }
}
