package com.kokuu.edukaizen.services.masters.category;

import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.kokuu.edukaizen.dao.masters.CategoryRepository;
import com.kokuu.edukaizen.dto.PaginatedResult;
import com.kokuu.edukaizen.dto.masters.category.IndexCategoryDTO;
import com.kokuu.edukaizen.dto.masters.category.StoreCategoryDTO;
import com.kokuu.edukaizen.entities.masters.Category;

import jakarta.transaction.Transactional;

@Service
public class CategoryServiceImpl implements CategoryService {
    private CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Object getCategories(IndexCategoryDTO indexCategoryDTO) {
        Specification<Category> spec = Specification.where(null);

        if (StringUtils.hasText(indexCategoryDTO.keyword())) {
            spec = spec.and((root, query, criteriaBuilder) -> criteriaBuilder.like(
                    criteriaBuilder.lower(root.get("name")),
                    "%" + indexCategoryDTO.keyword().toLowerCase() + "%"));
        }

        Pageable pageable = Pageable.unpaged();

        if (indexCategoryDTO.page() != null || indexCategoryDTO.perPage() != null) {
            Integer page = indexCategoryDTO.page() != null ? indexCategoryDTO.page() : 1;
            Integer perPage = indexCategoryDTO.perPage() != null ? indexCategoryDTO.perPage() : 10;

            pageable = Pageable.ofSize(perPage).withPage(page - 1);

            return new PaginatedResult<>(categoryRepository.findAll(spec, pageable));
        }

        return categoryRepository.findAll(spec);
    }

    @Override
    public Optional<Category> getCategory(int id) {
        return categoryRepository.findById(id);
    }

    @Override
    @Transactional
    public void storeCategory(StoreCategoryDTO input) {
        Category category = new Category(input.name());

        categoryRepository.save(category);
    }

    @Override
    @Transactional
    public void updateCategory(Category category, StoreCategoryDTO input) {
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
