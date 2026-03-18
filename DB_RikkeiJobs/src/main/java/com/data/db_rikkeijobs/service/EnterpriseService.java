package com.data.db_rikkeijobs.service;

import com.data.db_rikkeijobs.dto.request.CreateEnterpriseRequest;
import com.data.db_rikkeijobs.dto.request.UpdateEnterpriseRequest;
import com.data.db_rikkeijobs.dto.response.EnterpriseResponse;
import com.data.db_rikkeijobs.entity.Enterprise;

import java.util.List;
import java.util.Optional;

public interface EnterpriseService {
    List<Enterprise> findAll();
    Optional<Enterprise> findById(Long id);
    Optional<Enterprise> findByUserId(Long userId);
    List<Enterprise> findByStatus(String status);
    Enterprise save(Enterprise enterprise);
    Enterprise update(Long id, Enterprise enterprise);
    void deleteById(Long id);

    List<EnterpriseResponse> getAllEnterpriseResponses();
    EnterpriseResponse getEnterpriseResponseById(Long id);
    EnterpriseResponse getEnterpriseResponseByUserId(Long userId);
    List<EnterpriseResponse> getEnterpriseResponsesByStatus(String status);
    EnterpriseResponse createEnterprise(CreateEnterpriseRequest request);
    EnterpriseResponse updateEnterprise(Long id, UpdateEnterpriseRequest request);
    EnterpriseResponse patchEnterprise(Long id, UpdateEnterpriseRequest request);
    void deleteEnterpriseOrThrow(Long id);
}

