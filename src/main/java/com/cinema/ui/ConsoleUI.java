package com.cinema.ui;

import com.cinema.models.Customer;
import com.cinema.models.Movie;
import com.cinema.models.Theater;

import java.util.List;
import java.util.Scanner;

/**
 * Console UI helper for displaying menus and formatted output.
 */
public class ConsoleUI {
    
    private static final String SEPARATOR = "=".repeat(80);
    private static final String LINE = "-".repeat(80);
    
    /**
     * Displays the main menu.
     */
    public static void displayMainMenu() {
        System.out.println("\n" + SEPARATOR);
        System.out.println("              CINEMA MANAGEMENT SYSTEM - MAIN MENU");
        System.out.println(SEPARATOR);
        System.out.println("1.  Customer Management");
        System.out.println("2.  Movie Management");
        System.out.println("3.  Theater Management");
        System.out.println("4.  View Statistics");
        System.out.println("0.  Exit");
        System.out.println(SEPARATOR);
        System.out.print("Enter your choice: ");
    }
    
    /**
     * Displays the customer management menu.
     */
    public static void displayCustomerMenu() {
        System.out.println("\n" + LINE);
        System.out.println("CUSTOMER MANAGEMENT");
        System.out.println(LINE);
        System.out.println("1. Add New Customer");
        System.out.println("2. View All Customers");
        System.out.println("3. Search Customer");
        System.out.println("4. Update Customer");
        System.out.println("5. Delete Customer");
        System.out.println("6. Add Loyalty Points");
        System.out.println("0. Back to Main Menu");
        System.out.println(LINE);
        System.out.print("Enter your choice: ");
    }
    
    /**
     * Displays the movie management menu.
     */
    public static void displayMovieMenu() {
        System.out.println("\n" + LINE);
        System.out.println("MOVIE MANAGEMENT");
        System.out.println(LINE);
        System.out.println("1. Add New Movie");
        System.out.println("2. View All Movies");
        System.out.println("3. Search Movie");
        System.out.println("4. Update Movie");
        System.out.println("5. Delete Movie");
        System.out.println("6. Update Movie Rating");
        System.out.println("0. Back to Main Menu");
        System.out.println(LINE);
        System.out.print("Enter your choice: ");
    }
    
    /**
     * Displays the theater management menu.
     */
    public static void displayTheaterMenu() {
        System.out.println("\n" + LINE);
        System.out.println("THEATER MANAGEMENT");
        System.out.println(LINE);
        System.out.println("1. Add New Theater");
        System.out.println("2. View All Theaters");
        System.out.println("3. Search Theater");
        System.out.println("4. Update Theater");
        System.out.println("5. Delete Theater");
        System.out.println("6. Assign Movie to Theater");
        System.out.println("0. Back to Main Menu");
        System.out.println(LINE);
        System.out.print("Enter your choice: ");
    }
    
    /**
     * Displays a list of customers in a formatted table.
     */
    public static void displayCustomers(List<Customer> customers) {
        if (customers.isEmpty()) {
            System.out.println("\nNo customers found.");
            return;
        }
        
        System.out.println("\n" + LINE);
        System.out.printf("%-5s %-20s %-20s %-30s %-15s %-8s%n", 
                "ID", "First Name", "Last Name", "Email", "Phone", "Points");
        System.out.println(LINE);
        
        for (Customer customer : customers) {
            System.out.printf("%-5d %-20s %-20s %-30s %-15s %-8d%n",
                    customer.getId(),
                    truncate(customer.getFirstName(), 20),
                    truncate(customer.getLastName(), 20),
                    truncate(customer.getEmail() != null ? customer.getEmail() : "N/A", 30),
                    truncate(customer.getPhoneNumber() != null ? customer.getPhoneNumber() : "N/A", 15),
                    customer.getLoyaltyPoints());
        }
        System.out.println(LINE);
        System.out.println("Total: " + customers.size() + " customer(s)");
    }
    
    /**
     * Displays a list of movies in a formatted table.
     */
    public static void displayMovies(List<Movie> movies) {
        if (movies.isEmpty()) {
            System.out.println("\nNo movies found.");
            return;
        }
        
        System.out.println("\n" + LINE);
        System.out.printf("%-5s %-30s %-15s %-12s %-20s %-10s %-8s%n", 
                "ID", "Title", "Genre", "Duration", "Director", "Language", "Rating");
        System.out.println(LINE);
        
        for (Movie movie : movies) {
            System.out.printf("%-5d %-30s %-15s %-12s %-20s %-10s %-8.1f%n",
                    movie.getId(),
                    truncate(movie.getTitle(), 30),
                    truncate(movie.getGenre(), 15),
                    movie.getFormattedDuration(),
                    truncate(movie.getDirector() != null ? movie.getDirector() : "N/A", 20),
                    truncate(movie.getLanguage() != null ? movie.getLanguage() : "N/A", 10),
                    movie.getRating());
        }
        System.out.println(LINE);
        System.out.println("Total: " + movies.size() + " movie(s)");
    }
    
    /**
     * Displays a list of theaters in a formatted table.
     */
    public static void displayTheaters(List<Theater> theaters) {
        if (theaters.isEmpty()) {
            System.out.println("\nNo theaters found.");
            return;
        }
        
        System.out.println("\n" + LINE);
        System.out.printf("%-5s %-25s %-10s %-15s %-12s %-10s%n", 
                "ID", "Name", "Capacity", "Screen Type", "Wheelchair", "Movies");
        System.out.println(LINE);
        
        for (Theater theater : theaters) {
            System.out.printf("%-5d %-25s %-10d %-15s %-12s %-10d%n",
                    theater.getId(),
                    truncate(theater.getName(), 25),
                    theater.getCapacity(),
                    truncate(theater.getScreenType(), 15),
                    theater.isHasWheelchairAccess() ? "Yes" : "No",
                    theater.getCurrentMovieIds().size());
        }
        System.out.println(LINE);
        System.out.println("Total: " + theaters.size() + " theater(s)");
    }
    
    /**
     * Displays a success message.
     */
    public static void displaySuccess(String message) {
        System.out.println("\n✓ SUCCESS: " + message);
    }
    
    /**
     * Displays an error message.
     */
    public static void displayError(String message) {
        System.out.println("\n✗ ERROR: " + message);
    }
    
    /**
     * Displays an info message.
     */
    public static void displayInfo(String message) {
        System.out.println("\nℹ INFO: " + message);
    }
    
    /**
     * Prompts user for confirmation.
     */
    public static boolean confirm(Scanner scanner, String message) {
        System.out.print("\n" + message + " (y/n): ");
        String response = scanner.nextLine().trim().toLowerCase();
        return response.equals("y") || response.equals("yes");
    }
    
    /**
     * Waits for user to press Enter.
     */
    public static void waitForEnter(Scanner scanner) {
        System.out.print("\nPress Enter to continue...");
        scanner.nextLine();
    }
    
    /**
     * Truncates a string to the specified length.
     */
    private static String truncate(String str, int length) {
        if (str == null) return "";
        return str.length() > length ? str.substring(0, length - 3) + "..." : str;
    }
    
    /**
     * Clears the console (works on most terminals).
     */
    public static void clearScreen() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (Exception e) {
            // If clearing fails, just print some newlines
            for (int i = 0; i < 50; i++) {
                System.out.println();
            }
        }
    }
}
