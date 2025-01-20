package com.kokuu.edukaizen.handlers;

import java.util.List;

import com.kokuu.edukaizen.dto.PaginatedResultDTO.Paginator;

public record PaginatedResponseHandler(List<?> data, Paginator paginate) {
}
