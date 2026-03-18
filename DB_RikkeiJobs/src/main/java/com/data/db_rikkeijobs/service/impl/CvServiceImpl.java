package com.data.db_rikkeijobs.service.impl;

import com.data.db_rikkeijobs.dto.request.CreateCvRequest;
import com.data.db_rikkeijobs.dto.request.UpdateCvRequest;
import com.data.db_rikkeijobs.dto.response.CvResponse;
import com.data.db_rikkeijobs.entity.Cv;
import com.data.db_rikkeijobs.exception.HttpNotFound;
import com.data.db_rikkeijobs.mapper.CvMapper;
import com.data.db_rikkeijobs.repository.CvRepository;
import com.data.db_rikkeijobs.service.CvService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class CvServiceImpl implements CvService {
    
    @Autowired
    private CvRepository cvRepository;

    @Autowired
    private CvMapper cvMapper;
    
    @Override
    public List<Cv> findAll() {
        return cvRepository.findAll();
    }
    
    @Override
    public Optional<Cv> findById(Long id) {
        return cvRepository.findById(id);
    }
    
    @Override
    public List<Cv> findByUserId(Long userId) {
        return cvRepository.findByUserId(userId);
    }
    
    @Override
    public List<Cv> findByLanguageId(Long languageId) {
        return cvRepository.findByLanguageId(languageId);
    }
    
    @Override
    public List<Cv> findByStatus(Boolean status) {
        return cvRepository.findByStatus(status);
    }
    
    @Override
    public Cv save(Cv cv) {
        return cvRepository.save(cv);
    }
    
    @Override
    public Cv update(Long id, Cv cv) {
        Optional<Cv> existing = cvRepository.findById(id);
        if (existing.isPresent()) {
            Cv toUpdate = existing.get();
            toUpdate.setLanguageId(cv.getLanguageId());
            toUpdate.setLanguage(cv.getLanguage());
            toUpdate.setTitle(cv.getTitle());
            toUpdate.setPdf(cv.getPdf());
            toUpdate.setPdfDataUrl(cv.getPdfDataUrl());
            toUpdate.setUserId(cv.getUserId());
            toUpdate.setDate(cv.getDate());
            toUpdate.setStatus(cv.getStatus());
            return cvRepository.save(toUpdate);
        }
        throw new RuntimeException("Cv not found with id: " + id);
    }
    
    @Override
    public void deleteById(Long id) {
        cvRepository.deleteById(id);
    }

    @Override
    public List<CvResponse> getAllCvResponses() {
        return cvRepository.findAll().stream().map(cvMapper::toResponse).collect(Collectors.toList());
    }

    @Override
    public CvResponse getCvResponseById(Long id) {
        return cvRepository.findById(id)
                .map(cvMapper::toResponse)
                .orElseThrow(() -> new HttpNotFound("CV not found with id: " + id));
    }

    @Override
    public CvResponse createCv(CreateCvRequest request) {
        Cv cv = cvMapper.toEntity(request);
        return cvMapper.toResponse(save(cv));
    }

    @Override
    public CvResponse patchCv(Long id, UpdateCvRequest request) {
        Cv existingCv = cvRepository.findById(id)
                .orElseThrow(() -> new HttpNotFound("CV not found with id: " + id));
        cvMapper.updateEntityFromRequest(request, existingCv);
        return cvMapper.toResponse(update(id, existingCv));
    }

    @Override
    public void deleteCvOrThrow(Long id) {
        if (!cvRepository.existsById(id)) {
            throw new HttpNotFound("CV not found with id: " + id);
        }
        cvRepository.deleteById(id);
    }
}

