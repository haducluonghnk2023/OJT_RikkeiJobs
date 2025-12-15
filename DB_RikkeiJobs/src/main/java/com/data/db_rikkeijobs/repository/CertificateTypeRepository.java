package com.data.db_rikkeijobs.repository;

import com.data.db_rikkeijobs.entity.CertificateType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CertificateTypeRepository extends JpaRepository<CertificateType, Long> {
}

