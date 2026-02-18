package com.data.db_rikkeijobs.controller;

import com.data.db_rikkeijobs.dto.request.CreateJobRequest;
import com.data.db_rikkeijobs.dto.request.UpdateJobRequest;
import com.data.db_rikkeijobs.dto.response.JobResponse;
import com.data.db_rikkeijobs.dto.response.ResponseWrapper;
import com.data.db_rikkeijobs.entity.Job;
import com.data.db_rikkeijobs.exception.HttpNotFound;
import com.data.db_rikkeijobs.mapper.JobMapper;
import com.data.db_rikkeijobs.service.JobService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/jobs")
@RequiredArgsConstructor
public class JobController {

        private final JobService jobService;
        private final JobMapper jobMapper;

        @GetMapping
        public ResponseEntity<?> getAllJobs() {
                List<JobResponse> jobs = jobService.findAll().stream()
                                .map(jobMapper::toResponse)
                                .collect(Collectors.toList());

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
                JobResponse job = jobService.findById(id)
                                .map(jobMapper::toResponse)
                                .orElseThrow(() -> new HttpNotFound("Job not found with id: " + id));

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
                List<JobResponse> jobs = jobService.findByEnterpriseId(enterpriseId).stream()
                                .map(jobMapper::toResponse)
                                .collect(Collectors.toList());

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
                List<JobResponse> jobs = jobService.findByIndustry(industry).stream()
                                .map(jobMapper::toResponse)
                                .collect(Collectors.toList());

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
                List<JobResponse> jobs = jobService.findByFlight(flight).stream()
                                .map(jobMapper::toResponse)
                                .collect(Collectors.toList());

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
                Job job = jobMapper.toEntity(request);
                JobResponse createdJob = jobMapper.toResponse(jobService.save(job));

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
                Job existingJob = jobService.findById(id)
                                .orElseThrow(() -> new HttpNotFound("Job not found with id: " + id));

                jobMapper.updateEntityFromRequest(request, existingJob);
                JobResponse updatedJob = jobMapper.toResponse(jobService.update(id, existingJob));

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
                Job existingJob = jobService.findById(id)
                                .orElseThrow(() -> new HttpNotFound("Job not found with id: " + id));

                jobMapper.updateEntityFromRequest(request, existingJob);
                JobResponse updatedJob = jobMapper.toResponse(jobService.update(id, existingJob));

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
                if (!jobService.findById(id).isPresent()) {
                        throw new HttpNotFound("Job not found with id: " + id);
                }

                jobService.deleteById(id);

                return ResponseEntity.ok(
                                ResponseWrapper.builder()
                                                .status(HttpStatus.OK)
                                                .code(HttpStatus.OK.value())
                                                .data("Job deleted successfully")
                                                .message("Job deleted successfully")
                                                .build());
        }
}
