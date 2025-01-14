package com.kokuu.edukaizen.dto.masters.tool;

public record IndexToolDTO(String keyword, Integer page, Integer per_page) {
    public IndexToolDTO() {
        this(null, null, null);
    }
}
