package com.cinema.models;

import com.cinema.exception.ValidationException;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

/**
 * Represents a customer in the cinema system.
 */
public class Customer extends BaseEntity {
    
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private int loyaltyPoints;
    
    public Customer() {
        super();
        this.loyaltyPoints = 0;
    }
    
    @JsonCreator
    public Customer(
            @JsonProperty("id") Long id,
            @JsonProperty("firstName") String firstName,
            @JsonProperty("lastName") String lastName,
            @JsonProperty("email") String email,
            @JsonProperty("phoneNumber") String phoneNumber) {
        super(id);
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.loyaltyPoints = 0;
    }
    
    @Override
    public void validate() throws ValidationException {
        if (firstName == null || firstName.trim().isEmpty()) {
            throw new ValidationException("Customer first name cannot be empty");
        }
        if (lastName == null || lastName.trim().isEmpty()) {
            throw new ValidationException("Customer last name cannot be empty");
        }
        if (email != null && !email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            throw new ValidationException("Invalid email format");
        }
        if (phoneNumber != null && !phoneNumber.matches("^[0-9+\\-\\s()]+$")) {
            throw new ValidationException("Invalid phone number format");
        }
    }
    
    /**
     * Gets the full name of the customer.
     */
    public String getFullName() {
        return firstName + " " + lastName;
    }
    
    /**
     * Adds loyalty points to the customer.
     */
    public void addLoyaltyPoints(int points) {
        if (points > 0) {
            this.loyaltyPoints += points;
            touch();
        }
    }
    
    // Getters and Setters
    public String getFirstName() {
        return firstName;
    }
    
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    public String getLastName() {
        return lastName;
    }
    
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getPhoneNumber() {
        return phoneNumber;
    }
    
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    
    public int getLoyaltyPoints() {
        return loyaltyPoints;
    }
    
    public void setLoyaltyPoints(int loyaltyPoints) {
        this.loyaltyPoints = loyaltyPoints;
    }
    
    @Override
    public String toString() {
        return String.format("Customer[id=%d, name=%s, email=%s, phone=%s, points=%d]",
                getId(), getFullName(), email, phoneNumber, loyaltyPoints);
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Customer customer = (Customer) o;
        return Objects.equals(email, customer.email);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), email);
    }
}
