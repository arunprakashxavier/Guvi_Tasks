package com.project.busapp.controller;

import com.project.busapp.dto.ChangePasswordDto;
import com.project.busapp.dto.UserProfileDto;
import com.project.busapp.service.UserService;
import com.project.busapp.security.jwt.JwtUtils; // Import JwtUtils
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtils jwtUtils; // Inject JwtUtils

    @GetMapping("/profile")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<UserProfileDto> getUserProfile(@RequestHeader("Authorization") String token) {
        Long userId = extractUserIdFromToken(token);
        UserProfileDto userProfileDto = userService.getUserProfile(userId);
        return ResponseEntity.ok(userProfileDto);
    }

    @PutMapping("/profile")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<UserProfileDto> updateUserProfile(@RequestHeader("Authorization") String token,
                                                            @Valid @RequestBody UserProfileDto userProfileDto) {
        Long userId = extractUserIdFromToken(token);
        UserProfileDto updatedProfile = userService.updateUserProfile(userId, userProfileDto);
        return ResponseEntity.ok(updatedProfile);
    }

    @PostMapping("/change-password")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> changePassword(@RequestHeader("Authorization") String token,
                                            @Valid @RequestBody ChangePasswordDto changePasswordDto) {
        Long userId = extractUserIdFromToken(token);
        userService.changePassword(userId, changePasswordDto);
        return ResponseEntity.ok("Password changed successfully."); // Or a MessageResponse
    }

    private Long extractUserIdFromToken(String token) {
        String jwt = token.substring(7); // Remove "Bearer " prefix
        return jwtUtils.getIdFromJwtToken(jwt); // Use the correct method
    }
}