package com.data.db_rikkeijobs.repository;

import com.data.db_rikkeijobs.entity.CvLanguage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CvLanguageRepository extends JpaRepository<CvLanguage, Long> {
    List<CvLanguage> findByStatus(Boolean status);
}

