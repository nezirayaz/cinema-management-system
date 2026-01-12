package com.cinema.service;

import com.cinema.exception.CinemaException;
import com.cinema.exception.EntityNotFoundException;
import com.cinema.exception.ValidationException;
import com.cinema.models.Customer;
import com.cinema.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;

import java.io.File;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for CustomerService.
 */
class CustomerServiceTest {
    
    private CustomerService customerService;
    private CustomerRepository customerRepository;
    private static final String TEST_FILE = "src/test/resources/test-customers.json";
    
    @BeforeEach
    void setUp() {
        customerRepository = new CustomerRepository(TEST_FILE);
        customerService = new CustomerService(customerRepository);
    }
    
    @AfterEach
    void tearDown() throws CinemaException {
        customerRepository.deleteAll();
        new File(TEST_FILE).delete();
    }
    
    @Test
    void testCreateCustomer() throws CinemaException {
        Customer customer = customerService.createCustomer("John", "Doe", "john@example.com", "123456789");
        
        assertNotNull(customer);
        assertNotNull(customer.getId());
        assertEquals("John", customer.getFirstName());
        assertEquals("Doe", customer.getLastName());
        assertEquals("john@example.com", customer.getEmail());
    }
    
    @Test
    void testCreateCustomerDuplicateEmail() throws CinemaException {
        customerService.createCustomer("John", "Doe", "john@example.com", "123456789");
        
        assertThrows(ValidationException.class, () -> {
            customerService.createCustomer("Jane", "Smith", "john@example.com", "987654321");
        });
    }
    
    @Test
    void testGetCustomerById() throws CinemaException {
        Customer created = customerService.createCustomer("John", "Doe", "john@example.com", "123456789");
        Customer retrieved = customerService.getCustomerById(created.getId());
        
        assertEquals(created.getId(), retrieved.getId());
        assertEquals("John", retrieved.getFirstName());
    }
    
    @Test
    void testGetCustomerByIdNotFound() {
        assertThrows(EntityNotFoundException.class, () -> {
            customerService.getCustomerById(999L);
        });
    }
    
    @Test
    void testGetAllCustomers() throws CinemaException {
        customerService.createCustomer("John", "Doe", "john@example.com", "123456789");
        customerService.createCustomer("Jane", "Smith", "jane@example.com", "987654321");
        
        List<Customer> customers = customerService.getAllCustomers();
        assertEquals(2, customers.size());
    }
    
    @Test
    void testUpdateCustomer() throws CinemaException {
        Customer customer = customerService.createCustomer("John", "Doe", "john@example.com", "123456789");
        
        Customer updated = customerService.updateCustomer(customer.getId(), "Johnny", null, null, null);
        
        assertEquals("Johnny", updated.getFirstName());
        assertEquals("Doe", updated.getLastName());
    }
    
    @Test
    void testDeleteCustomer() throws CinemaException {
        Customer customer = customerService.createCustomer("John", "Doe", "john@example.com", "123456789");
        
        customerService.deleteCustomer(customer.getId());
        
        assertThrows(EntityNotFoundException.class, () -> {
            customerService.getCustomerById(customer.getId());
        });
    }
    
    @Test
    void testSearchCustomers() throws CinemaException {
        customerService.createCustomer("John", "Doe", "john@example.com", "123456789");
        customerService.createCustomer("Jane", "Doe", "jane@example.com", "987654321");
        customerService.createCustomer("Bob", "Smith", "bob@example.com", "555555555");
        
        List<Customer> results = customerService.searchCustomers("Doe");
        assertEquals(2, results.size());
    }
    
    @Test
    void testAddLoyaltyPoints() throws CinemaException {
        Customer customer = customerService.createCustomer("John", "Doe", "john@example.com", "123456789");
        
        Customer updated = customerService.addLoyaltyPoints(customer.getId(), 100);
        assertEquals(100, updated.getLoyaltyPoints());
        
        updated = customerService.addLoyaltyPoints(customer.getId(), 50);
        assertEquals(150, updated.getLoyaltyPoints());
    }
    
    @Test
    void testGetCustomerCount() throws CinemaException {
        assertEquals(0, customerService.getCustomerCount());
        
        customerService.createCustomer("John", "Doe", "john@example.com", "123456789");
        assertEquals(1, customerService.getCustomerCount());
        
        customerService.createCustomer("Jane", "Smith", "jane@example.com", "987654321");
        assertEquals(2, customerService.getCustomerCount());
    }
}
