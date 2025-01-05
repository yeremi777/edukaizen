package com.kokuu.edukaizen.dao.masters;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kokuu.edukaizen.entities.masters.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
