package com.data.db_rikkeijobs.controller;

import com.data.db_rikkeijobs.dto.request.CreateEnterpriseRequest;
import com.data.db_rikkeijobs.dto.request.UpdateEnterpriseRequest;
import com.data.db_rikkeijobs.dto.response.EnterpriseResponse;
import com.data.db_rikkeijobs.dto.response.ResponseWrapper;
import com.data.db_rikkeijobs.service.EnterpriseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/enterprises")
@RequiredArgsConstructor
public class EnterpriseController {

    private final EnterpriseService enterpriseService;

    @GetMapping
    public ResponseEntity<?> getAllEnterprises() {
        List<EnterpriseResponse> enterprises = enterpriseService.getAllEnterpriseResponses();
        return ResponseEntity.ok(
                ResponseWrapper.builder()
                        .status(HttpStatus.OK)
                        .code(HttpStatus.OK.value())
                        .data(enterprises)
                        .message("Enterprises retrieved successfully")
                        .build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getEnterpriseById(@PathVariable Long id) {
        EnterpriseResponse enterprise = enterpriseService.getEnterpriseResponseById(id);
        return ResponseEntity.ok(
                ResponseWrapper.builder()
                        .status(HttpStatus.OK)
                        .code(HttpStatus.OK.value())
                        .data(enterprise)
                        .message("Enterprise retrieved successfully")
                        .build());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getEnterpriseByUserId(@PathVariable Long userId) {
        EnterpriseResponse enterprise = enterpriseService.getEnterpriseResponseByUserId(userId);
        return ResponseEntity.ok(
                ResponseWrapper.builder()
                        .status(HttpStatus.OK)
                        .code(HttpStatus.OK.value())
                        .data(enterprise)
                        .message("Enterprise retrieved successfully")
                        .build());
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<?> getEnterprisesByStatus(@PathVariable String status) {
        List<EnterpriseResponse> enterprises = enterpriseService.getEnterpriseResponsesByStatus(status);
        return ResponseEntity.ok(
                ResponseWrapper.builder()
                        .status(HttpStatus.OK)
                        .code(HttpStatus.OK.value())
                        .data(enterprises)
                        .message("Enterprises retrieved successfully")
                        .build());
    }

    @PostMapping
    public ResponseEntity<?> createEnterprise(@Valid @RequestBody CreateEnterpriseRequest request) {
        EnterpriseResponse createdEnterprise = enterpriseService.createEnterprise(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ResponseWrapper.builder()
                        .status(HttpStatus.CREATED)
                        .code(HttpStatus.CREATED.value())
                        .data(createdEnterprise)
                        .message("Enterprise created successfully")
                        .build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateEnterprise(@PathVariable Long id,
                                              @Valid @RequestBody UpdateEnterpriseRequest request) {
        EnterpriseResponse updatedEnterprise = enterpriseService.updateEnterprise(id, request);
        return ResponseEntity.ok(
                ResponseWrapper.builder()
                        .status(HttpStatus.OK)
                        .code(HttpStatus.OK.value())
                        .data(updatedEnterprise)
                        .message("Enterprise updated successfully")
                        .build());
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> patchEnterprise(@PathVariable Long id, @RequestBody UpdateEnterpriseRequest request) {
        EnterpriseResponse updatedEnterprise = enterpriseService.patchEnterprise(id, request);
        return ResponseEntity.ok(
                ResponseWrapper.builder()
                        .status(HttpStatus.OK)
                        .code(HttpStatus.OK.value())
                        .data(updatedEnterprise)
                        .message("Enterprise updated successfully")
                        .build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEnterprise(@PathVariable Long id) {
        enterpriseService.deleteEnterpriseOrThrow(id);
        return ResponseEntity.ok(
                ResponseWrapper.builder()
                        .status(HttpStatus.OK)
                        .code(HttpStatus.OK.value())
                        .data("Enterprise deleted successfully")
                        .message("Enterprise deleted successfully")
                        .build());
    }
}
