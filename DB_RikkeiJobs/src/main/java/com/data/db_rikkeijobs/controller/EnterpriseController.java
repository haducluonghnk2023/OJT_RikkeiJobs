package com.data.db_rikkeijobs.controller;

import com.data.db_rikkeijobs.dto.request.CreateEnterpriseRequest;
import com.data.db_rikkeijobs.dto.request.UpdateEnterpriseRequest;
import com.data.db_rikkeijobs.dto.response.EnterpriseResponse;
import com.data.db_rikkeijobs.dto.response.ResponseWrapper;
import com.data.db_rikkeijobs.entity.Enterprise;
import com.data.db_rikkeijobs.exception.HttpNotFound;
import com.data.db_rikkeijobs.mapper.EnterpriseMapper;
import com.data.db_rikkeijobs.service.EnterpriseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/enterprises")
@RequiredArgsConstructor
public class EnterpriseController {

    private final EnterpriseService enterpriseService;
    private final EnterpriseMapper enterpriseMapper;

    @GetMapping
    public ResponseEntity<?> getAllEnterprises() {
        List<EnterpriseResponse> enterprises = enterpriseService.findAll().stream()
                .map(enterpriseMapper::toResponse)
                .collect(Collectors.toList());
        
        return ResponseEntity.ok(
                ResponseWrapper.builder()
                        .status(HttpStatus.OK)
                        .code(HttpStatus.OK.value())
                        .data(enterprises)
                        .message("Enterprises retrieved successfully")
                        .build()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getEnterpriseById(@PathVariable Long id) {
        EnterpriseResponse enterprise = enterpriseService.findById(id)
                .map(enterpriseMapper::toResponse)
                .orElseThrow(() -> new HttpNotFound("Enterprise not found with id: " + id));
        
        return ResponseEntity.ok(
                ResponseWrapper.builder()
                        .status(HttpStatus.OK)
                        .code(HttpStatus.OK.value())
                        .data(enterprise)
                        .message("Enterprise retrieved successfully")
                        .build()
        );
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getEnterpriseByUserId(@PathVariable Long userId) {
        EnterpriseResponse enterprise = enterpriseService.findByUserId(userId)
                .map(enterpriseMapper::toResponse)
                .orElseThrow(() -> new HttpNotFound("Enterprise not found with user id: " + userId));
        
        return ResponseEntity.ok(
                ResponseWrapper.builder()
                        .status(HttpStatus.OK)
                        .code(HttpStatus.OK.value())
                        .data(enterprise)
                        .message("Enterprise retrieved successfully")
                        .build()
        );
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<?> getEnterprisesByStatus(@PathVariable String status) {
        List<EnterpriseResponse> enterprises = enterpriseService.findByStatus(status).stream()
                .map(enterpriseMapper::toResponse)
                .collect(Collectors.toList());
        
        return ResponseEntity.ok(
                ResponseWrapper.builder()
                        .status(HttpStatus.OK)
                        .code(HttpStatus.OK.value())
                        .data(enterprises)
                        .message("Enterprises retrieved successfully")
                        .build()
        );
    }

    @PostMapping
    public ResponseEntity<?> createEnterprise(@Valid @RequestBody CreateEnterpriseRequest request) {
        Enterprise enterprise = enterpriseMapper.toEntity(request);
        EnterpriseResponse createdEnterprise = enterpriseMapper.toResponse(enterpriseService.save(enterprise));
        
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ResponseWrapper.builder()
                        .status(HttpStatus.CREATED)
                        .code(HttpStatus.CREATED.value())
                        .data(createdEnterprise)
                        .message("Enterprise created successfully")
                        .build()
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateEnterprise(@PathVariable Long id, 
                                            @Valid @RequestBody UpdateEnterpriseRequest request) {
        Enterprise existingEnterprise = enterpriseService.findById(id)
                .orElseThrow(() -> new HttpNotFound("Enterprise not found with id: " + id));
        
        enterpriseMapper.updateEntityFromRequest(request, existingEnterprise);
        EnterpriseResponse updatedEnterprise = enterpriseMapper.toResponse(
                enterpriseService.update(id, existingEnterprise));
        
        return ResponseEntity.ok(
                ResponseWrapper.builder()
                        .status(HttpStatus.OK)
                        .code(HttpStatus.OK.value())
                        .data(updatedEnterprise)
                        .message("Enterprise updated successfully")
                        .build()
        );
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> patchEnterprise(@PathVariable Long id, @RequestBody UpdateEnterpriseRequest request) {
        Enterprise existingEnterprise = enterpriseService.findById(id)
                .orElseThrow(() -> new HttpNotFound("Enterprise not found with id: " + id));
        
        enterpriseMapper.updateEntityFromRequest(request, existingEnterprise);
        
        EnterpriseResponse updatedEnterprise = enterpriseMapper.toResponse(
                enterpriseService.update(id, existingEnterprise));
        
        return ResponseEntity.ok(
                ResponseWrapper.builder()
                        .status(HttpStatus.OK)
                        .code(HttpStatus.OK.value())
                        .data(updatedEnterprise)
                        .message("Enterprise updated successfully")
                        .build()
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEnterprise(@PathVariable Long id) {
        if (!enterpriseService.findById(id).isPresent()) {
            throw new HttpNotFound("Enterprise not found with id: " + id);
        }
        
        enterpriseService.deleteById(id);
        
        return ResponseEntity.ok(
                ResponseWrapper.builder()
                        .status(HttpStatus.OK)
                        .code(HttpStatus.OK.value())
                        .data("Enterprise deleted successfully")
                        .message("Enterprise deleted successfully")
                        .build()
        );
    }
}

