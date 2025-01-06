package com.kokuu.edukaizen.handlers;

import java.util.List;

import com.kokuu.edukaizen.dto.PaginatedResult.Paginator;

public record PaginatedResponseHandler(List<?> data, Paginator paginate) {
}
