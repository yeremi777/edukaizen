package com.kokuu.edukaizen.services.masters.tool;

import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.kokuu.edukaizen.dao.masters.ToolRepository;
import com.kokuu.edukaizen.dto.PaginatedResult;
import com.kokuu.edukaizen.dto.masters.tool.IndexToolDTO;
import com.kokuu.edukaizen.dto.masters.tool.StoreToolDTO;
import com.kokuu.edukaizen.entities.masters.Tool;

import jakarta.transaction.Transactional;

@Service
public class ToolServiceImpl implements ToolService {
    private ToolRepository toolRepository;

    public ToolServiceImpl(ToolRepository toolRepository) {
        this.toolRepository = toolRepository;
    }

    @Override
    public Object getTools(IndexToolDTO indexToolDTO) {
        Specification<Tool> spec = Specification.where(null);

        if (StringUtils.hasText(indexToolDTO.keyword())) {
            spec = spec.and((root, query, criteriaBuilder) -> criteriaBuilder.like(
                    criteriaBuilder.lower(root.get("name")),
                    "%" + indexToolDTO.keyword().toLowerCase() + "%"));
        }

        Pageable pageable = Pageable.unpaged();

        if (indexToolDTO.page() != null || indexToolDTO.per_page() != null) {
            Integer page = indexToolDTO.page() != null ? indexToolDTO.page() : 1;
            Integer perPage = indexToolDTO.per_page() != null ? indexToolDTO.per_page() : 10;

            pageable = Pageable.ofSize(perPage).withPage(page - 1);

            return new PaginatedResult<>(toolRepository.findAll(spec, pageable));
        }

        return toolRepository.findAll(spec);
    }

    @Override
    public Optional<Tool> getTool(int id) {
        return toolRepository.findById(id);
    }

    @Override
    @Transactional
    public void storeTool(StoreToolDTO input) {
        Tool tool = new Tool(input.name());

        toolRepository.save(tool);
    }

    @Override
    @Transactional
    public void updateTool(Tool tool, StoreToolDTO input) {
        if (input.name() != null) {
            tool.setName(input.name());
        }

        toolRepository.save(tool);
    }

    @Override
    public void deleteTool(Tool tool) {
        toolRepository.delete(tool);
    }
}
