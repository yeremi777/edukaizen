package com.kokuu.edukaizen.api_docs;

import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.databind.node.TextNode;
import com.kokuu.edukaizen.dto.masters.tool.StoreToolDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Tools")
public interface ToolApiDoc {
	@Operation(operationId = "index-tools", summary = "Index Tools", description = "Get tools data", parameters = {
			@Parameter(name = "keyword", in = ParameterIn.QUERY, required = false, schema = @Schema(type = "string")),
			@Parameter(name = "page", in = ParameterIn.QUERY, required = false, schema = @Schema(type = "number")),
			@Parameter(name = "per_page", in = ParameterIn.QUERY, required = false, schema = @Schema(type = "number"))
	})
	@ApiResponses({
			@ApiResponse(responseCode = "200", content = {
					@Content(schema = @Schema(type = "array"), mediaType = "application/json", examples = {
							@ExampleObject(name = "Without pagination response", value = """
									[{
									    "id": 0,
									    "name": "Tool",
									    "created_at": "1970-01-01T00:00:00.000000Z",
									    "updated_at": "1970-01-01T00:00:00.000000Z"
									}]
									"""),
							@ExampleObject(name = "With pagination response", value = """
									{
									    "data": [{
									        "id": 0,
									        "name": "Tool",
									        "created_at": "1970-01-01T00:00:00.000000Z",
									        "updated_at": "1970-01-01T00:00:00.000000Z"
									    }],
									    "paginate": {
									        "current_page": 0,
									        "last_page": 0,
									        "per_page": 0,
									        "from": 0,
									        "to": 0,
									        "total": 0
									    }
									}
									""")
					}) })
	})
	ResponseEntity<Object> index(@Parameter(hidden = true) Map<String, String> query);

	@Operation(operationId = "insert-tool", summary = "Insert Tool", description = "Add a tool")
	@ApiResponses({
			@ApiResponse(responseCode = "201", content = {
					@Content(schema = @Schema(type = "string", example = "success"), mediaType = "application/json") }),
			@ApiResponse(responseCode = "422", description = "Validation Error", content = {
					@Content(schema = @Schema(type = "string", example = """
							["required", "required", "..."]
							"""), mediaType = "application/json") }),
			@ApiResponse(responseCode = "500", content = {
					@Content(schema = @Schema(type = "string", example = "Internal server error"), mediaType = "application/json") })
	})
	ResponseEntity<TextNode> store(StoreToolDTO input);

	@Operation(operationId = "update-tool", summary = "Update Tool", description = "Edit a tool")
	@ApiResponses({
			@ApiResponse(responseCode = "200", content = {
					@Content(schema = @Schema(type = "string", example = "success"), mediaType = "application/json") }),
			@ApiResponse(responseCode = "404", content = {
					@Content(schema = @Schema(type = "string", example = "not found"), mediaType = "application/json") }),
			@ApiResponse(responseCode = "422", description = "Validation Error", content = {
					@Content(schema = @Schema(type = "string", example = """
							["required", "required", "..."]
							"""), mediaType = "application/json") }),
			@ApiResponse(responseCode = "500", content = {
					@Content(schema = @Schema(type = "string", example = "Internal server error"), mediaType = "application/json") })
	})
	ResponseEntity<TextNode> update(int id, StoreToolDTO input);

	@Operation(operationId = "delete-tool", summary = "Delete Tool", description = "Delete a tool")
	@ApiResponses({
			@ApiResponse(responseCode = "200", content = {
					@Content(schema = @Schema(type = "string", example = "success"), mediaType = "application/json") }),
			@ApiResponse(responseCode = "404", content = {
					@Content(schema = @Schema(type = "string", example = "not found"), mediaType = "application/json") }),
			@ApiResponse(responseCode = "500", content = {
					@Content(schema = @Schema(type = "string", example = "Internal server error"), mediaType = "application/json") })
	})
	ResponseEntity<TextNode> delete(int id);
}
