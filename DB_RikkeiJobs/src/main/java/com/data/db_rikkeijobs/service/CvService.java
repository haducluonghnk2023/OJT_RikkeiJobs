package com.data.db_rikkeijobs.service;

import com.data.db_rikkeijobs.dto.request.CreateCvRequest;
import com.data.db_rikkeijobs.dto.request.UpdateCvRequest;
import com.data.db_rikkeijobs.dto.response.CvResponse;
import com.data.db_rikkeijobs.entity.Cv;

import java.util.List;
import java.util.Optional;

public interface CvService {
    List<Cv> findAll();
    Optional<Cv> findById(Long id);
    List<Cv> findByUserId(Long userId);
    List<Cv> findByLanguageId(Long languageId);
    List<Cv> findByStatus(Boolean status);
    Cv save(Cv cv);
    Cv update(Long id, Cv cv);
    void deleteById(Long id);

    List<CvResponse> getAllCvResponses();
    CvResponse getCvResponseById(Long id);
    CvResponse createCv(CreateCvRequest request);
    CvResponse patchCv(Long id, UpdateCvRequest request);
    void deleteCvOrThrow(Long id);
}

