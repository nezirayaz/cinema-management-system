package com.cinema.models;

import com.cinema.exception.ValidationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for Customer model.
 */
class CustomerTest {
    
    private Customer customer;
    
    @BeforeEach
    void setUp() {
        customer = new Customer(1L, "John", "Doe", "john.doe@example.com", "+1234567890");
    }
    
    @Test
    void testCustomerCreation() {
        assertNotNull(customer);
        assertEquals(1L, customer.getId());
        assertEquals("John", customer.getFirstName());
        assertEquals("Doe", customer.getLastName());
        assertEquals("john.doe@example.com", customer.getEmail());
        assertEquals("+1234567890", customer.getPhoneNumber());
        assertEquals(0, customer.getLoyaltyPoints());
    }
    
    @Test
    void testGetFullName() {
        assertEquals("John Doe", customer.getFullName());
    }
    
    @Test
    void testAddLoyaltyPoints() {
        customer.addLoyaltyPoints(100);
        assertEquals(100, customer.getLoyaltyPoints());
        
        customer.addLoyaltyPoints(50);
        assertEquals(150, customer.getLoyaltyPoints());
    }
    
    @Test
    void testAddNegativeLoyaltyPoints() {
        customer.addLoyaltyPoints(-50);
        assertEquals(0, customer.getLoyaltyPoints());
    }
    
    @Test
    void testValidateSuccess() {
        assertDoesNotThrow(() -> customer.validate());
    }
    
    @Test
    void testValidateEmptyFirstName() {
        customer.setFirstName("");
        assertThrows(ValidationException.class, () -> customer.validate());
    }
    
    @Test
    void testValidateNullFirstName() {
        customer.setFirstName(null);
        assertThrows(ValidationException.class, () -> customer.validate());
    }
    
    @Test
    void testValidateEmptyLastName() {
        customer.setLastName("");
        assertThrows(ValidationException.class, () -> customer.validate());
    }
    
    @Test
    void testValidateInvalidEmail() {
        customer.setEmail("invalid-email");
        assertThrows(ValidationException.class, () -> customer.validate());
    }
    
    @Test
    void testValidateInvalidPhoneNumber() {
        customer.setPhoneNumber("abc-def-ghij");
        assertThrows(ValidationException.class, () -> customer.validate());
    }
    
    @Test
    void testToString() {
        String str = customer.toString();
        assertTrue(str.contains("John Doe"));
        assertTrue(str.contains("john.doe@example.com"));
    }
    
    @Test
    void testEquality() {
        Customer customer2 = new Customer(1L, "John", "Doe", "john.doe@example.com", "+1234567890");
        assertEquals(customer, customer2);
    }
}
