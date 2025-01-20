package com.kokuu.edukaizen.controllers.masters;

import java.util.Map;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.node.TextNode;
import com.kokuu.edukaizen.api_docs.ToolApiDoc;
import com.kokuu.edukaizen.dto.PaginatedResultDTO;
import com.kokuu.edukaizen.dto.masters.tool.IndexToolDTO;
import com.kokuu.edukaizen.dto.masters.tool.StoreToolDTO;
import com.kokuu.edukaizen.entities.masters.Tool;
import com.kokuu.edukaizen.handlers.PaginatedResponseHandler;
import com.kokuu.edukaizen.services.masters.tool.ToolService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/master/tools")
public class ToolController implements ToolApiDoc {
    private ToolService toolService;

    public ToolController(ToolService toolService) {
        this.toolService = toolService;
    }

    @Override
    @GetMapping
    public ResponseEntity<Object> index(@RequestParam Map<String, String> query) {
        String keyword = query.get("keyword");
        Integer page = query.containsKey("page") ? Integer.valueOf(query.get("page")) : null;
        Integer perPage = query.containsKey("per_page") ? Integer.valueOf(query.get("per_page")) : null;

        IndexToolDTO indexToolDTO = new IndexToolDTO(keyword, page, perPage);

        Object result = toolService.getTools(indexToolDTO);

        if (result instanceof PaginatedResultDTO<?>) {
            PaginatedResultDTO<?> paginatedResult = (PaginatedResultDTO<?>) result;

            PaginatedResponseHandler response = new PaginatedResponseHandler(
                    paginatedResult.getData(),
                    paginatedResult.getPaginate());

            return ResponseEntity.ok(response);
        }

        return ResponseEntity.ok(result);
    }

    @Override
    @PostMapping
    public ResponseEntity<TextNode> store(@Valid @RequestBody StoreToolDTO input) {
        toolService.storeTool(input);

        return ResponseEntity.status(HttpStatus.CREATED).body(TextNode.valueOf("success"));
    }

    @Override
    @PatchMapping("/{id}")
    public ResponseEntity<TextNode> update(@PathVariable int id, @RequestBody StoreToolDTO input) {
        Optional<Tool> tool = toolService.getTool(id);

        if (tool.isPresent()) {
            toolService.updateTool(tool.get(), input);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(TextNode.valueOf("not found"));
        }

        return ResponseEntity.status(HttpStatus.OK).body(TextNode.valueOf("success"));
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<TextNode> delete(@PathVariable int id) {
        Optional<Tool> tool = toolService.getTool(id);

        if (tool.isPresent()) {
            toolService.deleteTool(tool.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(TextNode.valueOf("not found"));
        }

        return ResponseEntity.status(HttpStatus.OK).body(TextNode.valueOf("success"));
    }
}
