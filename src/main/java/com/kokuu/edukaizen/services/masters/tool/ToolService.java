package com.kokuu.edukaizen.services.masters.tool;

import java.util.Optional;

import com.kokuu.edukaizen.dto.masters.tool.IndexToolDTO;
import com.kokuu.edukaizen.dto.masters.tool.StoreToolDTO;
import com.kokuu.edukaizen.entities.masters.Tool;

public interface ToolService {
    Object getTools(IndexToolDTO indexToolDTO);

    Optional<Tool> getTool(int id);

    void storeTool(StoreToolDTO input);

    void updateTool(Tool tool, StoreToolDTO input);

    void deleteTool(Tool tool);
}
