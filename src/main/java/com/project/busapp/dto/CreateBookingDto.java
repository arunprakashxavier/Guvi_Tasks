package com.project.busapp.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class CreateBookingDto {
    @NotNull(message = "Bus ID is required")
    private Long busId;

    @NotNull(message = "User ID is required")
    private Long userId;

    @NotNull(message = "Number of seats is required")
    @Positive(message = "Number of seats must be positive")
    private Integer numberOfSeats; // Keep this for initial seat count

    @NotNull(message = "Passengers list cannot be null")
    @Size(min = 1, message = "At least one passenger is required") // Validate list size
    @Valid // This is VERY important - validates each PassengerDto in the list
    private List<PassengerDto> passengers; // Add this
}