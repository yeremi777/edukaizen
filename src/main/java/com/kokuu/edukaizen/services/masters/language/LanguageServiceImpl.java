package com.kokuu.edukaizen.services.masters.language;

import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.kokuu.edukaizen.dao.masters.LanguageRepository;
import com.kokuu.edukaizen.dto.PaginatedResult;
import com.kokuu.edukaizen.dto.masters.language.IndexLanguageDTO;
import com.kokuu.edukaizen.dto.masters.language.StoreLanguageDTO;
import com.kokuu.edukaizen.entities.masters.Language;

import jakarta.transaction.Transactional;

@Service
public class LanguageServiceImpl implements LanguageService {
    private LanguageRepository languageRepository;

    public LanguageServiceImpl(LanguageRepository languageRepository) {
        this.languageRepository = languageRepository;
    }

    @Override
    public Object getLanguages(IndexLanguageDTO indexLanguageDTO) {
        Specification<Language> spec = Specification.where(null);

        if (StringUtils.hasText(indexLanguageDTO.keyword())) {
            spec = spec.and((root, query, criteriaBuilder) -> criteriaBuilder.like(
                    criteriaBuilder.lower(root.get("name")),
                    "%" + indexLanguageDTO.keyword().toLowerCase() + "%"));
        }

        Pageable pageable = Pageable.unpaged();

        if (indexLanguageDTO.page() != null || indexLanguageDTO.per_page() != null) {
            Integer page = indexLanguageDTO.page() != null ? indexLanguageDTO.page() : 1;
            Integer perPage = indexLanguageDTO.per_page() != null ? indexLanguageDTO.per_page() : 10;

            pageable = Pageable.ofSize(perPage).withPage(page - 1);

            return new PaginatedResult<>(languageRepository.findAll(spec, pageable));
        }

        return languageRepository.findAll(spec);
    }

    @Override
    public Optional<Language> getLanguage(int id) {
        return languageRepository.findById(id);
    }

    @Override
    @Transactional
    public void storeLanguage(StoreLanguageDTO input) {
        Language language = new Language(input.name());

        languageRepository.save(language);
    }

    @Override
    @Transactional
    public void updateLanguage(Language language, StoreLanguageDTO input) {
        if (input.name() != null) {
            language.setName(input.name());
        }

        languageRepository.save(language);
    }

    @Override
    public void deleteLanguage(Language language) {
        languageRepository.delete(language);
    }
}
