package com.kokuu.edukaizen.services.masters.category;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.kokuu.edukaizen.dao.masters.CategoryRepository;
import com.kokuu.edukaizen.dto.PaginatedResultDTO;
import com.kokuu.edukaizen.dto.masters.category.IndexCategoryDTO;
import com.kokuu.edukaizen.dto.masters.category.StoreCategoryDTO;
import com.kokuu.edukaizen.dto.masters.category.UpdateCategoryDTO;
import com.kokuu.edukaizen.entities.masters.Category;
import com.kokuu.edukaizen.entities.masters.Subcategory;

import jakarta.transaction.Transactional;

@Service
public class CategoryServiceImpl implements CategoryService {
    private CategoryRepository categoryRepository;
    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public CategoryServiceImpl(CategoryRepository categoryRepository, JdbcTemplate jdbcTemplate,
            NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.categoryRepository = categoryRepository;
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
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

            return new PaginatedResultDTO<>(categoryRepository.findAll(spec, pageable));
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

        if (input.subcategories() != null && input.subcategories().length > 0) {
            List<Subcategory> subcategories = Arrays.stream(input.subcategories())
                    .map(name -> new Subcategory(name, category))
                    .toList();

            category.setSubcategories(subcategories);
        }

        categoryRepository.save(category);
    }

    @Override
    @Transactional
    public void updateCategory(Category category, UpdateCategoryDTO input) {
        if (input.name() != null) {
            category.setName(input.name());
        }

        categoryRepository.save(category);

        if (input.deleteSubcategoryIds() != null && input.deleteSubcategoryIds().length > 0) {
            String sql = """
                    DELETE FROM master_subcategories
                    WHERE id IN (:ids)
                    """;

            MapSqlParameterSource params = new MapSqlParameterSource();
            params.addValue("ids", Arrays.asList(input.deleteSubcategoryIds()));

            namedParameterJdbcTemplate.update(sql, params);
        }

        if (input.subcategories() != null && input.subcategories().length > 0) {
            String sql = """
                    INSERT INTO master_subcategories (category_id, name)
                    SELECT ?, ?
                    WHERE NOT EXISTS(
                        SELECT 1 FROM master_subcategories WHERE category_id = ? AND name = ?
                    )
                    """;

            jdbcTemplate.batchUpdate(sql, Arrays.asList(input.subcategories()), input.subcategories().length,
                    (ps, data) -> {
                        ps.setInt(1, category.getId());
                        ps.setString(2, data);
                        ps.setInt(3, category.getId());
                        ps.setString(4, data);
                    });
        }
    }

    @Override
    public void deleteCategory(Category category) {
        categoryRepository.delete(category);
    }
}
