package com.simplilearn.cabbooking.RestControllers;

import com.simplilearn.cabbooking.Service.CarService;
import com.simplilearn.cabbooking.model.Car;
import com.simplilearn.cabbooking.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
    @RequestMapping("/cars")
    public class CarRestController {

        private final CarService carService;
        @Autowired
        public CarRestController(CarService carService) {
            this.carService = carService;
        }
        @GetMapping
        public List<Car> getAllCars() {
            return carService.getAllCars();
        }

        @PostMapping
        public ResponseEntity<Car> saveUser(@RequestBody Car car) {
            try {
                Car savedCar = this.carService.addCar(car);
                return new ResponseEntity<>(savedCar, HttpStatus.CREATED);
            } catch (IllegalArgumentException e) {
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            }
        }
        @PutMapping("/{carID}")
        public ResponseEntity<Car> updateCar(@PathVariable Long carID ,@RequestBody Car car) {
            Optional<Car> existingCar = carService.getCarById(carID);
            if (existingCar.isPresent()) {
                car.setCarID(carID);
                carService.updateCar(carID, car);
                return new ResponseEntity<>(car,HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }
    @DeleteMapping("/{carID}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long carID) {
        Optional<Car> car = carService.getCarById(carID);
        if (car.isPresent()) {
            carService.deleteCar(carID);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    }
