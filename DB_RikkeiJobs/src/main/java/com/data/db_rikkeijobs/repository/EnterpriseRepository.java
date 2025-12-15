package com.data.db_rikkeijobs.repository;

import com.data.db_rikkeijobs.entity.Enterprise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EnterpriseRepository extends JpaRepository<Enterprise, Long> {
    Optional<Enterprise> findByUserId(Long userId);
    List<Enterprise> findByStatus(String status);
}

