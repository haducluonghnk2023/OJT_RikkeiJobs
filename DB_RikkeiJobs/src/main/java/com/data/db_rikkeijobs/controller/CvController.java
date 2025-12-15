package com.data.db_rikkeijobs.controller;

import com.data.db_rikkeijobs.dto.response.ResponseWrapper;
import com.data.db_rikkeijobs.entity.Cv;
import com.data.db_rikkeijobs.exception.HttpNotFound;
import com.data.db_rikkeijobs.service.CvService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/cvs")
@RequiredArgsConstructor
public class CvController {

    private final CvService cvService;

    @GetMapping
    public ResponseEntity<?> getAllCvs() {
        List<Cv> cvs = cvService.findAll();
        
        return ResponseEntity.ok(
                ResponseWrapper.builder()
                        .status(HttpStatus.OK)
                        .code(HttpStatus.OK.value())
                        .data(cvs)
                        .message("CVs retrieved successfully")
                        .build()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCvById(@PathVariable Long id) {
        Cv cv = cvService.findById(id)
                .orElseThrow(() -> new HttpNotFound("CV not found with id: " + id));
        
        return ResponseEntity.ok(
                ResponseWrapper.builder()
                        .status(HttpStatus.OK)
                        .code(HttpStatus.OK.value())
                        .data(cv)
                        .message("CV retrieved successfully")
                        .build()
        );
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateCv(@PathVariable Long id, @RequestBody Cv cv) {
        Cv existingCv = cvService.findById(id)
                .orElseThrow(() -> new HttpNotFound("CV not found with id: " + id));
        
        if (cv.getLanguageId() != null) {
            existingCv.setLanguageId(cv.getLanguageId());
        }
        if (cv.getLanguage() != null) {
            existingCv.setLanguage(cv.getLanguage());
        }
        if (cv.getTitle() != null) {
            existingCv.setTitle(cv.getTitle());
        }
        if (cv.getPdf() != null) {
            existingCv.setPdf(cv.getPdf());
        }
        if (cv.getPdfDataUrl() != null) {
            existingCv.setPdfDataUrl(cv.getPdfDataUrl());
        }
        if (cv.getUserId() != null) {
            existingCv.setUserId(cv.getUserId());
        }
        if (cv.getDate() != null) {
            existingCv.setDate(cv.getDate());
        }
        if (cv.getStatus() != null) {
            existingCv.setStatus(cv.getStatus());
        }
        
        Cv updatedCv = cvService.update(id, existingCv);
        
        return ResponseEntity.ok(
                ResponseWrapper.builder()
                        .status(HttpStatus.OK)
                        .code(HttpStatus.OK.value())
                        .data(updatedCv)
                        .message("CV updated successfully")
                        .build()
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCv(@PathVariable Long id) {
        if (!cvService.findById(id).isPresent()) {
            throw new HttpNotFound("CV not found with id: " + id);
        }
        
        cvService.deleteById(id);
        
        return ResponseEntity.ok(
                ResponseWrapper.builder()
                        .status(HttpStatus.OK)
                        .code(HttpStatus.OK.value())
                        .data("CV deleted successfully")
                        .message("CV deleted successfully")
                        .build()
        );
    }
}

