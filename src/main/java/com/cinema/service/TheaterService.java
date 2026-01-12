package com.cinema.service;

import com.cinema.exception.CinemaException;
import com.cinema.exception.EntityNotFoundException;
import com.cinema.models.Theater;
import com.cinema.repository.TheaterRepository;

import java.util.List;
import java.util.logging.Logger;

/**
 * Service layer for Theater business logic.
 */
public class TheaterService {
    
    private static final Logger logger = Logger.getLogger(TheaterService.class.getName());
    private final TheaterRepository theaterRepository;
    
    public TheaterService(TheaterRepository theaterRepository) {
        this.theaterRepository = theaterRepository;
    }
    
    /**
     * Creates a new theater.
     */
    public Theater createTheater(String name, int capacity, String screenType) throws CinemaException {
        Theater theater = new Theater(null, name, capacity, screenType);
        return theaterRepository.save(theater);
    }
    
    /**
     * Retrieves a theater by ID.
     */
    public Theater getTheaterById(Long id) throws EntityNotFoundException {
        return theaterRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Theater", id));
    }
    
    /**
     * Retrieves all theaters.
     */
    public List<Theater> getAllTheaters() {
        return theaterRepository.findAll();
    }
    
    /**
     * Updates theater information.
     */
    public Theater updateTheater(Long id, String name, Integer capacity, String screenType, 
                                Boolean hasWheelchairAccess) throws CinemaException {
        
        Theater theater = getTheaterById(id);
        
        if (name != null) theater.setName(name);
        if (capacity != null) theater.setCapacity(capacity);
        if (screenType != null) theater.setScreenType(screenType);
        if (hasWheelchairAccess != null) theater.setHasWheelchairAccess(hasWheelchairAccess);
        
        return theaterRepository.update(theater);
    }
    
    /**
     * Deletes a theater by ID.
     */
    public void deleteTheater(Long id) throws CinemaException {
        if (!theaterRepository.deleteById(id)) {
            throw new EntityNotFoundException("Theater", id);
        }
    }
    
    /**
     * Adds a movie to a theater's schedule.
     */
    public Theater addMovieToTheater(Long theaterId, Long movieId) throws CinemaException {
        Theater theater = getTheaterById(theaterId);
        theater.addMovie(movieId);
        return theaterRepository.update(theater);
    }
    
    /**
     * Removes a movie from a theater's schedule.
     */
    public Theater removeMovieFromTheater(Long theaterId, Long movieId) throws CinemaException {
        Theater theater = getTheaterById(theaterId);
        theater.removeMovie(movieId);
        return theaterRepository.update(theater);
    }
    
    /**
     * Searches theaters by name.
     */
    public List<Theater> searchTheaters(String query) {
        return theaterRepository.searchByName(query);
    }
    
    /**
     * Finds theaters by screen type.
     */
    public List<Theater> getTheatersByScreenType(String screenType) {
        return theaterRepository.findByScreenType(screenType);
    }
    
    /**
     * Gets total number of theaters.
     */
    public long getTheaterCount() {
        return theaterRepository.count();
    }
}
