package com.cinema.service;

import com.cinema.exception.CinemaException;
import com.cinema.exception.EntityNotFoundException;
import com.cinema.exception.ValidationException;
import com.cinema.models.Customer;
import com.cinema.repository.CustomerRepository;

import java.util.List;
import java.util.logging.Logger;

/**
 * Service layer for Customer business logic.
 */
public class CustomerService {
    
    private static final Logger logger = Logger.getLogger(CustomerService.class.getName());
    private final CustomerRepository customerRepository;
    
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }
    
    /**
     * Creates a new customer.
     */
    public Customer createCustomer(String firstName, String lastName, String email, String phoneNumber) 
            throws CinemaException {
        
        // Check if email already exists
        if (email != null && !email.isEmpty()) {
            Customer existing = customerRepository.findByEmail(email);
            if (existing != null) {
                throw new ValidationException("Customer with email " + email + " already exists");
            }
        }
        
        Customer customer = new Customer(null, firstName, lastName, email, phoneNumber);
        return customerRepository.save(customer);
    }
    
    /**
     * Retrieves a customer by ID.
     */
    public Customer getCustomerById(Long id) throws EntityNotFoundException {
        return customerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Customer", id));
    }
    
    /**
     * Retrieves all customers.
     */
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }
    
    /**
     * Updates customer information.
     */
    public Customer updateCustomer(Long id, String firstName, String lastName, String email, String phoneNumber) 
            throws CinemaException {
        
        Customer customer = getCustomerById(id);
        
        if (firstName != null) customer.setFirstName(firstName);
        if (lastName != null) customer.setLastName(lastName);
        if (email != null) customer.setEmail(email);
        if (phoneNumber != null) customer.setPhoneNumber(phoneNumber);
        
        return customerRepository.update(customer);
    }
    
    /**
     * Deletes a customer by ID.
     */
    public void deleteCustomer(Long id) throws CinemaException {
        if (!customerRepository.deleteById(id)) {
            throw new EntityNotFoundException("Customer", id);
        }
    }
    
    /**
     * Searches customers by name.
     */
    public List<Customer> searchCustomers(String query) {
        return customerRepository.searchByName(query);
    }
    
    /**
     * Adds loyalty points to a customer.
     */
    public Customer addLoyaltyPoints(Long customerId, int points) throws CinemaException {
        Customer customer = getCustomerById(customerId);
        customer.addLoyaltyPoints(points);
        return customerRepository.update(customer);
    }
    
    /**
     * Gets total number of customers.
     */
    public long getCustomerCount() {
        return customerRepository.count();
    }
}
