package com.kokuu.edukaizen.dto.masters.category;

public record UpdateCategoryDTO(String name, String[] subcategories, Integer[] deleteSubcategoryIds) {
}