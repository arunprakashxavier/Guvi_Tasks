package com.project.busapp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PassengerDto {
    private Long id;

    @NotBlank(message = "Passenger name is required")
    @Size(max = 100, message = "Name must be less than 100 characters")
    private String name;

    @NotNull(message = "Passenger age is required")
    @Positive(message = "Age must be a positive number")
    private Integer age;

    @NotBlank(message = "Passenger gender is required")
    private String gender;

    // No bookingId field here; it's handled through the BookingDto
}