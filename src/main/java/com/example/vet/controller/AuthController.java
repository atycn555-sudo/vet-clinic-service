package com.example.vet.controller;

import com.example.vet.dto.LoginRequest;
import com.example.vet.dto.RegisterRequest;
import com.example.vet.dto.LoginResponse;
import com.example.vet.dto.RegisterResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        // En Basic Auth, Spring valida automáticamente.
        // Aquí devolvemos un objeto de respuesta de demo.
        LoginResponse response = new LoginResponse(
                "Login exitoso",
                request.getUsername(),
                "USER" // demo, en real se obtendría de DB o contexto
        );
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@RequestBody RegisterRequest request) {
        // En Basic Auth con usuarios en memoria no se guarda nada.
        // Aquí devolvemos un objeto de respuesta de demo.
        RegisterResponse response = new RegisterResponse(
                "Usuario registrado correctamente",
                request.getUsername(),
                request.getRole());
        return ResponseEntity.ok(response);
    }
}
