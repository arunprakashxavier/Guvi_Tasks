package com.project.busapp.service;

import com.project.busapp.dto.ChangePasswordDto;
import com.project.busapp.dto.UserProfileDto;
import com.project.busapp.entity.User;
import com.project.busapp.exception.InvalidPasswordException;
import com.project.busapp.exception.ResourceNotFoundException;
import com.project.busapp.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder; // Mock PasswordEncoder

    @InjectMocks
    private UserServiceImpl userService;

    private User user;
    private UserProfileDto userProfileDto;
    private ChangePasswordDto changePasswordDto;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setFullName("Test User");
        user.setEmail("test@example.com");
        user.setPassword("encodedOldPassword"); // Simulate already encoded password
        user.setMobileNumber("1234567890");
        user.setAge(30);
        user.setGender("Male");

        userProfileDto = new UserProfileDto();
        userProfileDto.setId(1L);
        userProfileDto.setFullName("Test User Updated");
        userProfileDto.setEmail("updated@example.com");
        userProfileDto.setMobileNumber("9876543210");
        userProfileDto.setAge(33);
        userProfileDto.setGender("Female");


        changePasswordDto = new ChangePasswordDto();
        changePasswordDto.setOldPassword("oldPassword");
        changePasswordDto.setNewPassword("newPassword");
        changePasswordDto.setConfirmNewPassword("newPassword");
    }

    @Test
    void getUserProfile_validId_returnsDto() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        UserProfileDto retrievedDto = userService.getUserProfile(1L);

        assertNotNull(retrievedDto);
        assertEquals("Test User", retrievedDto.getFullName());
        assertEquals("test@example.com", retrievedDto.getEmail());
        verify(userRepository).findById(1L);
    }

    @Test
    void getUserProfile_userNotFound_throwsException() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> userService.getUserProfile(1L));

        verify(userRepository).findById(1L);
    }
    @Test
    void updateUserProfile_validInput_returnsUpdatedDto() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user); // Return the updated User object

        UserProfileDto updatedDto = userService.updateUserProfile(1L, userProfileDto);

        assertNotNull(updatedDto);
        assertEquals("Test User Updated", updatedDto.getFullName()); //Check updated name.
        assertEquals("updated@example.com", updatedDto.getEmail()); // Check other updated field

        // Verify that the repository's save method was called with the updated User object:
        verify(userRepository).findById(1L);
        verify(userRepository).save(user); // Verify save was called with the correct user

        // Additional verification (optional, but good practice):
        assertEquals("Test User Updated", user.getFullName());  //Check updated name in user
        assertEquals("updated@example.com", user.getEmail());  // Check updated field in user
    }
    @Test
    void updateUserProfile_userNotFound_throwsException(){
        when(userRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> userService.updateUserProfile(1L, userProfileDto));
        verify(userRepository).findById(1L);
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void changePassword_validCredentials_passwordChanged() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));  // Find by ID
        when(passwordEncoder.matches("oldPassword", "encodedOldPassword")).thenReturn(true);
        when(passwordEncoder.encode("newPassword")).thenReturn("encodedNewPassword");
        when(userRepository.save(any(User.class))).thenReturn(user); // Return updated User


        userService.changePassword(1L, changePasswordDto);

        verify(userRepository).findById(1L);
        verify(passwordEncoder).matches("oldPassword", "encodedOldPassword");
        verify(passwordEncoder).encode("newPassword");
        verify(userRepository).save(user);

        // Assert that the password was actually updated
        assertEquals("encodedNewPassword", user.getPassword());
    }


    @Test
    void changePassword_userNotFound_throwsException() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty()); // User not found

        assertThrows(ResourceNotFoundException.class, () -> {
            userService.changePassword(1L, changePasswordDto);
        });
        verify(userRepository).findById(1L);
        verify(passwordEncoder, never()).matches(anyString(), anyString());
        verify(passwordEncoder, never()).encode(anyString());
        verify(userRepository, never()).save(any(User.class));

    }

    @Test
    void changePassword_incorrectOldPassword_throwsException() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(eq("wrongOldPassword"), anyString())).thenReturn(false); // Incorrect password

        changePasswordDto.setOldPassword("wrongOldPassword"); // Set an incorrect old password

        assertThrows(InvalidPasswordException.class, () -> {
            userService.changePassword(1L, changePasswordDto);
        });

        verify(userRepository).findById(1L);
        verify(passwordEncoder).matches("wrongOldPassword", "encodedOldPassword");
        verify(passwordEncoder, never()).encode(anyString()); // Ensure encode is not called
        verify(userRepository, never()).save(any(User.class));  // Ensure save is not called
    }

    @Test
    void changePassword_newPasswordsDoNotMatch_throwsException() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(eq("oldPassword"), anyString())).thenReturn(true); //Correct old password
        changePasswordDto.setConfirmNewPassword("mismatchedPassword");

        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> {
            userService.changePassword(1L, changePasswordDto);
        });

        verify(userRepository).findById(1L);
        verify(passwordEncoder).matches("oldPassword", "encodedOldPassword");
        verify(passwordEncoder, never()).encode(anyString()); // Ensure encode is not called
        verify(userRepository, never()).save(any(User.class));
    }
}