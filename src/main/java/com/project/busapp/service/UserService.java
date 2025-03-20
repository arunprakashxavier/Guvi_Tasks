package com.project.busapp.service;

import com.project.busapp.dto.ChangePasswordDto; // Import ChangePasswordDto
import com.project.busapp.dto.UserProfileDto;
import com.project.busapp.entity.User;

public interface UserService {
    UserProfileDto getUserProfile(Long userId);
    UserProfileDto updateUserProfile(Long userId, UserProfileDto userProfileDto);
    void changePassword(Long userId, ChangePasswordDto changePasswordDto); // Add this method

}