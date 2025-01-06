package com.kokuu.edukaizen.controllers.masters;

import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.node.TextNode;
import com.kokuu.edukaizen.dto.PaginatedResult;
import com.kokuu.edukaizen.dto.masters.category.IndexCategoryDTO;
import com.kokuu.edukaizen.dto.masters.category.StoreCategoryDTO;
import com.kokuu.edukaizen.entities.masters.Category;
import com.kokuu.edukaizen.handlers.PaginatedResponseHandler;
import com.kokuu.edukaizen.services.masters.category.CategoryService;

import jakarta.validation.Valid;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/master/categories")
public class CategoryController {
    private CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public ResponseEntity<Object> index(@RequestParam MultiValueMap<String, String> query) {
        String keyword = query.getFirst("keyword");
        Integer page = query.containsKey("page") ? Integer.valueOf(query.getFirst("page")) : null;
        Integer perPage = query.containsKey("per_page") ? Integer.valueOf(query.getFirst("per_page")) : null;

        IndexCategoryDTO indexCategoryDTO = new IndexCategoryDTO(keyword, page, perPage);

        Object result = categoryService.getCategories(indexCategoryDTO);

        if (result instanceof PaginatedResult<?>) {
            PaginatedResult<?> paginatedResult = (PaginatedResult<?>) result;

            PaginatedResponseHandler response = new PaginatedResponseHandler(
                    paginatedResult.getData(),
                    paginatedResult.getPaginate());

            return ResponseEntity.ok(response);
        }

        return ResponseEntity.ok(result);
    }

    @PostMapping
    public ResponseEntity<TextNode> store(@Valid @RequestBody StoreCategoryDTO input) {
        categoryService.storeCategory(input);

        return ResponseEntity.status(HttpStatus.CREATED).body(TextNode.valueOf("success"));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<TextNode> update(@PathVariable int id, @RequestBody StoreCategoryDTO input) {
        Optional<Category> category = categoryService.getCategory(id);

        if (category.isPresent()) {
            categoryService.updateCategory(category.get(), input);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(TextNode.valueOf("not found"));
        }

        return ResponseEntity.status(HttpStatus.OK).body(TextNode.valueOf("success"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<TextNode> delete(@PathVariable int id) {
        Optional<Category> category = categoryService.getCategory(id);

        if (category.isPresent()) {
            categoryService.deleteCategory(category.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(TextNode.valueOf("not found"));
        }

        return ResponseEntity.status(HttpStatus.OK).body(TextNode.valueOf("success"));
    }
}
