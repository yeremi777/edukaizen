package com.kokuu.edukaizen.services.masters.category;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.kokuu.edukaizen.dao.masters.CategoryRepository;
import com.kokuu.edukaizen.dto.masters.CategoryDTO;
import com.kokuu.edukaizen.entities.masters.Category;

import jakarta.transaction.Transactional;

@Service
public class CategoryServiceImpl implements CategoryService {
    private CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Category> getCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Optional<Category> getCategory(int id) {
        return categoryRepository.findById(id);
    }

    @Override
    @Transactional
    public void storeCategory(CategoryDTO input) {
        Category category = new Category(input.name());

        categoryRepository.save(category);
    }

    @Override
    @Transactional
    public void updateCategory(Category category, CategoryDTO input) {
        if (input.name() != null) {
            category.setName(input.name());
        }

        categoryRepository.save(category);
    }

    @Override
    public void deleteCategory(Category category) {
        categoryRepository.delete(category);
    }
}
