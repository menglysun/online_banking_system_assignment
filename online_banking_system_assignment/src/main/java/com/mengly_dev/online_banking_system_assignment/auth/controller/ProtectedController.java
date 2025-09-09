package com.mengly_dev.online_banking_system_assignment.auth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/protected")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ProtectedController {
    @GetMapping("/user")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<Map<String, String>> getUserInfo(Authentication authentication) { Map<String, String> response = new HashMap<>();
        response.put("message", "Hello " + authentication.getName());
        response.put("role", authentication.getAuthorities().toString());
        return ResponseEntity.ok(response);
    }
    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, String>> getAdminInfo(Authentication authentication) { Map<String, String> response = new HashMap<>();
        response.put("message", "Admin access granted for " + authentication.getName()); return ResponseEntity.ok(response);
    }
    @GetMapping("/public")
    public ResponseEntity<Map<String, String>> getPublicInfo() {
        Map<String, String> response = new HashMap<>();
        response.put("message", "This is a public endpoint");
        return ResponseEntity.ok(response);
    }
}

