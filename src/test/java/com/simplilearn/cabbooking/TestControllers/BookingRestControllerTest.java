package com.simplilearn.cabbooking.TestControllers;

import com.simplilearn.cabbooking.RestControllers.BookingRestController;
import com.simplilearn.cabbooking.Service.BookingService;
import com.simplilearn.cabbooking.Service.UserService;
import com.simplilearn.cabbooking.Service.CarService;
import com.simplilearn.cabbooking.model.Booking;
import com.simplilearn.cabbooking.model.BookingRequest;
import com.simplilearn.cabbooking.model.Car;
import com.simplilearn.cabbooking.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(BookingRestController.class)
public class BookingRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private BookingService bookingService;

    @Mock
    private UserService userService;

    @Mock
    private CarService carService;

    @InjectMocks
    private BookingRestController bookingRestController;

    private User user;
    private Car car;
    private BookingRequest bookingRequest;
    private Booking booking;

    @BeforeEach
    public void setUp() {
        // Set up test data
        user = new User(1L, "John Doe", "johndoe@example.com", "1234567890", "password", "johndoe", LocalDateTime.now());
        car = new Car(1L, "Toyota Prius", "ABC123", 4, Car.AvailabilityStatus.Available, LocalDateTime.now());

        // Initialize BookingRequest without constructor, using setters
        bookingRequest = new BookingRequest();
        bookingRequest.setUsername("johndoe");
        bookingRequest.setCarLicensePlate("ABC123");
        bookingRequest.setPickupLocation("123 Main St");
        bookingRequest.setDropoffLocation("456 Elm St");
        bookingRequest.setPickupTime(LocalDateTime.now());
        bookingRequest.setDropoffTime(LocalDateTime.now().plusHours(1));

        booking = new Booking(user, car, "123 Main St", "456 Elm St", LocalDateTime.now(), LocalDateTime.now().plusHours(1), Booking.BookingStatus.CONFIRMED);
    }

    @Test
    public void testCreateBooking_Success() throws Exception {
        // Mock the behavior of services
        when(userService.findUserByUsername(bookingRequest.getUsername())).thenReturn(Optional.of(user));
        when(carService.getCarByLicensePlate(bookingRequest.getCarLicensePlate())).thenReturn(Optional.of(car));
        when(bookingService.addBooking(any(Booking.class))).thenReturn(booking);

        mockMvc.perform(post("/bookings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"johndoe\",\"carLicensePlate\":\"ABC123\",\"pickupLocation\":\"123 Main St\",\"dropoffLocation\":\"456 Elm St\",\"pickupTime\":\"2024-12-15T10:00:00\",\"dropoffTime\":\"2024-12-15T11:00:00\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.userId").value(1L))
                .andExpect(jsonPath("$.car.licensePlate").value("ABC123"));
    }

    @Test
    public void testCreateBooking_UserNotFound() throws Exception {
        // Mock the behavior of services
        when(userService.findUserByUsername(bookingRequest.getUsername())).thenReturn(Optional.empty());

        mockMvc.perform(post("/bookings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"johndoe\",\"carLicensePlate\":\"ABC123\",\"pickupLocation\":\"123 Main St\",\"dropoffLocation\":\"456 Elm St\",\"pickupTime\":\"2024-12-15T10:00:00\",\"dropoffTime\":\"2024-12-15T11:00:00\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testCreateBooking_CarNotFound() throws Exception {
        // Mock the behavior of services
        when(userService.findUserByUsername(bookingRequest.getUsername())).thenReturn(Optional.of(user));
        when(carService.getCarByLicensePlate(bookingRequest.getCarLicensePlate())).thenReturn(Optional.empty());

        mockMvc.perform(post("/bookings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"johndoe\",\"carLicensePlate\":\"ABC123\",\"pickupLocation\":\"123 Main St\",\"dropoffLocation\":\"456 Elm St\",\"pickupTime\":\"2024-12-15T10:00:00\",\"dropoffTime\":\"2024-12-15T11:00:00\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testCreateBooking_CarAlreadyBooked() throws Exception {
        // Mock the behavior of services
        when(userService.findUserByUsername(bookingRequest.getUsername())).thenReturn(Optional.of(user));
        when(carService.getCarByLicensePlate(bookingRequest.getCarLicensePlate())).thenReturn(Optional.of(car));
        car.setAvailabilityStatus(Car.AvailabilityStatus.Booked);

        mockMvc.perform(post("/bookings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"johndoe\",\"carLicensePlate\":\"ABC123\",\"pickupLocation\":\"123 Main St\",\"dropoffLocation\":\"456 Elm St\",\"pickupTime\":\"2024-12-15T10:00:00\",\"dropoffTime\":\"2024-12-15T11:00:00\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testGetBookingsByUser() throws Exception {
        // Mock the behavior of services
        when(userService.findUserById(1L)).thenReturn(Optional.of(user));  // Wrap user in Optional
        when(bookingService.getBookingsByUser(Optional.ofNullable(user))).thenReturn(List.of(booking));  // Mock method to return booking

        mockMvc.perform(get("/bookings/user/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].userId").value(1L))
                .andExpect(jsonPath("$[0].car.licensePlate").value("ABC123"));
    }

    @Test
    public void testDeleteBooking_Success() throws Exception {
        // Mock the behavior of services
        when(bookingService.findBookingById(1L)).thenReturn(Optional.of(booking));

        mockMvc.perform(delete("/bookings/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testDeleteBooking_NotFound() throws Exception {
        // Mock the behavior of services
        when(bookingService.findBookingById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(delete("/bookings/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

}
