package com.cinema;

import com.cinema.exception.CinemaException;
import com.cinema.models.Customer;
import com.cinema.models.Movie;
import com.cinema.models.Theater;
import com.cinema.repository.CustomerRepository;
import com.cinema.repository.MovieRepository;
import com.cinema.repository.TheaterRepository;
import com.cinema.service.CustomerService;
import com.cinema.service.MovieService;
import com.cinema.service.TheaterService;
import com.cinema.ui.ConsoleUI;

import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Main application class for the Cinema Management System.
 */
public class CinemaApplication {
    
    private static final Logger logger = Logger.getLogger(CinemaApplication.class.getName());
    
    private final Scanner scanner;
    private final CustomerService customerService;
    private final MovieService movieService;
    private final TheaterService theaterService;
    
    public CinemaApplication() {
        this.scanner = new Scanner(System.in);
        this.customerService = new CustomerService(new CustomerRepository());
        this.movieService = new MovieService(new MovieRepository());
        this.theaterService = new TheaterService(new TheaterRepository());
    }
    
    /**
     * Main entry point of the application.
     */
    public static void main(String[] args) {
        CinemaApplication app = new CinemaApplication();
        app.run();
    }
    
    /**
     * Runs the main application loop.
     */
    public void run() {
        displayWelcome();
        
        boolean running = true;
        while (running) {
            try {
                ConsoleUI.displayMainMenu();
                int choice = readInt();
                
                switch (choice) {
                    case 1:
                        handleCustomerManagement();
                        break;
                    case 2:
                        handleMovieManagement();
                        break;
                    case 3:
                        handleTheaterManagement();
                        break;
                    case 4:
                        displayStatistics();
                        break;
                    case 0:
                        running = false;
                        displayGoodbye();
                        break;
                    default:
                        ConsoleUI.displayError("Invalid choice. Please try again.");
                }
            } catch (Exception e) {
                logger.log(Level.SEVERE, "Unexpected error", e);
                ConsoleUI.displayError("An unexpected error occurred: " + e.getMessage());
            }
        }
        
        scanner.close();
    }
    
    /**
     * Handles customer management operations.
     */
    private void handleCustomerManagement() {
        boolean back = false;
        while (!back) {
            try {
                ConsoleUI.displayCustomerMenu();
                int choice = readInt();
                
                switch (choice) {
                    case 1:
                        addCustomer();
                        break;
                    case 2:
                        viewAllCustomers();
                        break;
                    case 3:
                        searchCustomer();
                        break;
                    case 4:
                        updateCustomer();
                        break;
                    case 5:
                        deleteCustomer();
                        break;
                    case 6:
                        addLoyaltyPoints();
                        break;
                    case 0:
                        back = true;
                        break;
                    default:
                        ConsoleUI.displayError("Invalid choice. Please try again.");
                }
            } catch (Exception e) {
                logger.log(Level.WARNING, "Error in customer management", e);
                ConsoleUI.displayError(e.getMessage());
            }
        }
    }
    
    /**
     * Handles movie management operations.
     */
    private void handleMovieManagement() {
        boolean back = false;
        while (!back) {
            try {
                ConsoleUI.displayMovieMenu();
                int choice = readInt();
                
                switch (choice) {
                    case 1:
                        addMovie();
                        break;
                    case 2:
                        viewAllMovies();
                        break;
                    case 3:
                        searchMovie();
                        break;
                    case 4:
                        updateMovie();
                        break;
                    case 5:
                        deleteMovie();
                        break;
                    case 6:
                        updateMovieRating();
                        break;
                    case 0:
                        back = true;
                        break;
                    default:
                        ConsoleUI.displayError("Invalid choice. Please try again.");
                }
            } catch (Exception e) {
                logger.log(Level.WARNING, "Error in movie management", e);
                ConsoleUI.displayError(e.getMessage());
            }
        }
    }
    
