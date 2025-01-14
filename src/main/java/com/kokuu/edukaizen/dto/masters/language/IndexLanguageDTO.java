package com.kokuu.edukaizen.dto.masters.language;

public record IndexLanguageDTO(String keyword, Integer page, Integer per_page) {
    public IndexLanguageDTO() {
        this(null, null, null);
    }
}
