package com.kokuu.edukaizen.dao.masters;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.kokuu.edukaizen.entities.masters.Tool;

public interface ToolRepository extends JpaRepository<Tool, Integer>, JpaSpecificationExecutor<Tool> {
}
