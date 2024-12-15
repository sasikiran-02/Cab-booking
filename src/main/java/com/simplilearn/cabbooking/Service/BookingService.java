package com.simplilearn.cabbooking.Service;

import com.simplilearn.cabbooking.model.Booking;
import com.simplilearn.cabbooking.Repository.BookingRepository;
import com.simplilearn.cabbooking.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;

    @Autowired
    public BookingService(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    // Add a new booking
    public Booking addBooking(Booking booking) {
        return bookingRepository.save(booking);
    }

    // Find a booking by its ID
    public Optional<Booking> findBookingById(Long bookingId) {
        return bookingRepository.findById(bookingId);
    }

    // Get all bookings
    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    // Update a booking
    public Booking updateBooking(Long bookingId, Booking booking) {
        return bookingRepository.findById(bookingId)
                .map(existingBooking -> {
                    existingBooking.setUser(booking.getUser());
                    existingBooking.setCar(booking.getCar());
                    existingBooking.setPickupLocation(booking.getPickupLocation());
                    existingBooking.setDropoffLocation(booking.getDropoffLocation());
                    existingBooking.setPickupTime(booking.getPickupTime());
                    existingBooking.setDropoffTime(booking.getDropoffTime());
                    existingBooking.setBookingStatus(booking.getBookingStatus());
                    existingBooking.setCreatedAt(booking.getCreatedAt());
                    return bookingRepository.save(existingBooking);
                })
                .orElseThrow(() -> new IllegalArgumentException("Booking not found with ID: " + bookingId));
    }

    // Delete a booking by its ID
    public void deleteBooking(Long bookingId) {
        bookingRepository.findById(bookingId)
                .ifPresentOrElse(
                        booking -> bookingRepository.delete(booking),
                        () -> { throw new IllegalArgumentException("Booking not found with ID: " + bookingId); });
    }
    public List<Booking> getBookingsByUser(Optional<User> user) {
        return bookingRepository.findByUser(user);
    }
}