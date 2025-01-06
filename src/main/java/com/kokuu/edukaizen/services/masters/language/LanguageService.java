package com.kokuu.edukaizen.services.masters.language;

import java.util.Optional;

import com.kokuu.edukaizen.dto.masters.language.IndexLanguageDTO;
import com.kokuu.edukaizen.dto.masters.language.StoreLanguageDTO;
import com.kokuu.edukaizen.entities.masters.Language;

public interface LanguageService {
    Object getLanguages(IndexLanguageDTO indexLanguageDTO);

    Optional<Language> getLanguage(int id);

    void storeLanguage(StoreLanguageDTO input);

    void updateLanguage(Language language, StoreLanguageDTO input);

    void deleteLanguage(Language language);
}
