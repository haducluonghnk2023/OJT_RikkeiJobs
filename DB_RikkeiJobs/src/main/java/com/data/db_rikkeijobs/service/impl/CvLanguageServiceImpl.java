package com.data.db_rikkeijobs.service.impl;

import com.data.db_rikkeijobs.dto.request.UpdateCvLanguageRequest;
import com.data.db_rikkeijobs.entity.CvLanguage;
import com.data.db_rikkeijobs.exception.HttpNotFound;
import com.data.db_rikkeijobs.repository.CvLanguageRepository;
import com.data.db_rikkeijobs.service.CvLanguageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CvLanguageServiceImpl implements CvLanguageService {
    
    @Autowired
    private CvLanguageRepository cvLanguageRepository;
    
    @Override
    public List<CvLanguage> findAll() {
        return cvLanguageRepository.findAll();
    }
    
    @Override
    public Optional<CvLanguage> findById(Long id) {
        return cvLanguageRepository.findById(id);
    }
    
    @Override
    public List<CvLanguage> findByStatus(Boolean status) {
        return cvLanguageRepository.findByStatus(status);
    }
    
    @Override
    public CvLanguage save(CvLanguage cvLanguage) {
        return cvLanguageRepository.save(cvLanguage);
    }
    
    @Override
    public CvLanguage update(Long id, CvLanguage cvLanguage) {
        Optional<CvLanguage> existing = cvLanguageRepository.findById(id);
        if (existing.isPresent()) {
            CvLanguage toUpdate = existing.get();
            toUpdate.setLanguage(cvLanguage.getLanguage());
            toUpdate.setCode(cvLanguage.getCode());
            toUpdate.setStatus(cvLanguage.getStatus());
            return cvLanguageRepository.save(toUpdate);
        }
        throw new RuntimeException("CvLanguage not found with id: " + id);
    }
    
    @Override
    public void deleteById(Long id) {
        cvLanguageRepository.deleteById(id);
    }

    @Override
    public CvLanguage getCvLanguageByIdOrThrow(Long id) {
        return cvLanguageRepository.findById(id)
                .orElseThrow(() -> new HttpNotFound("CV language not found with id: " + id));
    }

    @Override
    public CvLanguage updateCvLanguage(Long id, UpdateCvLanguageRequest request) {
        CvLanguage existing = getCvLanguageByIdOrThrow(id);
        if (request.getLanguage() != null) existing.setLanguage(request.getLanguage());
        if (request.getCode() != null) existing.setCode(request.getCode());
        if (request.getStatus() != null) existing.setStatus(request.getStatus());
        return update(id, existing);
    }

    @Override
    public void deleteCvLanguageOrThrow(Long id) {
        if (!cvLanguageRepository.existsById(id)) {
            throw new HttpNotFound("CV language not found with id: " + id);
        }
        cvLanguageRepository.deleteById(id);
    }
}

