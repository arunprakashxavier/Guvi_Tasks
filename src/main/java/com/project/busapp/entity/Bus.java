package com.project.busapp.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime; // Change to LocalDateTime

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "buses")
public class Bus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Bus number is required")
    @Column(unique = true)
    private String busNumber;

    @NotBlank(message = "Travels name is required")
    private String travelsName;

    @NotBlank(message = "Bus type is required")
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

    // Remove the separate 'date' field
    // private LocalDate date;
}