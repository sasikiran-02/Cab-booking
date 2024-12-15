package com.simplilearn.cabbooking.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Bookings")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookingID;  // Field names in camelCase

    @ManyToOne
    @JoinColumn(name = "userID", nullable = false)
    private User user;  // Many-to-one relationship with User

    @ManyToOne
    @JoinColumn(name = "carID")
    private Car car;  // Many-to-one relationship with Car (nullable)

    @Column(name = "pickupLocation")
    private String pickupLocation;  // Field names in camelCase

    @Column(name = "dropoffLocation")
    private String dropoffLocation;  // Field names in camelCase

    @Column(name = "pickupTime")
    private LocalDateTime pickupTime;  // Field names in camelCase

    @Column(name = "dropoffTime")
    private LocalDateTime dropoffTime;  // Field names in camelCase

    @Enumerated(EnumType.STRING)
    @Column(name = "bookingStatus")
    private BookingStatus bookingStatus;  // Field names in camelCase

    @Column(name = "createdAt")
    private LocalDateTime createdAt;  // Field names in camelCase

    public enum BookingStatus {
        PENDING,
        CONFIRMED,
        COMPLETED,
        CANCELLED
    }

    // Default constructor for JPA
    public Booking() {
        // Optionally, set createdAt to current time if it's not provided
        this.createdAt = LocalDateTime.now();
    }

    // Constructor with all fields (BookingID is auto-generated, so it's omitted)
    public Booking(User user, Car car, String pickupLocation, String dropoffLocation,
                   LocalDateTime pickupTime, LocalDateTime dropoffTime, BookingStatus bookingStatus) {
        this.user = user;
        this.car = car;
        this.pickupLocation = pickupLocation;
        this.dropoffLocation = dropoffLocation;
        this.pickupTime = pickupTime;
        this.dropoffTime = dropoffTime;
        this.bookingStatus = bookingStatus;
        this.createdAt = LocalDateTime.now();  // Set createdAt as the current timestamp
    }

    // Getters and setters
    public Long getBookingID() {
        return bookingID;
    }

    public void setBookingID(Long bookingID) {
        this.bookingID = bookingID;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public String getPickupLocation() {
        return pickupLocation;
    }

    public void setPickupLocation(String pickupLocation) {
        this.pickupLocation = pickupLocation;
    }

    public String getDropoffLocation() {
        return dropoffLocation;
    }

    public void setDropoffLocation(String dropoffLocation) {
        this.dropoffLocation = dropoffLocation;
    }

    public LocalDateTime getPickupTime() {
        return pickupTime;
    }

    public void setPickupTime(LocalDateTime pickupTime) {
        this.pickupTime = pickupTime;
    }

    public LocalDateTime getDropoffTime() {
        return dropoffTime;
    }

    public void setDropoffTime(LocalDateTime dropoffTime) {
        this.dropoffTime = dropoffTime;
    }

    public BookingStatus getBookingStatus() {
        return bookingStatus;
    }

    public void setBookingStatus(BookingStatus bookingStatus) {
        this.bookingStatus = bookingStatus;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