    /**
     * Handles theater management operations.
     */
    private void handleTheaterManagement() {
        boolean back = false;
        while (!back) {
            try {
                ConsoleUI.displayTheaterMenu();
                int choice = readInt();
                
                switch (choice) {
                    case 1:
                        addTheater();
                        break;
                    case 2:
                        viewAllTheaters();
                        break;
                    case 3:
                        searchTheater();
                        break;
                    case 4:
                        updateTheater();
                        break;
                    case 5:
                        deleteTheater();
                        break;
                    case 6:
                        assignMovieToTheater();
                        break;
                    case 0:
                        back = true;
                        break;
                    default:
                        ConsoleUI.displayError("Invalid choice. Please try again.");
                }
            } catch (Exception e) {
                logger.log(Level.WARNING, "Error in theater management", e);
                ConsoleUI.displayError(e.getMessage());
            }
        }
    }
    
    // Customer Operations
    
    private void addCustomer() throws CinemaException {
        System.out.println("\n--- Add New Customer ---");
        
        System.out.print("First Name: ");
        String firstName = scanner.nextLine().trim();
        
        System.out.print("Last Name: ");
        String lastName = scanner.nextLine().trim();
        
        System.out.print("Email (optional): ");
        String email = scanner.nextLine().trim();
        if (email.isEmpty()) email = null;
        
        System.out.print("Phone Number (optional): ");
        String phone = scanner.nextLine().trim();
        if (phone.isEmpty()) phone = null;
        
        Customer customer = customerService.createCustomer(firstName, lastName, email, phone);
        ConsoleUI.displaySuccess("Customer added successfully! ID: " + customer.getId());
        ConsoleUI.waitForEnter(scanner);
    }
    
    private void viewAllCustomers() {
        System.out.println("\n--- All Customers ---");
        List<Customer> customers = customerService.getAllCustomers();
        ConsoleUI.displayCustomers(customers);
        ConsoleUI.waitForEnter(scanner);
    }
    
    private void searchCustomer() {
        System.out.println("\n--- Search Customer ---");
        System.out.print("Enter name to search: ");
        String query = scanner.nextLine().trim();
        
        List<Customer> customers = customerService.searchCustomers(query);
        ConsoleUI.displayCustomers(customers);
        ConsoleUI.waitForEnter(scanner);
    }
    
    private void updateCustomer() throws CinemaException {
        System.out.println("\n--- Update Customer ---");
        System.out.print("Enter Customer ID: ");
        long id = readLong();
        
        Customer customer = customerService.getCustomerById(id);
        System.out.println("Current: " + customer);
        
        System.out.print("New First Name (press Enter to skip): ");
        String firstName = scanner.nextLine().trim();
        
        System.out.print("New Last Name (press Enter to skip): ");
        String lastName = scanner.nextLine().trim();
        
        System.out.print("New Email (press Enter to skip): ");
        String email = scanner.nextLine().trim();
        
        System.out.print("New Phone (press Enter to skip): ");
        String phone = scanner.nextLine().trim();
        
        customerService.updateCustomer(
                id,
                firstName.isEmpty() ? null : firstName,
                lastName.isEmpty() ? null : lastName,
                email.isEmpty() ? null : email,
                phone.isEmpty() ? null : phone
        );
        
        ConsoleUI.displaySuccess("Customer updated successfully!");
        ConsoleUI.waitForEnter(scanner);
    }
    
    private void deleteCustomer() throws CinemaException {
        System.out.println("\n--- Delete Customer ---");
        System.out.print("Enter Customer ID: ");
        long id = readLong();
        
        Customer customer = customerService.getCustomerById(id);
        System.out.println("Customer: " + customer);
        
        if (ConsoleUI.confirm(scanner, "Are you sure you want to delete this customer?")) {
            customerService.deleteCustomer(id);
            ConsoleUI.displaySuccess("Customer deleted successfully!");
        } else {
            ConsoleUI.displayInfo("Deletion cancelled.");
        }
        ConsoleUI.waitForEnter(scanner);
    }
    
    private void addLoyaltyPoints() throws CinemaException {
        System.out.println("\n--- Add Loyalty Points ---");
        System.out.print("Enter Customer ID: ");
        long id = readLong();
        
        System.out.print("Enter points to add: ");
        int points = readInt();
        
        Customer customer = customerService.addLoyaltyPoints(id, points);
        ConsoleUI.displaySuccess("Added " + points + " points. Total: " + customer.getLoyaltyPoints());
        ConsoleUI.waitForEnter(scanner);
    }
    
    // Movie Operations
    
