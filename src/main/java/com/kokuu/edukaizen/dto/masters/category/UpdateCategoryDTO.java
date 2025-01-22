package com.kokuu.edukaizen.dto.masters.category;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record UpdateCategoryDTO(String name, String[] subcategories, Integer[] deleteSubcategoryIds) {
}