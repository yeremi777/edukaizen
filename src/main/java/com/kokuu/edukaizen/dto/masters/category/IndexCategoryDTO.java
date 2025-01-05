package com.kokuu.edukaizen.dto.masters.category;

public record IndexCategoryDTO(String keyword, Integer page, Integer perPage) {
    public IndexCategoryDTO() {
        this(null, null, null);
    }
}