    private void addMovie() throws CinemaException {
        System.out.println("\n--- Add New Movie ---");
        
        System.out.print("Title: ");
        String title = scanner.nextLine().trim();
        
        System.out.print("Genre: ");
        String genre = scanner.nextLine().trim();
        
        System.out.print("Duration (minutes): ");
        int duration = readInt();
        
        System.out.print("Director: ");
        String director = scanner.nextLine().trim();
        
        System.out.print("Language: ");
        String language = scanner.nextLine().trim();
        
        Movie movie = movieService.createMovie(title, genre, duration, director, language);
        ConsoleUI.displaySuccess("Movie added successfully! ID: " + movie.getId());
        ConsoleUI.waitForEnter(scanner);
    }
    
    private void viewAllMovies() {
        System.out.println("\n--- All Movies ---");
        List<Movie> movies = movieService.getAllMovies();
        ConsoleUI.displayMovies(movies);
        ConsoleUI.waitForEnter(scanner);
    }
    
    private void searchMovie() {
        System.out.println("\n--- Search Movie ---");
        System.out.print("Enter title to search: ");
        String query = scanner.nextLine().trim();
        
        List<Movie> movies = movieService.searchMovies(query);
        ConsoleUI.displayMovies(movies);
        ConsoleUI.waitForEnter(scanner);
    }
    
    private void updateMovie() throws CinemaException {
        System.out.println("\n--- Update Movie ---");
        System.out.print("Enter Movie ID: ");
        long id = readLong();
        
        Movie movie = movieService.getMovieById(id);
        System.out.println("Current: " + movie);
        
        System.out.print("New Title (press Enter to skip): ");
        String title = scanner.nextLine().trim();
        
        System.out.print("New Genre (press Enter to skip): ");
        String genre = scanner.nextLine().trim();
        
        System.out.print("New Duration in minutes (0 to skip): ");
        int duration = readInt();
        
        System.out.print("New Director (press Enter to skip): ");
        String director = scanner.nextLine().trim();
        
        System.out.print("New Language (press Enter to skip): ");
        String language = scanner.nextLine().trim();
        
        movieService.updateMovie(
                id,
                title.isEmpty() ? null : title,
                genre.isEmpty() ? null : genre,
                duration == 0 ? null : duration,
                director.isEmpty() ? null : director,
                language.isEmpty() ? null : language,
                null,
                null
        );
        
        ConsoleUI.displaySuccess("Movie updated successfully!");
        ConsoleUI.waitForEnter(scanner);
    }
    
    private void deleteMovie() throws CinemaException {
        System.out.println("\n--- Delete Movie ---");
        System.out.print("Enter Movie ID: ");
        long id = readLong();
        
        Movie movie = movieService.getMovieById(id);
        System.out.println("Movie: " + movie);
        
        if (ConsoleUI.confirm(scanner, "Are you sure you want to delete this movie?")) {
            movieService.deleteMovie(id);
            ConsoleUI.displaySuccess("Movie deleted successfully!");
        } else {
            ConsoleUI.displayInfo("Deletion cancelled.");
        }
        ConsoleUI.waitForEnter(scanner);
    }
    
    private void updateMovieRating() throws CinemaException {
        System.out.println("\n--- Update Movie Rating ---");
        System.out.print("Enter Movie ID: ");
        long id = readLong();
        
        System.out.print("Enter new rating (0.0 - 10.0): ");
        double rating = readDouble();
        
        Movie movie = movieService.updateRating(id, rating);
        ConsoleUI.displaySuccess("Rating updated to " + rating + " for: " + movie.getTitle());
        ConsoleUI.waitForEnter(scanner);
    }
    
    // Theater Operations
    
    private void addTheater() throws CinemaException {
        System.out.println("\n--- Add New Theater ---");
        
        System.out.print("Name: ");
        String name = scanner.nextLine().trim();
        
        System.out.print("Capacity: ");
        int capacity = readInt();
        
        System.out.print("Screen Type (Standard/IMAX/3D/4DX): ");
        String screenType = scanner.nextLine().trim();
        
        Theater theater = theaterService.createTheater(name, capacity, screenType);
        ConsoleUI.displaySuccess("Theater added successfully! ID: " + theater.getId());
        ConsoleUI.waitForEnter(scanner);
    }
    
