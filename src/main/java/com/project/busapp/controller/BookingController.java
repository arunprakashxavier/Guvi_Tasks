package com.project.busapp.controller;

import com.project.busapp.dto.BookingDto;
import com.project.busapp.dto.CreateBookingDto;
import com.project.busapp.dto.PassengerDto;
import com.project.busapp.dto.UpdateBookingDto;
import com.project.busapp.service.BookingService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/bookings")
@CrossOrigin(origins = "*", maxAge = 3600)
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @PostMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')") // Users and admins can book
    public ResponseEntity<BookingDto> createBooking(@Valid @RequestBody CreateBookingDto createBookingDto) {
        BookingDto createdBooking = bookingService.createBooking(createBookingDto);
        return new ResponseEntity<>(createdBooking, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')") // Users can see their own bookings, admins can see any
    public ResponseEntity<BookingDto> getBookingById(@PathVariable Long id) {
        BookingDto bookingDto = bookingService.getBookingById(id);
        return ResponseEntity.ok(bookingDto);
    }

    @GetMapping("/bus/{busId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<BookingDto>> getBookingsByBusId(@PathVariable Long busId) {
        List<BookingDto> bookings = bookingService.getBookingsByBusId(busId);
        return ResponseEntity.ok(bookings);
    }

    @GetMapping("/user/{userId}")
    @PreAuthorize("#userId == authentication.principal.id or hasRole('ADMIN')") // Only user can see his bookings.
    public ResponseEntity<List<BookingDto>> getBookingsByUserId(@PathVariable Long userId) {
        List<BookingDto> bookings = bookingService.getBookingsByUserId(userId);
        return ResponseEntity.ok(bookings);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<BookingDto>> getAllBookings() {
        return ResponseEntity.ok(bookingService.getAllBookings());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")  // Users and admins can update (e.g., cancel)
    public ResponseEntity<BookingDto> updateBooking(@PathVariable Long id, @Valid @RequestBody UpdateBookingDto updateBookingDto) { // Use UpdateBookingDto
        BookingDto updatedBooking = bookingService.updateBooking(id, updateBookingDto);
        return ResponseEntity.ok(updatedBooking);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')") // Users and admins can delete
    public ResponseEntity<Void> deleteBooking(@PathVariable Long id) {
        bookingService.deleteBooking(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{bookingId}/passengers")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')") // Users and admins can view passenger details
    public ResponseEntity<List<PassengerDto>> getPassengersByBookingId(@PathVariable Long bookingId) {
        List<PassengerDto> passengers = bookingService.getPassengersByBookingId(bookingId);
        return ResponseEntity.ok(passengers);
    }
}