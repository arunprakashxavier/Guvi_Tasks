package com.project.busapp.service;

import com.project.busapp.dto.BusDto;
import com.project.busapp.entity.Bus;
import com.project.busapp.exception.DuplicateResourceException;
import com.project.busapp.exception.ResourceNotFoundException;
import com.project.busapp.repository.BusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BusServiceImpl implements BusService {

    @Autowired
    private BusRepository busRepository;

    @Override
    public BusDto createBus(BusDto busDto) {
        // Check if a bus with the same bus number already exists
        if (busRepository.existsByBusNumber(busDto.getBusNumber())) {
            throw new DuplicateResourceException("Bus with bus number " + busDto.getBusNumber() + " already exists");
        }

        Bus bus = convertToEntity(busDto);
        Bus savedBus = busRepository.save(bus);
        return convertToDto(savedBus);
    }

    @Override
    public BusDto getBusById(Long id) {
        Bus bus = busRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Bus not found with id: " + id));
        return convertToDto(bus);
    }
    @Override
    public List<BusDto> getAllBuses() {
        List<Bus> buses = busRepository.findAll();
        return buses.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @Override
    public BusDto updateBus(Long id, BusDto busDto) {
        Bus existingBus = busRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Bus not found with id: " + id));
        // Check if the updated bus number already exists for a different bus
        if (!existingBus.getBusNumber().equals(busDto.getBusNumber()) &&
                busRepository.existsByBusNumber(busDto.getBusNumber())) {
            throw new DuplicateResourceException("Bus with bus number " + busDto.getBusNumber() + " already exists");
        }

        // Update the existing bus entity
        existingBus.setBusNumber(busDto.getBusNumber()); // Now we are checking unique bus number on update as well
        existingBus.setTravelsName(busDto.getTravelsName()); // Update travelsName
        existingBus.setBusType(busDto.getBusType());       // Update busType
        existingBus.setCapacity(busDto.getCapacity());
        existingBus.setSource(busDto.getSource());
        existingBus.setDestination(busDto.getDestination());
        existingBus.setDepartureDateTime(busDto.getDepartureDateTime()); //Update departureDateTime
        existingBus.setArrivalDateTime(busDto.getArrivalDateTime()); //Update arrivalDateTime
        existingBus.setPrice(busDto.getPrice());
        //existingBus.setDate(busDto.getDate()); //Remove

        Bus updatedBus = busRepository.save(existingBus);
        return convertToDto(updatedBus);
    }

    @Override
    public void deleteBus(Long id) {
        Bus bus = busRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Bus not found with id : "+id));
        busRepository.delete(bus); // Corrected to delete the entity, not just the ID
    }

    @Override
    public List<BusDto> searchBuses(String source, String destination, LocalDate date) {
        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.atTime(23, 59, 59); // End of the day

        List<Bus> buses = busRepository.findBySourceAndDestinationAndDepartureDateTimeBetween(source, destination, startOfDay, endOfDay); // New method
        if(buses.isEmpty()){
            throw new ResourceNotFoundException("No buses found for the given route and date");
        }
        return buses.stream().map(this::convertToDto).collect(Collectors.toList());
    }
    @Override
    public List<BusDto> searchBuses(String source, String destination, LocalDate date, String travelsName, String busType) {
        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.atTime(23, 59, 59);
        List<Bus> buses;

        if (travelsName != null && busType != null) {
            buses = busRepository.findBySourceAndDestinationAndDepartureDateTimeBetweenAndTravelsNameContainingIgnoreCaseAndBusTypeContainingIgnoreCase(source, destination, startOfDay,endOfDay, travelsName, busType);
        } else if (travelsName != null) {
            buses = busRepository.findBySourceAndDestinationAndDepartureDateTimeBetweenAndTravelsNameContainingIgnoreCase(source, destination,startOfDay, endOfDay, travelsName);
        } else if (busType != null) {
            buses = busRepository.findBySourceAndDestinationAndDepartureDateTimeBetweenAndBusTypeContainingIgnoreCase(source, destination, startOfDay, endOfDay, busType);
        } else {
            buses = busRepository.findBySourceAndDestinationAndDepartureDateTimeBetween(source, destination, startOfDay, endOfDay);
        }

        if (buses.isEmpty()) {
            throw new ResourceNotFoundException("No buses found for the given criteria"); // Consistent 404
        }

        return buses.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    // Helper methods to convert between entity and DTO
    private BusDto convertToDto(Bus bus) {
        BusDto busDto = new BusDto();
        busDto.setId(bus.getId());
        busDto.setBusNumber(bus.getBusNumber());
        busDto.setTravelsName(bus.getTravelsName()); // Convert travelsName
        busDto.setBusType(bus.getBusType());       // Convert busType
        busDto.setCapacity(bus.getCapacity());
        busDto.setSource(bus.getSource());
        busDto.setDestination(bus.getDestination());
        busDto.setDepartureDateTime(bus.getDepartureDateTime()); //Update
        busDto.setArrivalDateTime(bus.getArrivalDateTime());//Update
        busDto.setPrice(bus.getPrice());
        // busDto.setDate(bus.getDate()); //Remove
        return busDto;
    }

    private Bus convertToEntity(BusDto busDto) {
        Bus bus = new Bus();
        //  bus.setId(busDto.getId()); // Don't set the ID when creating a new entity; it's auto-generated
        bus.setBusNumber(busDto.getBusNumber());
        bus.setTravelsName(busDto.getTravelsName()); // Convert travelsName
        bus.setBusType(busDto.getBusType());       // Convert busType
        bus.setCapacity(busDto.getCapacity());
        bus.setSource(busDto.getSource());
        bus.setDestination(busDto.getDestination());
        bus.setDepartureDateTime(busDto.getDepartureDateTime()); //Update
        bus.setArrivalDateTime(busDto.getArrivalDateTime()); //Update
        bus.setPrice(busDto.getPrice());
        //bus.setDate(busDto.getDate()); //Remove
        return bus;
    }
}