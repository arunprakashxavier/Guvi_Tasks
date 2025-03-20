package com.project.busapp.dto;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class JwtResponse {
    private String token;
    private String type = "Bearer";
    private Long id;
    private String fullName;
    private String email;
    private List<String> roles;

    public JwtResponse(String accessToken, Long id, String fullName, String email, List<String> roles) {
        this.token = accessToken;
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.roles = roles;
    }
}