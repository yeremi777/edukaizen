package com.kokuu.edukaizen.services.masters.category;

import java.util.Optional;

import com.kokuu.edukaizen.dto.masters.category.IndexCategoryDTO;
import com.kokuu.edukaizen.dto.masters.category.StoreCategoryDTO;
import com.kokuu.edukaizen.dto.masters.category.UpdateCategoryDTO;
import com.kokuu.edukaizen.entities.masters.Category;

public interface CategoryService {
    Object getCategories(IndexCategoryDTO indexCategoryDTO);

    Optional<Category> getCategory(int id);

    void storeCategory(StoreCategoryDTO input);

    void updateCategory(Category category, UpdateCategoryDTO input);

    void deleteCategory(Category category);
}
