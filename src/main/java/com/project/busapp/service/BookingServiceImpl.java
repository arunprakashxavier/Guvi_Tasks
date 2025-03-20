package com.project.busapp.service;

import com.project.busapp.dto.BookingDto;
import com.project.busapp.dto.CreateBookingDto;
import com.project.busapp.dto.PassengerDto;
import com.project.busapp.dto.UpdateBookingDto;
import com.project.busapp.entity.Booking;
import com.project.busapp.entity.Bus;
import com.project.busapp.entity.Passenger;
import com.project.busapp.entity.User;
import com.project.busapp.exception.NotEnoughSeatsException;
import com.project.busapp.exception.ResourceNotFoundException;
import com.project.busapp.repository.BookingRepository;
import com.project.busapp.repository.BusRepository;
import com.project.busapp.repository.PassengerRepository;
import com.project.busapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookingServiceImpl implements BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private BusRepository busRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PassengerRepository passengerRepository; // Inject PassengerRepository

    @Autowired
    private EmailService emailService; // Inject EmailService

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");


    @Override
    @Transactional
    public BookingDto createBooking(CreateBookingDto createBookingDto) {
        Bus bus = busRepository.findById(createBookingDto.getBusId())
                .orElseThrow(() -> new ResourceNotFoundException("Bus not found with id: " + createBookingDto.getBusId()));
        User user = userRepository.findById(createBookingDto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + createBookingDto.getUserId()));

        // Validate number of passengers matches number of seats
        if (createBookingDto.getPassengers().size() != createBookingDto.getNumberOfSeats()) {
            throw new IllegalArgumentException("Number of passengers must match number of seats.");
        }

        int bookedSeats = bookingRepository.findByBus_Id(bus.getId()).stream()
                .mapToInt(Booking::getNumberOfSeats)
                .sum();

        if (bookedSeats + createBookingDto.getNumberOfSeats() > bus.getCapacity()) {
            throw new NotEnoughSeatsException("Not enough seats available on this bus.");
        }

        Booking booking = new Booking();
        booking.setBus(bus);
        booking.setUser(user);
        booking.setBookingDateTime(LocalDateTime.now());
        booking.setNumberOfSeats(createBookingDto.getNumberOfSeats());
        booking.setTotalPrice(bus.getPrice() * createBookingDto.getNumberOfSeats());
        booking.setStatus("CONFIRMED");

        // Save the booking *first* to get a booking ID
        booking = bookingRepository.save(booking);

        // Create and associate passengers
        for (PassengerDto passengerDto : createBookingDto.getPassengers()) {
            Passenger passenger = new Passenger();
            passenger.setName(passengerDto.getName());
            passenger.setAge(passengerDto.getAge());
            passenger.setGender(passengerDto.getGender());
            passenger.setBooking(booking); // Set the booking
            passengerRepository.save(passenger); // Save each passenger
            booking.getPassengers().add(passenger); // Add to the booking's list -  VERY important for Hibernate
        }

        //  booking = bookingRepository.save(booking); // No need to save again
        // --- EMAIL SENDING ---
        try {
            String bookingDetails = formatBookingDetails(booking); // Format the email body
            emailService.sendBookingConfirmationEmail(user.getEmail(), bookingDetails);
        } catch (Exception e) {
            // Log the error.  Don't let email sending failure break the booking.
            System.err.println("Failed to send email: " + e.getMessage());
            // Consider adding more robust error handling, like retrying or queuing the email.
        }
        return convertToDto(booking);
    }
    private String formatBookingDetails(Booking booking) {
        StringBuilder details = new StringBuilder();
        details.append("Booking Confirmation\n\n");
        details.append("Booking ID: ").append(booking.getId()).append("\n");
        details.append("Bus Number: ").append(booking.getBus().getBusNumber()).append("\n");
        details.append("Travels Name: ").append(booking.getBus().getTravelsName()).append("\n");
        details.append("Bus Type: ").append(booking.getBus().getBusType()).append("\n");
        details.append("Source: ").append(booking.getBus().getSource()).append("\n");
        details.append("Destination: ").append(booking.getBus().getDestination()).append("\n");
        details.append("Departure Time: ").append(booking.getBus().getDepartureDateTime().format(DateTimeFormatter.ofPattern("HH:mm"))).append("\n"); // Correct
        details.append("Arrival Time: ").append(booking.getBus().getArrivalDateTime().format(DateTimeFormatter.ofPattern("HH:mm"))).append("\n");   // Correct
        details.append("Date: ").append(booking.getBus().getDepartureDateTime().toLocalDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))).append("\n"); // Extract date
        details.append("Number of Seats: ").append(booking.getNumberOfSeats()).append("\n");
        details.append("Total Price: ").append(booking.getTotalPrice()).append("\n");
        details.append("Passengers:\n");
        for (Passenger passenger : booking.getPassengers()) {
            details.append("  - ").append(passenger.getName()).append(" (").append(passenger.getAge()).append(", ").append(passenger.getGender()).append(")\n");
        }
        details.append("\nBooking Date and Time: ").append(booking.getBookingDateTime().format(formatter)).append("\n");
        details.append("Status: ").append(booking.getStatus()).append("\n");

        return details.toString();
    }



    @Override
    public BookingDto getBookingById(Long id) {
        return convertToDto(bookingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found with id: " + id)));
    }

    @Override
    public List<BookingDto> getBookingsByBusId(Long busId) {
        return bookingRepository.findByBus_Id(busId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<BookingDto> getAllBookings() {
        return bookingRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<BookingDto> getBookingsByUserId(Long userId) {
        return bookingRepository.findByUser_Id(userId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public BookingDto updateBooking(Long id, UpdateBookingDto updateBookingDto) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found with id: " + id));

        if (updateBookingDto.getStatus() != null) {
            booking.setStatus(updateBookingDto.getStatus());
        }

        // Update passenger details *if* allowed and provided.
        if (updateBookingDto.getPassengers() != null && !updateBookingDto.getPassengers().isEmpty()) {
            // Clear existing passengers (because of orphanRemoval, this will delete them)
            booking.getPassengers().clear();

            for (PassengerDto passengerDto : updateBookingDto.getPassengers()) {
                Passenger passenger = new Passenger();
                passenger.setName(passengerDto.getName());
                passenger.setAge(passengerDto.getAge());
                passenger.setGender(passengerDto.getGender());
                passenger.setBooking(booking);  // Associate with the booking
                // Don't save passengers individually here. Let cascade handle it
                booking.getPassengers().add(passenger); // Add to booking's list
            }
        }

        /*  // Example - only if updating number of seats is allowed.
        if (updateBookingDto.getNumberOfSeats() != null) {
            int newNumberOfSeats = updateBookingDto.getNumberOfSeats();
            booking.setNumberOfSeats(newNumberOfSeats);

            // Recalculate available seats *after* updating booking.
            int bookedSeats = bookingRepository.findByBus_Id(booking.getBus().getId()).stream()
                    .mapToInt(Booking::getNumberOfSeats).sum();

            if (bookedSeats > booking.getBus().getCapacity()) {
                throw new NotEnoughSeatsException("Not enough seats available.");
            }
            booking.setTotalPrice(booking.getBus().getPrice() * newNumberOfSeats); // Recalculate price
        }
        */

        booking = bookingRepository.save(booking); // Save changes
        return convertToDto(booking);
    }
    @Override
    @Transactional
    public void deleteBooking(Long id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found with ID: " + id));
        booking.setStatus("CANCELLED");
        bookingRepository.save(booking);

    }
    @Override
    public List<PassengerDto> getPassengersByBookingId(Long bookingId) {
        List<Passenger> passengers = passengerRepository.findByBookingId(bookingId);
        return passengers.stream()
                .map(this::convertToPassengerDto)
                .collect(Collectors.toList());
    }

    // Helper method to convert Passenger to PassengerDto
    private PassengerDto convertToPassengerDto(Passenger passenger) {
        PassengerDto passengerDto = new PassengerDto();
        passengerDto.setId(passenger.getId());
        passengerDto.setName(passenger.getName());
        passengerDto.setAge(passenger.getAge());
        passengerDto.setGender(passenger.getGender());
        return passengerDto;
    }

    // Helper method to convert Booking to BookingDto (including passengers)
    private BookingDto convertToDto(Booking booking) {
        BookingDto dto = new BookingDto();
        dto.setId(booking.getId());
        dto.setBusId(booking.getBus().getId());
        dto.setUserId(booking.getUser().getId());
        dto.setBookingDateTime(booking.getBookingDateTime());
        dto.setNumberOfSeats(booking.getNumberOfSeats());
        dto.setTotalPrice(booking.getTotalPrice());
        dto.setStatus(booking.getStatus());

        // Convert passenger entities to DTOs and set in the BookingDto
        dto.setPassengers(booking.getPassengers().stream()
                .map(this::convertToPassengerDto)
                .collect(Collectors.toList()));

        // Add these lines to populate the new fields from the Bus entity:
        dto.setBusNumber(booking.getBus().getBusNumber());
        dto.setTravelsName(booking.getBus().getTravelsName());
        dto.setBusType(booking.getBus().getBusType());
        dto.setDepartureDateTime(booking.getBus().getDepartureDateTime());
        dto.setArrivalDateTime(booking.getBus().getArrivalDateTime());
        dto.setSource(booking.getBus().getSource()); // Set source
        dto.setDestination(booking.getBus().getDestination()); // Set destination

        return dto;
    }
}