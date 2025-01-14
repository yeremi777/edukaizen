package com.kokuu.edukaizen.dto;

import java.util.List;

import org.springframework.data.domain.Page;

import lombok.Getter;

@Getter
public class PaginatedResult<T> {
    private List<T> data;
    private Paginator paginate;

    public PaginatedResult(Page<T> page) {
        this.data = page.getContent();
        this.paginate = new Paginator(page);
    }

    @Getter
    public static class Paginator {
        private Integer current_page;
        private Integer last_page;
        private Integer per_page;
        private Integer from;
        private Integer to;
        private Long total;

        public Paginator(Page<?> page) {
            this.current_page = page.getNumber() + 1;
            this.last_page = page.getTotalPages() != 0 ? page.getTotalPages() : 1;
            this.per_page = page.getSize();
            this.total = page.getTotalElements();

            if (this.current_page <= this.last_page) {
                if (this.total != 0) {
                    this.from = page.getNumber() * page.getSize() + 1;
                } else {
                    this.from = 0;
                }
            } else {
                this.from = null;
            }

            if (this.current_page < this.last_page) {
                this.to = (page.getNumber() + 1) * page.getSize();
            } else if (this.current_page == this.last_page) {
                this.to = Math.toIntExact(total);
            } else {
                this.to = null;
            }
        }
    }
}
