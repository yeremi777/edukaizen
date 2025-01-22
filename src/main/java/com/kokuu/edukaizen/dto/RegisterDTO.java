package com.kokuu.edukaizen.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.kokuu.edukaizen.common.annotations.IsEqualTo;

import jakarta.validation.constraints.NotBlank;

@IsEqualTo(field = "password", fieldToMatch = "passwordConfirmation", message = "passwords does not match")
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record RegisterDTO(
        @NotBlank String firstName,
        @NotBlank String lastName,
        @NotBlank String username,
        @NotBlank String email,
        @NotBlank String password,
        @NotBlank String passwordConfirmation) {
}
