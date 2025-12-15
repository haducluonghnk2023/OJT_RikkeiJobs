package com.data.db_rikkeijobs.repository;

import com.data.db_rikkeijobs.entity.UserCertificate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserCertificateRepository extends JpaRepository<UserCertificate, Long> {
    List<UserCertificate> findByUserId(Long userId);
    List<UserCertificate> findByCertificateId(Long certificateId);
}

