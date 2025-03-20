package com.project.busapp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class BookingDto {
    private Long id;

    @NotNull(message = "Bus ID is required")
    private Long busId;

    @NotNull(message = "User ID is required")
    private Long userId;

    private LocalDateTime bookingDateTime;

    @NotNull(message = "Number of seats is required")
    @Positive(message = "Number of seats must be positive")
    private Integer numberOfSeats;

    private Double totalPrice;

    @NotBlank(message = "Booking status is required")
    private String status;

    private List<PassengerDto> passengers;

    // Add these fields (from Bus)
    private String busNumber;
    private String travelsName;
    private String busType;
    private LocalDateTime departureDateTime;
    private LocalDateTime arrivalDateTime;
    private String source; // Add source
    private String destination; // Add destination
}