package com.kokuu.edukaizen.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kokuu.edukaizen.common.Password;
import com.kokuu.edukaizen.dto.auth.LoginDTO;
import com.kokuu.edukaizen.dto.auth.RegisterDTO;
import com.kokuu.edukaizen.entities.User;
import com.kokuu.edukaizen.services.auth.AuthService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private static record AuthResponse(boolean success, String message) {
    }

    private final AuthService authService;
    private final Password password;

    public AuthController(AuthService authService, Password password) {
        this.authService = authService;
        this.password = password;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterDTO input) {
        authService.register(input);

        return ResponseEntity.ok().body(new AuthResponse(true, "Register successfully"));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginDTO input) {
        User user = authService.findUserByIdentity(input.username());

        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new AuthResponse(false, "Login failed"));
        }

        if (!(password.verifyPassword(input.password(), user.getPassword()))) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new AuthResponse(false, "Login failed"));
        }

        return ResponseEntity.ok().body(new AuthResponse(true, "Login successfully"));
    }
}
