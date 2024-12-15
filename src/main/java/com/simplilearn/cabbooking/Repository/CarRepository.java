package com.simplilearn.cabbooking.Repository;
import com.simplilearn.cabbooking.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface CarRepository extends JpaRepository<Car, Long> {
    // Find cars by availability status
    List<Car> findByAvailabilityStatus(Car.AvailabilityStatus availabilityStatus);

    // Find a car by its license plate
    Optional<Car> findByLicensePlate(String licensePlate);

    // Find cars by model
    List<Car> findByModel(String model);
}
