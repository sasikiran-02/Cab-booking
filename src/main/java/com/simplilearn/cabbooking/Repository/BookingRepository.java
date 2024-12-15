package com.simplilearn.cabbooking.Repository;

import com.simplilearn.cabbooking.model.Booking;
import com.simplilearn.cabbooking.model.Car;
import com.simplilearn.cabbooking.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByUser(Optional<User> user);
    List<Booking> findByCar(Car car);
    List<Booking> findByBookingStatus(Booking.BookingStatus status);
    List<Booking> findByPickupTimeBetween(LocalDateTime start, LocalDateTime end);
}

