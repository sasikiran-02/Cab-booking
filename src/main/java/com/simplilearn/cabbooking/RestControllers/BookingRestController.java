package com.simplilearn.cabbooking.RestControllers;

import com.simplilearn.cabbooking.Service.BookingService;
import com.simplilearn.cabbooking.Service.UserService;
import com.simplilearn.cabbooking.Service.CarService;
import com.simplilearn.cabbooking.model.Booking;
import com.simplilearn.cabbooking.model.BookingRequest;
import com.simplilearn.cabbooking.model.Car;
import com.simplilearn.cabbooking.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/bookings")
public class BookingRestController {
    private final BookingService bookingService;
    private final UserService userService;
    private final CarService carService;

    @Autowired
    public BookingRestController(BookingService bookingService, UserService userService, CarService carService) {
        this.bookingService = bookingService;
        this.userService = userService;
        this.carService = carService;
    }
    @GetMapping("/user/{userId}")
    public List<Booking> getBookingsByUser(@PathVariable Long userId) {
        // Assuming User object is fetched from the DB
        Optional<User> user = this.userService.findUserById(userId);  // This should ideally be fetched from the DB based on the ID
        if (user.isPresent()) {
            List<Booking>Bookings= bookingService.getBookingsByUser(user);
            if(Bookings.isEmpty()) {
                return Collections.emptyList();
            }
            return Bookings;

        }

            throw new RuntimeException("User not found with ID: " + userId);
    }
    @GetMapping
    public List<Booking> getAllBookings() {
        return bookingService.getAllBookings();
    }
    @PostMapping
    public ResponseEntity<Booking> createBooking(@RequestBody BookingRequest bookingRequest) {

        Optional<User> user = this.userService.findUserByUsername(bookingRequest.getUsername());
        Optional<Car> car = this.carService.getCarByLicensePlate(bookingRequest.getCarLicensePlate());


        if (user.isEmpty() || car.isEmpty()) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);  // Return a bad request if user or car is not found
        }

        User foundUser = user.get();
        Car foundCar = car.get();

        if(foundCar.getAvailabilityStatus().equals(Car.AvailabilityStatus.Booked)){
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }


        // Create a new Booking object
        Booking booking = new Booking();
        booking.setUser(foundUser);
        booking.setCar(foundCar);
        booking.setPickupLocation(bookingRequest.getPickupLocation());
        booking.setDropoffLocation(bookingRequest.getDropoffLocation());
        booking.setPickupTime(bookingRequest.getPickupTime());
        booking.setDropoffTime(bookingRequest.getDropoffTime());
        booking.setBookingStatus(Booking.BookingStatus.CONFIRMED);

        foundCar.setAvailabilityStatus(Car.AvailabilityStatus.Booked);

        carService.updateCar(foundCar.getCarID(), foundCar);

        try {
            bookingService.addBooking(booking);
            return new ResponseEntity<>(booking, HttpStatus.CREATED);
        }
        catch (IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{bookingID}")
    public ResponseEntity<Booking> deleteBooking(@PathVariable Long bookingID ,@RequestBody Booking booking) {
        Optional<Booking> booked=bookingService.findBookingById(bookingID);
        if(booked.isPresent()) {
            bookingService.deleteBooking(bookingID);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
