package com.kokuu.edukaizen.api_docs;

import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;

import com.fasterxml.jackson.databind.node.TextNode;
import com.kokuu.edukaizen.dto.masters.category.StoreCategoryDTO;
import com.kokuu.edukaizen.dto.masters.category.UpdateCategoryDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Categories")
public interface CategoryApiDoc {
    @Operation(operationId = "index-categories", summary = "Index Categories", description = "Get categories data", parameters = {
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
                                        "name": "Category",
                                        "subcategories": [],
                                        "created_at": "1970-01-01T00:00:00.000000Z",
                                        "updated_at": "1970-01-01T00:00:00.000000Z"
                                    }]
                                    """),
                            @ExampleObject(name = "With pagination response", value = """
                                    {
                                        "data": [{
                                            "id": 0,
                                            "name": "Category",
                                            "subcategories": [],
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
    ResponseEntity<Object> index(@Parameter(hidden = true) MultiValueMap<String, String> query);

    @Operation(operationId = "insert-category", summary = "Insert Category", description = "Add a category")
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
    ResponseEntity<TextNode> store(StoreCategoryDTO input);

    @Operation(operationId = "update-category", summary = "Update Category", description = "Edit a category")
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
    ResponseEntity<TextNode> update(int id, UpdateCategoryDTO input);

    @Operation(operationId = "delete-category", summary = "Delete Category", description = "Delete a category")
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
