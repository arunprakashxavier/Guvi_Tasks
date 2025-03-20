package com.project.busapp.service;

import com.project.busapp.dto.JwtResponse;
import com.project.busapp.dto.LoginRequest;
import com.project.busapp.dto.SignupRequest;
import org.springframework.http.ResponseEntity;

public interface AuthService {
    ResponseEntity<?> registerUser(SignupRequest signUpRequest);
    ResponseEntity<JwtResponse> authenticateUser(LoginRequest loginRequest);
}