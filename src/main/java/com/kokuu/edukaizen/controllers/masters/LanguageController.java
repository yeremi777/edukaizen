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
import com.kokuu.edukaizen.api_docs.LanguageApiDoc;
import com.kokuu.edukaizen.dto.PaginatedResultDTO;
import com.kokuu.edukaizen.dto.masters.language.IndexLanguageDTO;
import com.kokuu.edukaizen.dto.masters.language.StoreLanguageDTO;
import com.kokuu.edukaizen.entities.masters.Language;
import com.kokuu.edukaizen.handlers.PaginatedResponseHandler;
import com.kokuu.edukaizen.services.masters.language.LanguageService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/master/languages")
public class LanguageController implements LanguageApiDoc {
    private LanguageService languageService;

    public LanguageController(LanguageService languageService) {
        this.languageService = languageService;
    }

    @GetMapping
    public ResponseEntity<Object> index(@RequestParam Map<String, String> query) {
        String keyword = query.get("keyword");
        Integer page = query.containsKey("page") ? Integer.valueOf(query.get("page")) : null;
        Integer perPage = query.containsKey("per_page") ? Integer.valueOf(query.get("per_page")) : null;

        IndexLanguageDTO indexLanguageDTO = new IndexLanguageDTO(keyword, page, perPage);

        Object result = languageService.getLanguages(indexLanguageDTO);

        if (result instanceof PaginatedResultDTO<?>) {
            PaginatedResultDTO<?> paginatedResult = (PaginatedResultDTO<?>) result;

            PaginatedResponseHandler response = new PaginatedResponseHandler(
                    paginatedResult.getData(),
                    paginatedResult.getPaginate());

            return ResponseEntity.ok(response);
        }

        return ResponseEntity.ok(result);
    }

    @PostMapping
    public ResponseEntity<TextNode> store(@Valid @RequestBody StoreLanguageDTO input) {
        languageService.storeLanguage(input);

        return ResponseEntity.status(HttpStatus.CREATED).body(TextNode.valueOf("success"));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<TextNode> update(@PathVariable int id, @RequestBody StoreLanguageDTO input) {
        Optional<Language> language = languageService.getLanguage(id);

        if (language.isPresent()) {
            languageService.updateLanguage(language.get(), input);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(TextNode.valueOf("not found"));
        }

        return ResponseEntity.status(HttpStatus.OK).body(TextNode.valueOf("success"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<TextNode> delete(@PathVariable int id) {
        Optional<Language> language = languageService.getLanguage(id);

        if (language.isPresent()) {
            languageService.deleteLanguage(language.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(TextNode.valueOf("not found"));
        }

        return ResponseEntity.status(HttpStatus.OK).body(TextNode.valueOf("success"));
    }
}
