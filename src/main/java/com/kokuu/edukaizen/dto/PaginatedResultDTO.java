package com.kokuu.edukaizen.dto;

import java.util.List;

import org.springframework.data.domain.Page;

import lombok.Getter;

@Getter
public class PaginatedResultDTO<T> {
    private List<T> data;
    private Paginator paginate;

    public PaginatedResultDTO(Page<T> page) {
        this.data = page.getContent();
        this.paginate = new Paginator(page);
    }

    @Getter
    public static class Paginator {
        private Integer currentPage;
        private Integer lastPage;
        private Integer perPage;
        private Integer from;
        private Integer to;
        private Long total;

        public Paginator(Page<?> page) {
            this.currentPage = page.getNumber() + 1;
            this.lastPage = page.getTotalPages() != 0 ? page.getTotalPages() : 1;
            this.perPage = page.getSize();
            this.total = page.getTotalElements();

            if (this.currentPage <= this.lastPage) {
                if (this.total != 0) {
                    this.from = page.getNumber() * page.getSize() + 1;
                } else {
                    this.from = 0;
                }
            } else {
                this.from = null;
            }

            if (this.currentPage < this.lastPage) {
                this.to = (page.getNumber() + 1) * page.getSize();
            } else if (this.currentPage == this.lastPage) {
                this.to = Math.toIntExact(total);
            } else {
                this.to = null;
            }
        }
    }
}
