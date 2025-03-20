package com.project.busapp.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "passengers")
public class Passenger {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Passenger name is required")
    @Size(max = 100, message = "Name must be less than 100 characters") // Example size limit
    private String name;

    @NotNull(message = "Passenger age is required")
    @Positive(message = "Age must be a positive number")
    private Integer age;

    @NotBlank(message = "Passenger gender is required")
    private String gender; // Could use an enum if you have a limited set of options

    @ManyToOne(fetch = FetchType.LAZY) // Many passengers can belong to one booking
    @JoinColumn(name = "booking_id") // Foreign key to Booking
    private Booking booking; // No @NotNull here; we'll set it from the Booking side
}