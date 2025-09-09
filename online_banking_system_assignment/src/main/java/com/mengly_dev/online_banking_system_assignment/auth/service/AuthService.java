package com.mengly_dev.online_banking_system_assignment.auth.service;


import com.mengly_dev.online_banking_system_assignment.auth.dto.AuthResponse;
import com.mengly_dev.online_banking_system_assignment.auth.dto.LoginRequest;
import com.mengly_dev.online_banking_system_assignment.auth.dto.RegisterRequest;
import com.mengly_dev.online_banking_system_assignment.auth.entity.User;
import com.mengly_dev.online_banking_system_assignment.auth.repository.UserRepository;
import com.mengly_dev.online_banking_system_assignment.auth.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken; import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    public AuthResponse login(LoginRequest loginRequest) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword())
            );
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Invalid credentials", e);
        }
        final UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getUsername()); final String token = jwtUtil.generateToken(userDetails);
        return new AuthResponse(token, userDetails.getUsername());
    }
    public AuthResponse register(RegisterRequest registerRequest) {
        if (userRepository.existsByUsername(registerRequest.getUsername())) {
            throw new RuntimeException("Username already exists!");
        }
        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            throw new RuntimeException("Email already exists!");
        }
        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        userRepository.save(user);
        final UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername()); final String token = jwtUtil.generateToken(userDetails);
        return new AuthResponse(token, user.getUsername());
    }
}

