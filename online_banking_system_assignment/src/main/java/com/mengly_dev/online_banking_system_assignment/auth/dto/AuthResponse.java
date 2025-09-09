package com.mengly_dev.online_banking_system_assignment.auth.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AuthResponse {
    // Getters and Setters
    private String token;
    private String username;
    public AuthResponse(String token, String username) {
        this.token = token;
        this.username = username;
    }

}

