package com.project.busapp.service;

import com.project.busapp.dto.BusDto;
import com.project.busapp.entity.Bus;

import java.time.LocalDate;
import java.util.List;

public interface BusService {
    BusDto createBus(BusDto busDto);
    BusDto getBusById(Long id);
    List<BusDto> getAllBuses();
    BusDto updateBus(Long id, BusDto busDto);
    void deleteBus(Long id);
    List<BusDto> searchBuses(String source, String destination, LocalDate date);
    List<BusDto> searchBuses(String source, String destination, LocalDate date, String travelsName, String busType); // Added Method.
}