package com.data.db_rikkeijobs.service;

import com.data.db_rikkeijobs.entity.CertificateType;

import java.util.List;
import java.util.Optional;

public interface CertificateTypeService {
    List<CertificateType> findAll();
    Optional<CertificateType> findById(Long id);
    CertificateType save(CertificateType certificateType);
    CertificateType update(Long id, CertificateType certificateType);
    void deleteById(Long id);
}

