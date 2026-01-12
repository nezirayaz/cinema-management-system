package com.cinema.repository;

import com.cinema.models.Customer;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Repository for Customer entities with additional search capabilities.
 */
public class CustomerRepository extends JsonRepository<Customer> {
    
    private static final String DEFAULT_FILE_PATH = "src/main/resources/customers.json";
    
    public CustomerRepository() {
        super(DEFAULT_FILE_PATH, Customer.class);
    }
    
    public CustomerRepository(String filePath) {
        super(filePath, Customer.class);
    }
    
    /**
     * Finds customers by last name (case-insensitive).
     */
    public List<Customer> findByLastName(String lastName) {
        return entities.stream()
                .filter(c -> c.getLastName().equalsIgnoreCase(lastName))
                .collect(Collectors.toList());
    }
    
    /**
     * Finds a customer by email.
     */
    public Customer findByEmail(String email) {
        return entities.stream()
                .filter(c -> email.equalsIgnoreCase(c.getEmail()))
                .findFirst()
                .orElse(null);
    }
    
    /**
     * Searches customers by name (first or last name contains the query).
     */
    public List<Customer> searchByName(String query) {
        String lowerQuery = query.toLowerCase();
        return entities.stream()
                .filter(c -> c.getFirstName().toLowerCase().contains(lowerQuery) ||
                           c.getLastName().toLowerCase().contains(lowerQuery))
                .collect(Collectors.toList());
    }
    
    /**
     * Finds customers with loyalty points greater than or equal to the specified amount.
     */
    public List<Customer> findByMinLoyaltyPoints(int minPoints) {
        return entities.stream()
                .filter(c -> c.getLoyaltyPoints() >= minPoints)
                .collect(Collectors.toList());
    }
}
