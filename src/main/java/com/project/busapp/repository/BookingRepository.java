package com.project.busapp.repository;

import com.project.busapp.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByUser_Id(Long userId); // Find bookings by user ID
    List<Booking> findByBus_Id(Long busId); //Find bookings by bus ID
}