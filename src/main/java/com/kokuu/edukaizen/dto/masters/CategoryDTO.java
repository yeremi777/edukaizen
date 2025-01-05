package com.kokuu.edukaizen.dto.masters;

import jakarta.validation.constraints.NotBlank;

public record CategoryDTO(@NotBlank String name) {
}
