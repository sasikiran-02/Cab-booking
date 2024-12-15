package com.simplilearn.cabbooking.TestControllers;

import com.simplilearn.cabbooking.RestControllers.CarRestController;
import com.simplilearn.cabbooking.Service.CarService;
import com.simplilearn.cabbooking.model.Car;
import com.simplilearn.cabbooking.model.Car.AvailabilityStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class CarRestControllerTest {

    @Mock
    private CarService carService;

    @InjectMocks
    private CarRestController carRestController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(carRestController).build();
    }

    @Test
    void testGetAllCars() throws Exception {
        // Test logic for getting all cars

        mockMvc.perform(get("/cars"))
                .andExpect(status().isOk());
    }

    @Test
    void testSaveCar() throws Exception {
        // Test logic for saving a car
        Car car = new Car(1L, "Toyota", "ABC123", 4, AvailabilityStatus.Available, LocalDateTime.now());
        when(carService.addCar(any(Car.class))).thenReturn(car);

        mockMvc.perform(post("/cars")
                        .contentType("application/json")
                        .content("{\"model\":\"Toyota\",\"licensePlate\":\"ABC123\",\"capacity\":4,\"availabilityStatus\":\"Available\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.carID").value(1))
                .andExpect(jsonPath("$.model").value("Toyota"))
                .andExpect(jsonPath("$.licensePlate").value("ABC123"));
    }

    @Test
    void testUpdateCar() throws Exception {
        // Test logic for updating a car
        Car car = new Car(1L, "Toyota", "ABC123", 4, AvailabilityStatus.Available, LocalDateTime.now());
        when(carService.getCarById(1L)).thenReturn(Optional.of(car));

        mockMvc.perform(put("/cars/1")
                        .contentType("application/json")
                        .content("{\"model\":\"Toyota\",\"licensePlate\":\"ABC123\",\"capacity\":4,\"availabilityStatus\":\"Booked\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.availabilityStatus").value("Booked"));

        verify(carService, times(1)).updateCar(eq(1L), any(Car.class));
    }

    @Test
    void testDeleteCar() throws Exception {
        // Test logic for deleting a car
        Car car = new Car(1L, "Toyota", "ABC123", 4, AvailabilityStatus.Available, LocalDateTime.now());
        when(carService.getCarById(1L)).thenReturn(Optional.of(car));

        mockMvc.perform(delete("/cars/1"))
                .andExpect(status().isNoContent());

        verify(carService, times(1)).deleteCar(1L);
    }

    @Test
    void testDeleteCarNotFound() throws Exception {
        // Test logic when the car is not found
        when(carService.getCarById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(delete("/cars/1"))
                .andExpect(status().isNotFound());

        verify(carService, times(1)).getCarById(1L);
    }
}
