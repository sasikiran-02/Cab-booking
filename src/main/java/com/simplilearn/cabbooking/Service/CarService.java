package com.simplilearn.cabbooking.Service;

import com.simplilearn.cabbooking.Repository.CarRepository;
import com.simplilearn.cabbooking.model.Car;
import com.simplilearn.cabbooking.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CarService {

    @Autowired
    private CarRepository carRepository;

    // Get all cars
    public List<Car> getAllCars() {
        return carRepository.findAll();
    }

    // Get a car by its ID
    public Optional<Car> getCarById(Long carID) {
        return carRepository.findById(carID);
    }

    // Get cars by availability status
    public List<Car> getCarsByAvailabilityStatus(Car.AvailabilityStatus availabilityStatus) {
        return carRepository.findByAvailabilityStatus(availabilityStatus);
    }

    // Get a car by its license plate
    public Optional<Car> getCarByLicensePlate(String licensePlate) {
        return carRepository.findByLicensePlate(licensePlate);
    }

    // Add a new car
    public Car addCar(Car car) {
        return carRepository.save(car);
    }

    // Update an existing car
    public void updateCar(Long carID, Car carDetails) {
        Optional<Car> existingCar = carRepository.findById(carDetails.getCarID());
        existingCar.ifPresent(u -> {
            u.setModel(carDetails.getModel());
            u.setCapacity(carDetails.getCapacity());
            u.setLicensePlate(carDetails.getLicensePlate());
            u.setAvailabilityStatus(carDetails.getAvailabilityStatus());
            u.setCreatedAt(carDetails.getCreatedAt());
            carRepository.save(u);
        });
    }

    // Delete a car
    public void deleteCar(Long carID) {
        carRepository.deleteById(carID);
    }

    // Get cars by model
    public List<Car> getCarsByModel(String model) {
        return carRepository.findByModel(model);
    }
}
