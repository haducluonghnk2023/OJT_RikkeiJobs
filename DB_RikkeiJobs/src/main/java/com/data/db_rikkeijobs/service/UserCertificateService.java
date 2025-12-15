package com.data.db_rikkeijobs.service;

import com.data.db_rikkeijobs.entity.UserCertificate;

import java.util.List;
import java.util.Optional;

public interface UserCertificateService {
    List<UserCertificate> findAll();
    Optional<UserCertificate> findById(Long id);
    List<UserCertificate> findByUserId(Long userId);
    List<UserCertificate> findByCertificateId(Long certificateId);
    UserCertificate save(UserCertificate userCertificate);
    UserCertificate update(Long id, UserCertificate userCertificate);
    void deleteById(Long id);
}

