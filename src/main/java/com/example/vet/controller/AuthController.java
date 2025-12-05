package com.example.vet.controller;

import com.example.vet.dto.LoginRequest;
import com.example.vet.dto.RegisterRequest;
import com.example.vet.dto.LoginResponse;
import com.example.vet.dto.RegisterResponse;
import com.example.vet.model.UserEntity;
import com.example.vet.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        // ⚠️ En Basic Auth, Spring valida automáticamente.
        // Aquí devolvemos un objeto de respuesta de ejemplo.
        LoginResponse response = new LoginResponse(
                "Login exitoso",
                request.getUsername(),
                "USER" // En real, se obtendría del contexto de seguridad
        );
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@RequestBody RegisterRequest request) {
        // ✅ Crear y guardar el usuario en la BD
        UserEntity user = new UserEntity();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword())); // encriptar contraseña
        user.setRole(request.getRole());

        userRepository.save(user);

        RegisterResponse response = new RegisterResponse(
                "Usuario registrado correctamente",
                user.getUsername(),
                user.getRole()
        );
        return ResponseEntity.ok(response);
    }
}
