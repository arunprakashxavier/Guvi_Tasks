package com.project.busapp.service;

import com.project.busapp.dto.BookingDto;
import com.project.busapp.dto.CreateBookingDto;
import com.project.busapp.dto.PassengerDto;
import com.project.busapp.dto.UpdateBookingDto; // Import UpdateBookingDto

import java.util.List;

public interface BookingService {
    BookingDto createBooking(CreateBookingDto createBookingDto); // Use CreateBookingDto
    BookingDto getBookingById(Long id);
    List<BookingDto> getAllBookings(); // Might be admin-only
    List<BookingDto> getBookingsByUserId(Long userId);
    BookingDto updateBooking(Long id, UpdateBookingDto updateBookingDto); // Use UpdateBookingDto
    void deleteBooking(Long id);
    List<BookingDto> getBookingsByBusId(Long busId);
    List<PassengerDto> getPassengersByBookingId(Long bookingId); // New Method
}