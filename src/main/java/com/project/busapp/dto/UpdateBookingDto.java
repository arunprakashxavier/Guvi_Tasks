package com.project.busapp.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;
@Data
public class UpdateBookingDto {

    @NotBlank(message = "Booking status is required") // Only allow updating status
    private String status;

    // Add other fields here *if* you want to allow updating them.
    // For example, you *might* allow changing the number of seats,
    // but you probably would *not* allow changing the busId or userId.
    // private Integer numberOfSeats; // Example - only if you allow seat changes
    @Valid
    private List<PassengerDto> passengers;
}