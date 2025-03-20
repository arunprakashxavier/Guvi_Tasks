package com.project.busapp.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "bookings")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bus_id", nullable = false)
    @NotNull(message = "Bus is required")
    private Bus bus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @NotNull(message = "User is required")
    private User user;

    @NotNull(message = "Booking date and time is required")
    private LocalDateTime bookingDateTime;

    @NotNull(message = "Number of seats is required")
    @Positive(message = "Number of seats must be positive")
    private Integer numberOfSeats;

    @NotNull(message = "Total price is required")
    private Double totalPrice;

    @NotBlank(message = "Booking status is required")
    private String status;

    @OneToMany(mappedBy = "booking", cascade = CascadeType.ALL, orphanRemoval = true) // Add this
    private List<Passenger> passengers = new ArrayList<>(); // Initialize to avoid NullPointerException
}