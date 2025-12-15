package com.data.db_rikkeijobs.service;

import com.data.db_rikkeijobs.entity.CvLanguage;

import java.util.List;
import java.util.Optional;

public interface CvLanguageService {
    List<CvLanguage> findAll();
    Optional<CvLanguage> findById(Long id);
    List<CvLanguage> findByStatus(Boolean status);
    CvLanguage save(CvLanguage cvLanguage);
    CvLanguage update(Long id, CvLanguage cvLanguage);
    void deleteById(Long id);
}

