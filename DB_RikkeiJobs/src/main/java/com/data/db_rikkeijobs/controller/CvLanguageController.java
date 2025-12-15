package com.data.db_rikkeijobs.controller;

import com.data.db_rikkeijobs.dto.response.ResponseWrapper;
import com.data.db_rikkeijobs.entity.CvLanguage;
import com.data.db_rikkeijobs.exception.HttpNotFound;
import com.data.db_rikkeijobs.service.CvLanguageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/cvLanguages")
@RequiredArgsConstructor
public class CvLanguageController {

    private final CvLanguageService cvLanguageService;

    @GetMapping
    public ResponseEntity<?> getAllCvLanguages() {
        List<CvLanguage> cvLanguages = cvLanguageService.findAll();
        
        return ResponseEntity.ok(
                ResponseWrapper.builder()
                        .status(HttpStatus.OK)
                        .code(HttpStatus.OK.value())
                        .data(cvLanguages)
                        .message("CV languages retrieved successfully")
                        .build()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCvLanguageById(@PathVariable Long id) {
        CvLanguage cvLanguage = cvLanguageService.findById(id)
                .orElseThrow(() -> new HttpNotFound("CV language not found with id: " + id));
        
        return ResponseEntity.ok(
                ResponseWrapper.builder()
                        .status(HttpStatus.OK)
                        .code(HttpStatus.OK.value())
                        .data(cvLanguage)
                        .message("CV language retrieved successfully")
                        .build()
        );
    }

    @PostMapping
    public ResponseEntity<?> createCvLanguage(@RequestBody CvLanguage newLanguage) {
        CvLanguage createdCvLanguage = cvLanguageService.save(newLanguage);
        
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ResponseWrapper.builder()
                        .status(HttpStatus.CREATED)
                        .code(HttpStatus.CREATED.value())
                        .data(createdCvLanguage)
                        .message("CV language created successfully")
                        .build()
        );
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateCvLanguage(@PathVariable Long id, @RequestBody CvLanguage updatedLanguage) {
        CvLanguage existingCvLanguage = cvLanguageService.findById(id)
                .orElseThrow(() -> new HttpNotFound("CV language not found with id: " + id));
        
        if (updatedLanguage.getLanguage() != null) {
            existingCvLanguage.setLanguage(updatedLanguage.getLanguage());
        }
        if (updatedLanguage.getCode() != null) {
            existingCvLanguage.setCode(updatedLanguage.getCode());
        }
        if (updatedLanguage.getStatus() != null) {
            existingCvLanguage.setStatus(updatedLanguage.getStatus());
        }
        
        CvLanguage updatedCvLanguage = cvLanguageService.update(id, existingCvLanguage);
        
        return ResponseEntity.ok(
                ResponseWrapper.builder()
                        .status(HttpStatus.OK)
                        .code(HttpStatus.OK.value())
                        .data(updatedCvLanguage)
                        .message("CV language updated successfully")
                        .build()
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCvLanguage(@PathVariable Long id) {
        if (!cvLanguageService.findById(id).isPresent()) {
            throw new HttpNotFound("CV language not found with id: " + id);
        }
        
        cvLanguageService.deleteById(id);
        
        return ResponseEntity.ok(
                ResponseWrapper.builder()
                        .status(HttpStatus.OK)
                        .code(HttpStatus.OK.value())
                        .data("CV language deleted successfully")
                        .message("CV language deleted successfully")
                        .build()
        );
    }
}

