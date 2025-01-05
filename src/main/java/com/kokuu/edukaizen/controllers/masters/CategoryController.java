package com.kokuu.edukaizen.controllers.masters;

import org.springframework.web.bind.annotation.RestController;

import com.kokuu.edukaizen.dto.masters.CategoryDTO;
import com.kokuu.edukaizen.entities.masters.Category;
import com.kokuu.edukaizen.services.masters.category.CategoryService;

import jakarta.validation.Valid;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/master/categories")
public class CategoryController {
    private CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public List<Category> index() {
        return categoryService.getCategories();
    }

    @PostMapping
    public ResponseEntity<String> store(@Valid @RequestBody CategoryDTO input) {
        categoryService.storeCategory(input);

        return ResponseEntity.status(HttpStatus.CREATED).body("success");
    }

    @PatchMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable int id, @RequestBody CategoryDTO input) {
        Optional<Category> category = categoryService.getCategory(id);

        if (category.isPresent()) {
            categoryService.updateCategory(category.get(), input);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("not found");
        }

        return ResponseEntity.status(HttpStatus.OK).body("success");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable int id) {
        Optional<Category> category = categoryService.getCategory(id);

        if (category.isPresent()) {
            categoryService.deleteCategory(category.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("not found");
        }

        return ResponseEntity.status(HttpStatus.OK).body("success");
    }
}
