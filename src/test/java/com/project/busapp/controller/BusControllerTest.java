package com.project.busapp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.project.busapp.dto.BusDto;
import com.project.busapp.service.BusService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class) // Use Mockito Extension
public class BusControllerTest {

    private MockMvc mockMvc; // MockMVC manually initialized

    @Mock // Use @Mock instead of @MockBean
    private BusService busService;

    @InjectMocks // Inject the mocks into the controller
    private BusController busController;

    private ObjectMapper objectMapper; // JSON Mapper

    private BusDto busDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(busController).build(); // Manually setup MockMvc

        busDto = new BusDto();
        busDto.setBusNumber("123");
        busDto.setTravelsName("Travels A");
        busDto.setBusType("AC Sleeper");
        busDto.setCapacity(30);
        busDto.setSource("City A");
        busDto.setDestination("City B");
        busDto.setDepartureDateTime(LocalDateTime.now().plusDays(1));
        busDto.setArrivalDateTime(LocalDateTime.now().plusDays(1).plusHours(5));
        busDto.setPrice(100.0);

        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule()); // Add this!
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS); // And this!
    }

    @Test
    @WithMockUser(roles = "ADMIN") // Simulate an ADMIN user
    void createBus_success() throws Exception {
        when(busService.createBus(any(BusDto.class))).thenReturn(busDto);

        mockMvc.perform(post("/api/buses") // Use `post()` from MockMvcRequestBuilders
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(busDto)))
                .andExpect(status().isCreated()) // Verify 201 Created status
                .andExpect(jsonPath("$.busNumber").value("123")); // Verify response body
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void getBusById_success() throws Exception {
        when(busService.getBusById(1L)).thenReturn(busDto);

        mockMvc.perform(get("/api/buses/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.busNumber").value("123"));
    }

    @Test
    @WithMockUser(roles = "USER") // Regular user can search
    void searchBuses_success() throws Exception {
        List<BusDto> searchResults = Arrays.asList(busDto);
        when(busService.searchBuses(anyString(), anyString(), any(LocalDate.class), isNull(), isNull())).thenReturn(searchResults);

        mockMvc.perform(get("/api/buses/search")
                        .param("source", "City A")
                        .param("destination", "City B")
                        .param("date", "2025-03-28"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].busNumber").value("123"));

        verify(busService).searchBuses(eq("City A"), eq("City B"), eq(LocalDate.of(2025, 3, 28)), isNull(), isNull());
    }
}