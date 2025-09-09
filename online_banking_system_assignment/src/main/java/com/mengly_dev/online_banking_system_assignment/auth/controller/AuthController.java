package com.mengly_dev.online_banking_system_assignment.auth.controller;

import com.mengly_dev.online_banking_system_assignment.auth.dto.AuthResponse;
import com.mengly_dev.online_banking_system_assignment.auth.dto.LoginRequest;
import com.mengly_dev.online_banking_system_assignment.auth.dto.RegisterRequest;
import com.mengly_dev.online_banking_system_assignment.auth.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest loginRequest) {
        try {
            AuthResponse response = authService.login(loginRequest);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest registerRequest) {

        AuthResponse response = authService.register(registerRequest);
        return ResponseEntity.ok(response);

    }
}

