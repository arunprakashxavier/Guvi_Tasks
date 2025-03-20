package com.project.busapp.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.Set;
@Data
public class SignupRequest {
    @NotBlank(message = "Full name is required")
    private String fullName;

    @Min(value = 18, message = "Age must be at least 18")
    private int age;

    @NotBlank(message = "Gender is required")
    private String gender;

    @NotBlank(message = "Mobile number is required")
    @Pattern(regexp = "\\d{10}", message = "Mobile number must be 10 digits")
    private String mobileNumber;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters")
    private String password;

    private Set<String> roles; // We'll receive role names as strings
}