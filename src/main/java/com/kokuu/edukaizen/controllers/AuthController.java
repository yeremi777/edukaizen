package com.kokuu.edukaizen.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kokuu.edukaizen.dto.auth.RegisterDTO;
import com.kokuu.edukaizen.services.auth.AuthService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private static record AuthResponse(String success, String message) {
    }

    private AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterDTO input) {
        authService.register(input);

        return ResponseEntity.ok().body(new AuthResponse("success", "Register successfully"));
    }
}
