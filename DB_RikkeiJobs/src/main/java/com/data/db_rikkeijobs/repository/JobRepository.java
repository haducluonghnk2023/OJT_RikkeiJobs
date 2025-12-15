package com.data.db_rikkeijobs.repository;

import com.data.db_rikkeijobs.entity.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {
    List<Job> findByEnterpriseId(Long enterpriseId);
    List<Job> findByIndustry(String industry);
    List<Job> findByFlight(String flight);
}

