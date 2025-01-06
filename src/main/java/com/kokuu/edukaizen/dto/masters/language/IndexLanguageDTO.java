package com.kokuu.edukaizen.dto.masters.language;

public record IndexLanguageDTO(String keyword, Integer page, Integer perPage) {
    public IndexLanguageDTO() {
        this(null, null, null);
    }
}
