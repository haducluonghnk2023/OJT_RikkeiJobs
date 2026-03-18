package com.data.db_rikkeijobs.service;

import com.data.db_rikkeijobs.dto.request.CreateJobRequest;
import com.data.db_rikkeijobs.dto.request.UpdateJobRequest;
import com.data.db_rikkeijobs.dto.response.JobResponse;
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

    List<JobResponse> getAllJobResponses();
    JobResponse getJobResponseById(Long id);
    List<JobResponse> getJobResponsesByEnterpriseId(Long enterpriseId);
    List<JobResponse> getJobResponsesByIndustry(String industry);
    List<JobResponse> getJobResponsesByFlight(String flight);
    JobResponse createJob(CreateJobRequest request);
    JobResponse updateJob(Long id, UpdateJobRequest request);
    JobResponse patchJob(Long id, UpdateJobRequest request);
    void deleteJobOrThrow(Long id);
}

