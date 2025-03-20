package com.project.busapp.service;

import com.project.busapp.dto.ChangePasswordDto;
import com.project.busapp.dto.UserProfileDto;
import com.project.busapp.entity.User;
import com.project.busapp.exception.InvalidPasswordException;
import com.project.busapp.exception.ResourceNotFoundException;
import com.project.busapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder; // Inject PasswordEncoder

    @Override
    public UserProfileDto getUserProfile(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
        return convertToDto(user);
    }

    @Override
    @Transactional // Important for data consistency
    public UserProfileDto updateUserProfile(Long userId, UserProfileDto userProfileDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

        // Update fields *only* if they are provided in the DTO and are not blank
        if (userProfileDto.getFullName() != null && !userProfileDto.getFullName().isBlank()) {
            user.setFullName(userProfileDto.getFullName());
        }
        if (userProfileDto.getAge()!=0) {
            user.setAge(userProfileDto.getAge());
        }
        if (userProfileDto.getGender() != null && !userProfileDto.getGender().isBlank()) {
            user.setGender(userProfileDto.getGender());
        }
        if (userProfileDto.getMobileNumber() != null && !userProfileDto.getMobileNumber().isBlank()) {
            user.setMobileNumber(userProfileDto.getMobileNumber());
        }
        if (userProfileDto.getEmail() != null && !userProfileDto.getEmail().isBlank()) {
            user.setEmail(userProfileDto.getEmail());
        }
        // We *do not* update the password here.

        user = userRepository.save(user);
        return convertToDto(user);
    }
    @Override
    @Transactional
    public void changePassword(Long userId, ChangePasswordDto changePasswordDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

        // 1. Check if old password matches
        if (!passwordEncoder.matches(changePasswordDto.getOldPassword(), user.getPassword())) {
            throw new InvalidPasswordException("Incorrect old password.");
        }

        // 2. Check if new password and confirm password match
        if (!changePasswordDto.getNewPassword().equals(changePasswordDto.getConfirmNewPassword())) {
            throw new IllegalArgumentException("New password and confirm password do not match.");
        }

        // 3. Hash and set the new password
        user.setPassword(passwordEncoder.encode(changePasswordDto.getNewPassword()));

        // 4. Save the user
        userRepository.save(user);
    }


    private UserProfileDto convertToDto(User user) {
        UserProfileDto dto = new UserProfileDto();
        dto.setId(user.getId());
        dto.setFullName(user.getFullName());
        dto.setAge(user.getAge());
        dto.setGender(user.getGender());
        dto.setMobileNumber(user.getMobileNumber());
        dto.setEmail(user.getEmail());
        return dto;
    }
}