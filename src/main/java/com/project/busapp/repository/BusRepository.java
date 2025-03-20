package com.project.busapp.repository;

import com.project.busapp.entity.Bus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface BusRepository extends JpaRepository<Bus, Long> {
    // List<Bus> findBySourceAndDestinationAndDate(String source, String destination, LocalDate date); // Remove this
    Optional<Bus> findByBusNumber(String busNumber);
    boolean existsByBusNumber(String busNumber);

    // Add these methods:
    List<Bus> findBySourceAndDestinationAndDepartureDateTimeBetween(String source, String destination, LocalDateTime startOfDay, LocalDateTime endOfDay);
    List<Bus> findBySourceAndDestinationAndDepartureDateTimeBetweenAndTravelsNameContainingIgnoreCase(String source, String destination, LocalDateTime startOfDay, LocalDateTime endOfDay, String travelsName);
    List<Bus> findBySourceAndDestinationAndDepartureDateTimeBetweenAndBusTypeContainingIgnoreCase(String source, String destination, LocalDateTime startOfDay, LocalDateTime endOfDay, String busType);
    List<Bus> findBySourceAndDestinationAndDepartureDateTimeBetweenAndTravelsNameContainingIgnoreCaseAndBusTypeContainingIgnoreCase(String source, String destination, LocalDateTime startOfDay, LocalDateTime endOfDay, String travelsName, String busType);
}