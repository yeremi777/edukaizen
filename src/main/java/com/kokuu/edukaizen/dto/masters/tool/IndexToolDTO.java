package com.kokuu.edukaizen.dto.masters.tool;

public record IndexToolDTO(String keyword, Integer page, Integer perPage) {
    public IndexToolDTO() {
        this(null, null, null);
    }
}
