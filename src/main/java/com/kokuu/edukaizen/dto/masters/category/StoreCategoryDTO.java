package com.kokuu.edukaizen.dto.masters.category;

import jakarta.validation.constraints.NotBlank;

public record StoreCategoryDTO(@NotBlank String name) {
}
