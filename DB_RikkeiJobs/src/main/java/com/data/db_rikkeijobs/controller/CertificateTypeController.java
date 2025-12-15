package com.data.db_rikkeijobs.controller;

import com.data.db_rikkeijobs.dto.response.ResponseWrapper;
import com.data.db_rikkeijobs.entity.CertificateType;
import com.data.db_rikkeijobs.exception.HttpNotFound;
import com.data.db_rikkeijobs.service.CertificateTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/CertificateTypes")
@RequiredArgsConstructor
public class CertificateTypeController {

    private final CertificateTypeService certificateTypeService;

    @GetMapping
    public ResponseEntity<?> getAllCertificateTypes() {
        List<CertificateType> certificateTypes = certificateTypeService.findAll();
        
        return ResponseEntity.ok(
                ResponseWrapper.builder()
                        .status(HttpStatus.OK)
                        .code(HttpStatus.OK.value())
                        .data(certificateTypes)
                        .message("Certificate types retrieved successfully")
                        .build()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCertificateTypeById(@PathVariable Long id) {
        CertificateType certificateType = certificateTypeService.findById(id)
                .orElseThrow(() -> new HttpNotFound("Certificate type not found with id: " + id));
        
        return ResponseEntity.ok(
                ResponseWrapper.builder()
                        .status(HttpStatus.OK)
                        .code(HttpStatus.OK.value())
                        .data(certificateType)
                        .message("Certificate type retrieved successfully")
                        .build()
        );
    }

    @PostMapping
    public ResponseEntity<?> createCertificateType(@RequestBody CertificateType newCerti) {
        CertificateType createdCertificateType = certificateTypeService.save(newCerti);
        
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ResponseWrapper.builder()
                        .status(HttpStatus.CREATED)
                        .code(HttpStatus.CREATED.value())
                        .data(createdCertificateType)
                        .message("Certificate type created successfully")
                        .build()
        );
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateCertificateType(@PathVariable Long id, @RequestBody CertificateType updatedCerti) {
        CertificateType existingCertificateType = certificateTypeService.findById(id)
                .orElseThrow(() -> new HttpNotFound("Certificate type not found with id: " + id));
        
        if (updatedCerti.getType() != null) {
            existingCertificateType.setType(updatedCerti.getType());
        }
        if (updatedCerti.getLanguage() != null) {
            existingCertificateType.setLanguage(updatedCerti.getLanguage());
        }
        if (updatedCerti.getStatus() != null) {
            existingCertificateType.setStatus(updatedCerti.getStatus());
        }
        if (updatedCerti.getCode() != null) {
            existingCertificateType.setCode(updatedCerti.getCode());
        }
        if (updatedCerti.getValues() != null) {
            existingCertificateType.setValues(updatedCerti.getValues());
        }
        
        CertificateType updatedCertificateType = certificateTypeService.update(id, existingCertificateType);
        
        return ResponseEntity.ok(
                ResponseWrapper.builder()
                        .status(HttpStatus.OK)
                        .code(HttpStatus.OK.value())
                        .data(updatedCertificateType)
                        .message("Certificate type updated successfully")
                        .build()
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCertificateType(@PathVariable Long id) {
        if (!certificateTypeService.findById(id).isPresent()) {
            throw new HttpNotFound("Certificate type not found with id: " + id);
        }
        
        certificateTypeService.deleteById(id);
        
        return ResponseEntity.ok(
                ResponseWrapper.builder()
                        .status(HttpStatus.OK)
                        .code(HttpStatus.OK.value())
                        .data("Certificate type deleted successfully")
                        .message("Certificate type deleted successfully")
                        .build()
        );
    }
}

