package com.project.busapp.service;

import com.project.busapp.dto.BusDto;
import com.project.busapp.entity.Bus;
import com.project.busapp.repository.BusRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.project.busapp.exception.ResourceNotFoundException;
import com.project.busapp.exception.DuplicateResourceException;


import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BusServiceImplTest {

    @Mock
    private BusRepository busRepository;

    @InjectMocks
    private BusServiceImpl busService;

    private BusDto busDto;  //For testing create method.
    private Bus bus;     // For retrieving.

    @BeforeEach
    void setUp() {
        busDto = new BusDto();
        busDto.setBusNumber("TEST123");
        busDto.setTravelsName("Test Travels");
        busDto.setBusType("AC Sleeper");
        busDto.setCapacity(30);
        busDto.setSource("City A");
        busDto.setDestination("City B");
        busDto.setDepartureDateTime(LocalDateTime.now().plusDays(1));
        busDto.setArrivalDateTime(LocalDateTime.now().plusDays(1).plusHours(5));
        busDto.setPrice(100.0);

        bus = new Bus();
        bus.setId(1L);
        bus.setBusNumber("TEST123");
        bus.setTravelsName("Test Travels");
    }


    @Test
    void createBus_validBus_returnsDto() {
        // Arrange
        when(busRepository.existsByBusNumber(anyString())).thenReturn(false);
        when(busRepository.save(any(Bus.class))).thenReturn(bus); // Return a Bus object

        // Act
        BusDto createdDto = busService.createBus(busDto);

        // Assert
        assertNotNull(createdDto);
        assertEquals("TEST123", createdDto.getBusNumber());

        verify(busRepository).existsByBusNumber("TEST123");
        verify(busRepository).save(any(Bus.class));
    }


    @Test
    void getBusById_validId_returnsDto() {
        // Arrange
        when(busRepository.findById(1L)).thenReturn(Optional.of(bus));

        // Act
        BusDto retrievedDto = busService.getBusById(1L);

        // Assert
        assertNotNull(retrievedDto);
        assertEquals("TEST123", retrievedDto.getBusNumber());

        verify(busRepository).findById(1L);
    }

    // Inside BusServiceImplTest

    @Test
    void updateBus_validInput_returnsUpdatedDto() {
        // Arrange
        Bus existingBus = new Bus();
        existingBus.setId(1L);
        existingBus.setBusNumber("OLD123");
        existingBus.setTravelsName("Old Travels");

        BusDto updatedBusDto = new BusDto();
        updatedBusDto.setBusNumber("NEW456"); // Changed bus number
        updatedBusDto.setTravelsName("New Travels"); // Changed name

        when(busRepository.findById(1L)).thenReturn(Optional.of(existingBus));
        when(busRepository.existsByBusNumber("NEW456")).thenReturn(false); // Simulate no duplicate
        when(busRepository.save(any(Bus.class))).thenAnswer(invocation -> invocation.getArgument(0)); // Return the updated Bus object

        // Act
        BusDto resultDto = busService.updateBus(1L, updatedBusDto);

        // Assert
        assertNotNull(resultDto);
        assertEquals("NEW456", resultDto.getBusNumber());
        assertEquals("New Travels", resultDto.getTravelsName());

        verify(busRepository).findById(1L);
        verify(busRepository).existsByBusNumber("NEW456");
        verify(busRepository).save(any(Bus.class));
    }

    @Test
    void updateBus_busNotFound_throwsException() {
        // Arrange
        when(busRepository.findById(999L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> {
            busService.updateBus(999L, new BusDto()); // Pass a dummy DTO
        });

        verify(busRepository).findById(999L);
        verify(busRepository, never()).save(any(Bus.class));
    }
    @Test
    void updateBus_duplicateBusNumber_throwsException() {
        // Arrange
        Bus existingBus = new Bus();
        existingBus.setId(1L);
        existingBus.setBusNumber("OLD123");


        BusDto updatedBusDto = new BusDto();
        updatedBusDto.setBusNumber("NEW456"); // Changed bus number

        when(busRepository.findById(1L)).thenReturn(Optional.of(existingBus));
        when(busRepository.existsByBusNumber("NEW456")).thenReturn(true); // Simulate duplicate

        // Act & Assert
        assertThrows(DuplicateResourceException.class, () -> {
            busService.updateBus(1L, updatedBusDto); // Pass a dummy DTO
        });

        verify(busRepository).findById(1L);
        verify(busRepository).existsByBusNumber("NEW456");
        verify(busRepository, never()).save(any(Bus.class));
    }
}