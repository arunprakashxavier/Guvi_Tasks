package com.project.busapp.service;

import com.project.busapp.dto.BookingDto;
import com.project.busapp.dto.CreateBookingDto;
import com.project.busapp.dto.PassengerDto;
import com.project.busapp.dto.UpdateBookingDto;
import com.project.busapp.entity.Booking;
import com.project.busapp.entity.Bus;
import com.project.busapp.entity.Passenger;
import com.project.busapp.entity.User;
import com.project.busapp.exception.NotEnoughSeatsException;
import com.project.busapp.exception.ResourceNotFoundException;
import com.project.busapp.repository.BookingRepository;
import com.project.busapp.repository.BusRepository;
import com.project.busapp.repository.PassengerRepository;
import com.project.busapp.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookingServiceImplTest {

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private BusRepository busRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PassengerRepository passengerRepository;

    @Mock
    private EmailService emailService; // Mock EmailService

    @InjectMocks
    private BookingServiceImpl bookingService;

    @Captor
    private ArgumentCaptor<Booking> bookingCaptor;

    private Bus bus;
    private User user;
    private CreateBookingDto createBookingDto;
    private UpdateBookingDto updateBookingDto;
    private Booking booking;
    private List<PassengerDto> passengerDtos;

    @BeforeEach
    void setUp() {
        // Create a sample Bus
        bus = new Bus();
        bus.setId(1L);
        bus.setBusNumber("BUS123");
        bus.setCapacity(30);
        bus.setDepartureDateTime(LocalDateTime.now().plusDays(1)); // Valid departure time
        bus.setPrice(50.0);

        // Create a sample User
        user = new User();
        user.setId(2L);
        user.setEmail("test@example.com");

        // Create sample PassengerDTOs
        passengerDtos = new ArrayList<>();
        PassengerDto passenger1 = new PassengerDto();
        passenger1.setName("Alice");
        passenger1.setAge(30);
        passenger1.setGender("Female");
        passengerDtos.add(passenger1);

        PassengerDto passenger2 = new PassengerDto();
        passenger2.setName("Bob");
        passenger2.setAge(40);
        passenger2.setGender("Male");
        passengerDtos.add(passenger2);

        // Create a sample CreateBookingDto
        createBookingDto = new CreateBookingDto();
        createBookingDto.setBusId(1L);
        createBookingDto.setUserId(2L);
        createBookingDto.setNumberOfSeats(2);
        createBookingDto.setPassengers(passengerDtos);

        // Create a sample existing Booking
        booking = new Booking();
        booking.setId(3L);
        booking.setBus(bus);
        booking.setUser(user);
        booking.setNumberOfSeats(2);
        booking.setStatus("CONFIRMED");
        booking.setTotalPrice(100.0);
        booking.setBookingDateTime(LocalDateTime.now());
        List<Passenger> passengers = new ArrayList<>();
        passengers.add(new Passenger()); // Initial Passengers.
        passengers.add(new Passenger());
        booking.setPassengers(passengers);

        // Create a sample UpdateBookingDto
        updateBookingDto = new UpdateBookingDto();
        updateBookingDto.setStatus("CANCELLED");

    }

    @Test
    void createBooking_validInput_returnsBookingDto() {
        // Arrange
        // Ensure the mocked Bus object has all necessary fields set!
        bus.setDepartureDateTime(LocalDateTime.now().plusDays(1));
        bus.setArrivalDateTime(LocalDateTime.now().plusDays(1).plusHours(5));


        when(busRepository.findById(1L)).thenReturn(Optional.of(bus));
        when(userRepository.findById(2L)).thenReturn(Optional.of(user));
        when(bookingRepository.findByBus_Id(1L)).thenReturn(new ArrayList<>()); // No existing bookings
        when(bookingRepository.save(any(Booking.class))).thenAnswer(invocation -> {  // Return saved entity
            Booking savedBooking = invocation.getArgument(0);
            savedBooking.setId(3L); // Simulate ID generation
            return savedBooking;
        });
        //We do not need to mock passengerRepository.save() because we have used CascadeType.ALL.

        // Act
        BookingDto createdBookingDto = bookingService.createBooking(createBookingDto);


        // Assert
        assertNotNull(createdBookingDto);
        assertEquals(3L, createdBookingDto.getId()); // Verify ID
        assertEquals("CONFIRMED", createdBookingDto.getStatus());
        assertEquals(1L, createdBookingDto.getBusId());
        assertEquals(2L, createdBookingDto.getUserId());
        assertEquals(2, createdBookingDto.getNumberOfSeats());
        assertEquals(100.0, createdBookingDto.getTotalPrice()); // Check price
        assertEquals(2, createdBookingDto.getPassengers().size()); // Check Passengers
        assertEquals("Alice", createdBookingDto.getPassengers().get(0).getName()); //Check Passenger name
        // Verify interactions with mocked repositories
        verify(busRepository).findById(1L);
        verify(userRepository).findById(2L);
        verify(bookingRepository).findByBus_Id(1L);
        verify(bookingRepository).save(bookingCaptor.capture()); // Capture the saved booking
        verify(passengerRepository, times(2)).save(any(Passenger.class)); // Verify passenger save (2 times)
        verify(emailService).sendBookingConfirmationEmail(eq(user.getEmail()), anyString());

        //Further, more specific, assertions on captured booking:
        Booking capturedBooking = bookingCaptor.getValue();
        assertEquals("CONFIRMED", capturedBooking.getStatus());
        assertEquals(2, capturedBooking.getPassengers().size()); // Verify passengers added to booking

    }


    @Test
    void createBooking_busNotFound_throwsResourceNotFoundException() {
        when(busRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            bookingService.createBooking(createBookingDto);
        });
        verify(bookingRepository, never()).save(any(Booking.class));
    }

    @Test
    void createBooking_userNotFound_throwsResourceNotFoundException() {
        when(busRepository.findById(1L)).thenReturn(Optional.of(bus));
        when(userRepository.findById(2L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            bookingService.createBooking(createBookingDto);
        });
        verify(bookingRepository, never()).save(any(Booking.class));
    }

    @Test
    void createBooking_notEnoughSeats_throwsNotEnoughSeatsException() {
        when(busRepository.findById(1L)).thenReturn(Optional.of(bus));
        when(userRepository.findById(2L)).thenReturn(Optional.of(user));
        // Simulate existing bookings that fill up the bus
        List<Booking> existingBookings = new ArrayList<>();
        Booking existingBooking = new Booking();
        existingBooking.setNumberOfSeats(30); // Bus is already full
        existingBookings.add(existingBooking);
        when(bookingRepository.findByBus_Id(1L)).thenReturn(existingBookings);

        assertThrows(NotEnoughSeatsException.class, () -> {
            bookingService.createBooking(createBookingDto);
        });
        verify(bookingRepository, never()).save(any(Booking.class));
    }

    @Test
    void createBooking_passengerMismatch_throwsIllegalArgumentException(){
        createBookingDto.setNumberOfSeats(3); // Mismatched number of seats.
        when(busRepository.findById(1L)).thenReturn(Optional.of(bus));
        when(userRepository.findById(2L)).thenReturn(Optional.of(user));
        assertThrows(IllegalArgumentException.class, () -> {
            bookingService.createBooking(createBookingDto);
        });
        verify(bookingRepository, never()).save(any(Booking.class));
    }

    @Test
    void getBookingById_validId_returnsBookingDto() {
        when(bookingRepository.findById(3L)).thenReturn(Optional.of(booking));

        BookingDto result = bookingService.getBookingById(3L);

        assertNotNull(result);
        assertEquals(3L, result.getId());
        verify(bookingRepository).findById(3L);
    }

    @Test
    void getBookingById_invalidId_throwsResourceNotFoundException() {
        when(bookingRepository.findById(999L)).thenReturn(Optional.empty()); // Simulate not found

        assertThrows(ResourceNotFoundException.class, () -> {
            bookingService.getBookingById(999L);
        });
        verify(bookingRepository).findById(999L);
    }

    @Test
    void getBookingsByUserId_validUserId_returnsListOfBookings() {
        List<Booking> bookings = new ArrayList<>();
        bookings.add(booking); // Add our sample booking
        when(bookingRepository.findByUser_Id(2L)).thenReturn(bookings);

        List<BookingDto> result = bookingService.getBookingsByUserId(2L);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(booking.getId(), result.get(0).getId()); // Check IDs match.
        verify(bookingRepository).findByUser_Id(2L);
    }

    @Test
    void getBookingsByBusId_validBusId_returnsListOfBookings() {
        List<Booking> bookings = List.of(booking);
        when(bookingRepository.findByBus_Id(1L)).thenReturn(bookings);

        List<BookingDto> result = bookingService.getBookingsByBusId(1L);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        verify(bookingRepository).findByBus_Id(1L);
    }

    @Test
    void updateBooking_validInput_updatesBooking() {
        when(bookingRepository.findById(3L)).thenReturn(Optional.of(booking));
        when(bookingRepository.save(any(Booking.class))).thenAnswer(i -> i.getArgument(0)); // Return saved object

        BookingDto updatedBooking = bookingService.updateBooking(3L, updateBookingDto);

        assertNotNull(updatedBooking);
        assertEquals("CANCELLED", updatedBooking.getStatus());  // Check the updated status
        verify(bookingRepository).findById(3L);
        verify(bookingRepository).save(booking); // Verify save was called

        assertEquals("CANCELLED", booking.getStatus());// Check status updated in entity.
    }

    @Test
    void updateBooking_bookingNotFound_throwsResourceNotFoundException() {
        when(bookingRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            bookingService.updateBooking(999L, updateBookingDto);
        });
        verify(bookingRepository, never()).save(any(Booking.class));
    }


    @Test
    void deleteBooking_validId_softDeletesBooking() { // We use Cancelled to delete.
        when(bookingRepository.findById(3L)).thenReturn(Optional.of(booking));
        when(bookingRepository.save(any(Booking.class))).thenReturn(booking);

        bookingService.deleteBooking(3L);

        assertEquals("CANCELLED", booking.getStatus()); // Check status is updated
        verify(bookingRepository).findById(3L);
        verify(bookingRepository).save(booking); // Verify save called.

    }

    @Test
    void deleteBooking_bookingNotFound_throwsResourceNotFoundException() {
        when(bookingRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, ()->{
            bookingService.deleteBooking(999L);
        });

        verify(bookingRepository).findById(999L);
        verify(bookingRepository, never()).save(any(Booking.class)); // Ensure save is never called
    }
    @Test
    void getPassengersByBookingId_validId_returnsListOfPassengers() {
        // Create some sample passengers associated with the booking
        Passenger passenger1 = new Passenger();
        passenger1.setId(101L);
        passenger1.setName("Test Passenger 1");
        passenger1.setBooking(booking);

        Passenger passenger2 = new Passenger();
        passenger2.setId(102L);
        passenger2.setName("Test Passenger 2");
        passenger2.setBooking(booking);

        List<Passenger> passengerList = List.of(passenger1, passenger2);

        when(passengerRepository.findByBookingId(3L)).thenReturn(passengerList);


        List<PassengerDto> result = bookingService.getPassengersByBookingId(3L);

        assertNotNull(result);
        assertEquals(2, result.size()); // Verify two passengers are returned
        assertEquals("Test Passenger 1", result.get(0).getName());
        assertEquals("Test Passenger 2", result.get(1).getName());
        verify(passengerRepository).findByBookingId(3L);
    }


}