package com.data.db_rikkeijobs.controller;

import com.data.db_rikkeijobs.dto.request.CreateCertificateTypeRequest;
import com.data.db_rikkeijobs.dto.request.UpdateCertificateTypeRequest;
import com.data.db_rikkeijobs.dto.response.ResponseWrapper;
import com.data.db_rikkeijobs.entity.CertificateType;
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
                        .build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCertificateTypeById(@PathVariable Long id) {
        CertificateType certificateType = certificateTypeService.getCertificateTypeByIdOrThrow(id);
        return ResponseEntity.ok(
                ResponseWrapper.builder()
                        .status(HttpStatus.OK)
                        .code(HttpStatus.OK.value())
                        .data(certificateType)
                        .message("Certificate type retrieved successfully")
                        .build());
    }

    @PostMapping
    public ResponseEntity<?> createCertificateType(@RequestBody CreateCertificateTypeRequest request) {
        CertificateType created = certificateTypeService.createCertificateType(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ResponseWrapper.builder()
                        .status(HttpStatus.CREATED)
                        .code(HttpStatus.CREATED.value())
                        .data(created)
                        .message("Certificate type created successfully")
                        .build());
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateCertificateType(@PathVariable Long id, @RequestBody UpdateCertificateTypeRequest request) {
        CertificateType updated = certificateTypeService.updateCertificateType(id, request);
        return ResponseEntity.ok(
                ResponseWrapper.builder()
                        .status(HttpStatus.OK)
                        .code(HttpStatus.OK.value())
                        .data(updated)
                        .message("Certificate type updated successfully")
                        .build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCertificateType(@PathVariable Long id) {
        certificateTypeService.deleteCertificateTypeOrThrow(id);
        return ResponseEntity.ok(
                ResponseWrapper.builder()
                        .status(HttpStatus.OK)
                        .code(HttpStatus.OK.value())
                        .data("Certificate type deleted successfully")
                        .message("Certificate type deleted successfully")
                        .build());
    }
}
