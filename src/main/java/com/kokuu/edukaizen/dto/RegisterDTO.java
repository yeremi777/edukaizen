package com.kokuu.edukaizen.dto;

import com.kokuu.edukaizen.common.annotations.IsEqualTo;

import jakarta.validation.constraints.NotBlank;

@IsEqualTo(field = "password", fieldToMatch = "passwordConfirmation", message = "passwords does not match")
public record RegisterDTO(
        @NotBlank String firstName,
        @NotBlank String lastName,
        @NotBlank String username,
        @NotBlank String email,
        @NotBlank String password,
        @NotBlank String passwordConfirmation) {
}
