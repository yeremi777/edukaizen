package com.kokuu.edukaizen.dto.masters.category;

public record IndexCategoryDTO(String keyword, Integer page, Integer per_page) {
    public IndexCategoryDTO() {
        this(null, null, null);
    }
}
