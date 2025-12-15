package com.data.db_rikkeijobs.service;

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
}

