package com.data.db_rikkeijobs.repository;

import com.data.db_rikkeijobs.entity.Cv;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CvRepository extends JpaRepository<Cv, Long> {
    List<Cv> findByUserId(Long userId);
    List<Cv> findByLanguageId(Long languageId);
    List<Cv> findByStatus(Boolean status);
}

