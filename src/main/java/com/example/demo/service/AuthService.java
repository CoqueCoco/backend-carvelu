package com.example.demo.service;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.dto.AuthRequest;
import com.example.demo.dto.AuthResponse;
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.config.JwtService;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    public AuthResponse register(AuthRequest request) {

        if (userRepo.findByEmail(request.getEmail()).isPresent()) {
            return new AuthResponse("Email already used", null, null);
        }

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRoles(Set.of("USER"));

        userRepo.save(user);

        String token = jwtService.generateToken(user);

        return new AuthResponse(
                "User registered",
                user.getEmail(),
                token
        );
    }

    public AuthResponse login(AuthRequest request) {

        var user = userRepo.findByEmail(request.getEmail())
                .orElse(null);

        if (user == null || !passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            return new AuthResponse("Invalid credentials", null, null);
        }

        String token = jwtService.generateToken(user);

        return new AuthResponse(
                "User authenticated",
                user.getEmail(),
                token
        );
    }
}
