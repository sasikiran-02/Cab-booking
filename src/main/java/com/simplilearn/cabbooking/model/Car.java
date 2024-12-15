package com.simplilearn.cabbooking.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Cars")
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long carID;

    @Column(name = "Model", nullable = false)
    private String model;

    @Column(name = "LicensePlate", unique = true, nullable = false)
    private String licensePlate;

    @Column(name = "Capacity", nullable = false)
    private int capacity;

    @Enumerated(EnumType.STRING)
    @Column(name = "AvailabilityStatus", nullable = false)
    private AvailabilityStatus availabilityStatus;

    @Column(name = "CreatedAt", nullable = false)
    private LocalDateTime createdAt;

    public enum AvailabilityStatus {
        UnderMaintance,
        Available,
        Booked;


    }

    // Default constructor for JPA
    public Car() {}

    // Constructor with all fields
    public Car(Long carID, String model, String licensePlate, int capacity, AvailabilityStatus availabilityStatus, LocalDateTime createdAt) {
        this.carID = carID;
        this.model = model;
        this.licensePlate = licensePlate;
        this.capacity = capacity;
        this.availabilityStatus = availabilityStatus;
        this.createdAt = createdAt;
    }

    // Getters and Setters
    public Long getCarID() {
        return carID;
    }

    public void setCarID(Long carID) {
        this.carID = carID;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public AvailabilityStatus getAvailabilityStatus() {
        return availabilityStatus;
    }

    public void setAvailabilityStatus(AvailabilityStatus availabilityStatus) {
        this.availabilityStatus = availabilityStatus;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
