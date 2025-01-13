package com.kokuu.edukaizen.api_docs;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import com.fasterxml.jackson.databind.node.TextNode;
import com.kokuu.edukaizen.dto.masters.language.StoreLanguageDTO;
import com.kokuu.edukaizen.entities.masters.Language;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Languages")
public interface LanguageApiDoc {
	@Operation(operationId = "index-languages", summary = "Index Languages", description = "Get languages data", parameters = {
			@Parameter(name = "keyword", in = ParameterIn.QUERY, required = false, schema = @Schema(type = "string")),
			@Parameter(name = "page", in = ParameterIn.QUERY, required = false, schema = @Schema(type = "number")),
			@Parameter(name = "per_page", in = ParameterIn.QUERY, required = false, schema = @Schema(type = "number"))
	})
	@ApiResponses({
			@ApiResponse(responseCode = "200", content = {
					@Content(schema = @Schema(type = "array", implementation = Language.class), mediaType = "application/json", examples = {
							@ExampleObject(name = "Without pagination response", value = """
									[{
									    "id": 0,
									    "name": "Language",
									    "created_at": "1970-01-01T00:00:00.000000Z",
									    "updated_at": "1970-01-01T00:00:00.000000Z"
									}]
									"""),
							@ExampleObject(name = "With pagination response", value = """
									{
									    "data": [{
									        "id": 0,
									        "name": "Language",
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

	@Operation(operationId = "insert-language", summary = "Insert Language", description = "Add a language")
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
	ResponseEntity<TextNode> store(StoreLanguageDTO input);

	@Operation(operationId = "update-language", summary = "Update Language", description = "Edit a language")
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
	ResponseEntity<TextNode> update(int id, StoreLanguageDTO input);

	@Operation(operationId = "delete-language", summary = "Delete Language", description = "Delete a language")
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