    private void viewAllTheaters() {
        System.out.println("\n--- All Theaters ---");
        List<Theater> theaters = theaterService.getAllTheaters();
        ConsoleUI.displayTheaters(theaters);
        ConsoleUI.waitForEnter(scanner);
    }
    
    private void searchTheater() {
        System.out.println("\n--- Search Theater ---");
        System.out.print("Enter name to search: ");
        String query = scanner.nextLine().trim();
        
        List<Theater> theaters = theaterService.searchTheaters(query);
        ConsoleUI.displayTheaters(theaters);
        ConsoleUI.waitForEnter(scanner);
    }
    
    private void updateTheater() throws CinemaException {
        System.out.println("\n--- Update Theater ---");
        System.out.print("Enter Theater ID: ");
        long id = readLong();
        
        Theater theater = theaterService.getTheaterById(id);
        System.out.println("Current: " + theater);
        
        System.out.print("New Name (press Enter to skip): ");
        String name = scanner.nextLine().trim();
        
        System.out.print("New Capacity (0 to skip): ");
        int capacity = readInt();
        
        System.out.print("New Screen Type (press Enter to skip): ");
        String screenType = scanner.nextLine().trim();
        
        theaterService.updateTheater(
                id,
                name.isEmpty() ? null : name,
                capacity == 0 ? null : capacity,
                screenType.isEmpty() ? null : screenType,
                null
        );
        
        ConsoleUI.displaySuccess("Theater updated successfully!");
        ConsoleUI.waitForEnter(scanner);
    }
    
    private void deleteTheater() throws CinemaException {
        System.out.println("\n--- Delete Theater ---");
        System.out.print("Enter Theater ID: ");
        long id = readLong();
        
        Theater theater = theaterService.getTheaterById(id);
        System.out.println("Theater: " + theater);
        
        if (ConsoleUI.confirm(scanner, "Are you sure you want to delete this theater?")) {
            theaterService.deleteTheater(id);
            ConsoleUI.displaySuccess("Theater deleted successfully!");
        } else {
            ConsoleUI.displayInfo("Deletion cancelled.");
        }
        ConsoleUI.waitForEnter(scanner);
    }
    
    private void assignMovieToTheater() throws CinemaException {
        System.out.println("\n--- Assign Movie to Theater ---");
        System.out.print("Enter Theater ID: ");
        long theaterId = readLong();
        
        System.out.print("Enter Movie ID: ");
        long movieId = readLong();
        
        theaterService.addMovieToTheater(theaterId, movieId);
        ConsoleUI.displaySuccess("Movie assigned to theater successfully!");
        ConsoleUI.waitForEnter(scanner);
    }
    
    // Statistics
    
    private void displayStatistics() {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("                          SYSTEM STATISTICS");
        System.out.println("=".repeat(80));
        System.out.println("Total Customers: " + customerService.getCustomerCount());
        System.out.println("Total Movies:    " + movieService.getMovieCount());
        System.out.println("Total Theaters:  " + theaterService.getTheaterCount());
        System.out.println("=".repeat(80));
        ConsoleUI.waitForEnter(scanner);
    }
    
    // Utility Methods
    
    private int readInt() {
        while (true) {
            try {
                int value = Integer.parseInt(scanner.nextLine().trim());
                return value;
            } catch (NumberFormatException e) {
                System.out.print("Invalid input. Please enter a number: ");
            }
        }
    }
    
    private long readLong() {
        while (true) {
            try {
                long value = Long.parseLong(scanner.nextLine().trim());
                return value;
            } catch (NumberFormatException e) {
                System.out.print("Invalid input. Please enter a number: ");
            }
        }
    }
    
    private double readDouble() {
        while (true) {
            try {
                double value = Double.parseDouble(scanner.nextLine().trim());
                return value;
            } catch (NumberFormatException e) {
                System.out.print("Invalid input. Please enter a number: ");
            }
        }
    }
    
    private void displayWelcome() {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("              WELCOME TO CINEMA MANAGEMENT SYSTEM v2.0");
        System.out.println("=".repeat(80));
        System.out.println("A professional system for managing customers, movies, and theaters.");
        System.out.println("=".repeat(80));
    }
    
    private void displayGoodbye() {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("              Thank you for using Cinema Management System!");
        System.out.println("=".repeat(80));
    }
}
