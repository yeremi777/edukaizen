package com.kokuu.edukaizen.api_docs;

import org.springframework.http.ResponseEntity;

import com.kokuu.edukaizen.dto.LoginDTO;
import com.kokuu.edukaizen.dto.RegisterDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Authentication")
public interface AuthApiDoc {
    @Operation(operationId = "register", summary = "Authenticate User", description = "API for register")
    ResponseEntity<?> register(RegisterDTO input);

    @Operation(operationId = "login", summary = "Authenticate User", description = "API for login")
    ResponseEntity<?> login(LoginDTO input);
}
