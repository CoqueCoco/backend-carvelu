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

    // ============================
    // REGISTER
    // ============================
    public AuthResponse register(AuthRequest request) {

        // Validar si email ya existe
        if (userRepo.findByEmail(request.getEmail()).isPresent()) {
            // El campo name será null en la respuesta de error
            return new AuthResponse("Email already used", null, null); 
        }

        // Crear nuevo usuario
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRoles(Set.of("USER")); 

        userRepo.save(user);

        // Generar JWT
        String token = jwtService.generateToken(user);

        // Retornar nombre en caso de registro exitoso
        return new AuthResponse("User registered", token, user.getName());
    }

    // ============================
    // LOGIN
    // ============================
    public AuthResponse login(AuthRequest request) {

        // Buscar usuario por email
        var user = userRepo.findByEmail(request.getEmail())
                .orElse(null);

        // Validar credenciales
        if (user == null || !passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            // ✅ Mensaje de error personalizado y name=null
            return new AuthResponse("Contraseña incorrecta, por favor intentar de nuevo", null, null); 
        }

        // Generar token JWT
        String token = jwtService.generateToken(user);

        // ✅ Retornar nombre en caso de autenticación exitosa
        return new AuthResponse("User authenticated", token, user.getName());
    }
}