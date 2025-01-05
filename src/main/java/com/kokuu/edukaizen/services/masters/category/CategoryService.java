package com.kokuu.edukaizen.services.masters.category;

import java.util.List;
import java.util.Optional;

import com.kokuu.edukaizen.dto.masters.CategoryDTO;
import com.kokuu.edukaizen.entities.masters.Category;

public interface CategoryService {
    List<Category> getCategories();

    Optional<Category> getCategory(int id);

    void storeCategory(CategoryDTO input);

    void updateCategory(Category category, CategoryDTO input);

    void deleteCategory(Category category);
}
