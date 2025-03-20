package com.project.busapp.controller;

import com.project.busapp.dto.BusDto;
import com.project.busapp.entity.Bus;
import com.project.busapp.repository.BusRepository;
import com.project.busapp.service.BusService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/buses")
@CrossOrigin(origins = "*", maxAge = 3600)
public class BusController {

    @Autowired
    private BusService busService;

    @Autowired
    private BusRepository busRepository;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')") // Only admins can create buses
    public ResponseEntity<BusDto> createBus(@Valid @RequestBody BusDto busDto) {
        BusDto createdBus = busService.createBus(busDto);
        return new ResponseEntity<>(createdBus, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BusDto> getBusById(@PathVariable Long id) {
        BusDto busDto = busService.getBusById(id);
        return ResponseEntity.ok(busDto);
    }
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<BusDto>> getAllBuses(){
        return ResponseEntity.ok(busService.getAllBuses());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BusDto> updateBus(@PathVariable Long id, @Valid @RequestBody BusDto busDto) {
        BusDto updatedBus = busService.updateBus(id, busDto);
        return ResponseEntity.ok(updatedBus);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteBus(@PathVariable Long id) {
        busService.deleteBus(id);
        return ResponseEntity.noContent().build();
    }

    // Endpoint for users to search for buses
    @GetMapping("/search")
    public ResponseEntity<List<BusDto>> searchBuses(
            @RequestParam String source,
            @RequestParam String destination,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam(required = false) String travelsName, // Optional search parameter
            @RequestParam(required = false) String busType) {     // Optional search parameter

        List<BusDto> busDtos = busService.searchBuses(source, destination, date, travelsName, busType);
        return ResponseEntity.ok(busDtos);
    }
}