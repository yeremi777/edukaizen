package com.kokuu.edukaizen.controllers;

import java.time.Instant;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.kokuu.edukaizen.common.JWT;
import com.kokuu.edukaizen.common.Password;
import com.kokuu.edukaizen.dto.LoginDTO;
import com.kokuu.edukaizen.dto.RegisterDTO;
import com.kokuu.edukaizen.dto.UserDTO;
import com.kokuu.edukaizen.entities.User;
import com.kokuu.edukaizen.services.user.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private static record AuthResponse(
            boolean success,
            String message,
            @JsonInclude(JsonInclude.Include.NON_NULL) String token,
            @JsonInclude(JsonInclude.Include.NON_NULL) Instant expiresAt,
            @JsonInclude(JsonInclude.Include.NON_NULL) UserDTO user) {
        public AuthResponse(boolean success, String message) {
            this(success, message, null, null, null);
        }
    }

    private final UserService authService;
    private final Password password;
    private final JWT jwt;
    private final ModelMapper modelMapper;

    public AuthController(UserService authService, Password password, JWT jwt, ModelMapper modelMapper) {
        this.authService = authService;
        this.password = password;
        this.jwt = jwt;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterDTO input) {
        User user = new User(
                input.firstName(),
                input.lastName(),
                input.username(),
                input.email(),
                input.password());

        authService.save(user);

        user = authService.findUserByIdentity(user.getUsername(), user.getEmail());

        String token = jwt.signToken((long) user.getId(), user.getEmail());
        UserDTO userOutput = modelMapper.map(user, UserDTO.class);

        return ResponseEntity.ok()
                .body(new AuthResponse(
                        true,
                        "Register successfully",
                        token,
                        jwt.extractExpiration(token).toInstant(),
                        userOutput));
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

        String token = jwt.signToken((long) user.getId(), user.getEmail());
        UserDTO userOutput = modelMapper.map(user, UserDTO.class);

        return ResponseEntity.ok()
                .body(new AuthResponse(
                        true,
                        "Login successfully",
                        token,
                        jwt.extractExpiration(token).toInstant(),
                        userOutput));
    }
}
