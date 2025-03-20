package com.project.busapp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
@Data
public class BusDto {
    private Long id;
    @NotBlank(message = "Bus number is required")
    private String busNumber;

    @NotBlank(message = "Travels name is required") // New field
    private String travelsName;

    @NotBlank(message = "Bus type is required") // New field
    private String busType;

    @NotNull(message = "Capacity is required")
    @Positive(message = "Capacity must be a positive number")
    private Integer capacity;

    @NotBlank(message = "Source is required")
    private String source;

    @NotBlank(message = "Destination is required")
    private String destination;

    @NotNull(message = "Departure date and time is required")
    private LocalDateTime departureDateTime; // Combined date and time

    @NotNull(message = "Arrival date and time is required")
    private LocalDateTime arrivalDateTime; // Combined date and time

    @NotNull(message = "Price is required")
    @Positive(message = "Price must be a positive number")
    private Double price;

//     @NotNull(message = = "Date is required") //Remove
//     private LocalDate date;
}