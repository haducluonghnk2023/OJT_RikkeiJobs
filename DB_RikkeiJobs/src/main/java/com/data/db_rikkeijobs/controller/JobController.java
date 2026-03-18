package com.data.db_rikkeijobs.controller;

import com.data.db_rikkeijobs.dto.request.CreateJobRequest;
import com.data.db_rikkeijobs.dto.request.UpdateJobRequest;
import com.data.db_rikkeijobs.dto.response.JobResponse;
import com.data.db_rikkeijobs.dto.response.ResponseWrapper;
import com.data.db_rikkeijobs.service.JobService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/jobs")
@RequiredArgsConstructor
public class JobController {

    private final JobService jobService;

    @GetMapping
    public ResponseEntity<?> getAllJobs() {
        List<JobResponse> jobs = jobService.getAllJobResponses();
        return ResponseEntity.ok(
                ResponseWrapper.builder()
                        .status(HttpStatus.OK)
                        .code(HttpStatus.OK.value())
                        .data(jobs)
                        .message("Jobs retrieved successfully")
                        .build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getJobById(@PathVariable Long id) {
        JobResponse job = jobService.getJobResponseById(id);
        return ResponseEntity.ok(
                ResponseWrapper.builder()
                        .status(HttpStatus.OK)
                        .code(HttpStatus.OK.value())
                        .data(job)
                        .message("Job retrieved successfully")
                        .build());
    }

    @GetMapping("/enterprise/{enterpriseId}")
    public ResponseEntity<?> getJobsByEnterpriseId(@PathVariable Long enterpriseId) {
        List<JobResponse> jobs = jobService.getJobResponsesByEnterpriseId(enterpriseId);
        return ResponseEntity.ok(
                ResponseWrapper.builder()
                        .status(HttpStatus.OK)
                        .code(HttpStatus.OK.value())
                        .data(jobs)
                        .message("Jobs retrieved successfully")
                        .build());
    }

    @GetMapping("/industry/{industry}")
    public ResponseEntity<?> getJobsByIndustry(@PathVariable String industry) {
        List<JobResponse> jobs = jobService.getJobResponsesByIndustry(industry);
        return ResponseEntity.ok(
                ResponseWrapper.builder()
                        .status(HttpStatus.OK)
                        .code(HttpStatus.OK.value())
                        .data(jobs)
                        .message("Jobs retrieved successfully")
                        .build());
    }

    @GetMapping("/flight/{flight}")
    public ResponseEntity<?> getJobsByFlight(@PathVariable String flight) {
        List<JobResponse> jobs = jobService.getJobResponsesByFlight(flight);
        return ResponseEntity.ok(
                ResponseWrapper.builder()
                        .status(HttpStatus.OK)
                        .code(HttpStatus.OK.value())
                        .data(jobs)
                        .message("Jobs retrieved successfully")
                        .build());
    }

    @PostMapping
    public ResponseEntity<?> createJob(@Valid @RequestBody CreateJobRequest request) {
        JobResponse createdJob = jobService.createJob(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ResponseWrapper.builder()
                        .status(HttpStatus.CREATED)
                        .code(HttpStatus.CREATED.value())
                        .data(createdJob)
                        .message("Job created successfully")
                        .build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateJob(@PathVariable Long id,
                                       @Valid @RequestBody UpdateJobRequest request) {
        JobResponse updatedJob = jobService.updateJob(id, request);
        return ResponseEntity.ok(
                ResponseWrapper.builder()
                        .status(HttpStatus.OK)
                        .code(HttpStatus.OK.value())
                        .data(updatedJob)
                        .message("Job updated successfully")
                        .build());
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> patchJob(@PathVariable Long id, @RequestBody UpdateJobRequest request) {
        JobResponse updatedJob = jobService.patchJob(id, request);
        return ResponseEntity.ok(
                ResponseWrapper.builder()
                        .status(HttpStatus.OK)
                        .code(HttpStatus.OK.value())
                        .data(updatedJob)
                        .message("Job updated successfully")
                        .build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteJob(@PathVariable Long id) {
        jobService.deleteJobOrThrow(id);
        return ResponseEntity.ok(
                ResponseWrapper.builder()
                        .status(HttpStatus.OK)
                        .code(HttpStatus.OK.value())
                        .data("Job deleted successfully")
                        .message("Job deleted successfully")
                        .build());
    }
}
