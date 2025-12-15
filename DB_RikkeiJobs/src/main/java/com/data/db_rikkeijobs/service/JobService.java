package com.data.db_rikkeijobs.service;

import com.data.db_rikkeijobs.entity.Job;

import java.util.List;
import java.util.Optional;

public interface JobService {
    List<Job> findAll();
    Optional<Job> findById(Long id);
    List<Job> findByEnterpriseId(Long enterpriseId);
    List<Job> findByIndustry(String industry);
    List<Job> findByFlight(String flight);
    Job save(Job job);
    Job update(Long id, Job job);
    void deleteById(Long id);
}

