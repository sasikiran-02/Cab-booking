package com.simplilearn.cabbooking.model;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class BookingRequest {

    @NotNull(message = "Username cannot be null")  // Ensures username is not null
    private String username;  // Username of the user making the booking

    @NotNull(message = "Car License Plate cannot be null")  // Ensures carLicensePlate is not null
    private String carLicensePlate;  // License plate of the car being booked

    @NotNull(message = "Pickup Location cannot be null")  // Ensures pickupLocation is not null
    private String pickupLocation;  // Pickup location

    @NotNull(message = "Dropoff Location cannot be null")  // Ensures dropoffLocation is not null
    private String dropoffLocation;  // Dropoff location

    @NotNull(message = "Pickup Time cannot be null")  // Ensures pickupTime is not null
    private LocalDateTime pickupTime;  // Pickup time

    @NotNull(message = "Dropoff Time cannot be null")  // Ensures dropoffTime is not null
    private LocalDateTime dropoffTime;  // Dropoff time


    // Getters and setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCarLicensePlate() {
        return carLicensePlate;
    }

    public void setCarLicensePlate(String carLicensePlate) {
        this.carLicensePlate = carLicensePlate;
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
}
