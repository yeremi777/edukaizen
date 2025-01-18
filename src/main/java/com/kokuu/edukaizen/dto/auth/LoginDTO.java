package com.kokuu.edukaizen.dto.auth;

import jakarta.validation.constraints.NotBlank;

public record LoginDTO(
        @NotBlank String username,
        @NotBlank String password) {
}
