package com.data.db_rikkeijobs.service.impl;

import com.data.db_rikkeijobs.dto.request.CreateJobRequest;
import com.data.db_rikkeijobs.dto.request.UpdateJobRequest;
import com.data.db_rikkeijobs.dto.response.JobResponse;
import com.data.db_rikkeijobs.entity.Job;
import com.data.db_rikkeijobs.entity.JobBenefit;
import com.data.db_rikkeijobs.entity.JobDescription;
import com.data.db_rikkeijobs.entity.JobRank;
import com.data.db_rikkeijobs.entity.JobRequirement;
import com.data.db_rikkeijobs.exception.HttpNotFound;
import com.data.db_rikkeijobs.mapper.JobMapper;
import com.data.db_rikkeijobs.repository.JobRepository;
import com.data.db_rikkeijobs.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class JobServiceImpl implements JobService {
    
    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private JobMapper jobMapper;
    
    @Override
    public List<Job> findAll() {
        return jobRepository.findAll();
    }
    
    @Override
    public Optional<Job> findById(Long id) {
        return jobRepository.findById(id);
    }
    
    @Override
    public List<Job> findByEnterpriseId(Long enterpriseId) {
        return jobRepository.findByEnterpriseId(enterpriseId);
    }
    
    @Override
    public List<Job> findByIndustry(String industry) {
        return jobRepository.findByIndustry(industry);
    }
    
    @Override
    public List<Job> findByFlight(String flight) {
        return jobRepository.findByFlight(flight);
    }
    
    @Override
    public Job save(Job job) {
        return jobRepository.save(job);
    }
    
    @Override
    public Job update(Long id, Job job) {
        Optional<Job> existing = jobRepository.findById(id);
        if (existing.isPresent()) {
            Job toUpdate = existing.get();
            toUpdate.setTitle(job.getTitle());
            toUpdate.setQuantity(job.getQuantity());
            toUpdate.setGender(job.getGender());
            toUpdate.setSkills(job.getSkills());
            toUpdate.setSalaryCurrent(job.getSalaryCurrent());
            toUpdate.setSalary(job.getSalary());
            toUpdate.setProvince(job.getProvince());
            toUpdate.setDistrict(job.getDistrict());
            toUpdate.setImage(job.getImage());
            toUpdate.setAddress(job.getAddress());
            toUpdate.setWorkingTime(job.getWorkingTime());
            toUpdate.setDeadline(job.getDeadline());
            toUpdate.setIndustry(job.getIndustry());
            toUpdate.setEnterpriseId(job.getEnterpriseId());
            toUpdate.setFlight(job.getFlight());
            toUpdate.setUpdateDate(job.getUpdateDate());
            
            // Update descriptions list - clear existing and set new ones
            if (job.getDescriptions() != null) {
                toUpdate.getDescriptions().clear();
                List<JobDescription> newDescriptions = job.getDescriptions().stream()
                        .map(desc -> {
                            JobDescription newDesc = new JobDescription();
                            newDesc.setDescription(desc.getDescription());
                            newDesc.setJob(toUpdate);
                            return newDesc;
                        })
                        .collect(Collectors.toList());
                toUpdate.getDescriptions().addAll(newDescriptions);
            }
            
            // Update ranks list - clear existing and set new ones
            if (job.getRanks() != null) {
                toUpdate.getRanks().clear();
                List<JobRank> newRanks = job.getRanks().stream()
                        .map(rank -> {
                            JobRank newRank = new JobRank();
                            newRank.setRank(rank.getRank());
                            newRank.setJob(toUpdate);
                            return newRank;
                        })
                        .collect(Collectors.toList());
                toUpdate.getRanks().addAll(newRanks);
            }
            
            // Update benefits list - clear existing and set new ones
            if (job.getBenefits() != null) {
                toUpdate.getBenefits().clear();
                List<JobBenefit> newBenefits = job.getBenefits().stream()
                        .map(benefit -> {
                            JobBenefit newBenefit = new JobBenefit();
                            newBenefit.setBenefit(benefit.getBenefit());
                            newBenefit.setJob(toUpdate);
                            return newBenefit;
                        })
                        .collect(Collectors.toList());
                toUpdate.getBenefits().addAll(newBenefits);
            }
            
            // Update requirements list - clear existing and set new ones
            if (job.getRequirements() != null) {
                toUpdate.getRequirements().clear();
                List<JobRequirement> newRequirements = job.getRequirements().stream()
                        .map(req -> {
                            JobRequirement newReq = new JobRequirement();
                            newReq.setRequirement(req.getRequirement());
                            newReq.setJob(toUpdate);
                            return newReq;
                        })
                        .collect(Collectors.toList());
                toUpdate.getRequirements().addAll(newRequirements);
            }
            
            return jobRepository.save(toUpdate);
        }
        throw new RuntimeException("Job not found with id: " + id);
    }
    
    @Override
    public void deleteById(Long id) {
        jobRepository.deleteById(id);
    }

    @Override
    public List<JobResponse> getAllJobResponses() {
        return jobRepository.findAll().stream().map(jobMapper::toResponse).collect(Collectors.toList());
    }

    @Override
    public JobResponse getJobResponseById(Long id) {
        return jobRepository.findById(id)
                .map(jobMapper::toResponse)
                .orElseThrow(() -> new HttpNotFound("Job not found with id: " + id));
    }

    @Override
    public List<JobResponse> getJobResponsesByEnterpriseId(Long enterpriseId) {
        return jobRepository.findByEnterpriseId(enterpriseId).stream()
                .map(jobMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<JobResponse> getJobResponsesByIndustry(String industry) {
        return jobRepository.findByIndustry(industry).stream()
                .map(jobMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<JobResponse> getJobResponsesByFlight(String flight) {
        return jobRepository.findByFlight(flight).stream()
                .map(jobMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public JobResponse createJob(CreateJobRequest request) {
        Job job = jobMapper.toEntity(request);
        return jobMapper.toResponse(save(job));
    }

    @Override
    public JobResponse updateJob(Long id, UpdateJobRequest request) {
        Job existingJob = jobRepository.findById(id)
                .orElseThrow(() -> new HttpNotFound("Job not found with id: " + id));
        jobMapper.updateEntityFromRequest(request, existingJob);
        return jobMapper.toResponse(update(id, existingJob));
    }

    @Override
    public JobResponse patchJob(Long id, UpdateJobRequest request) {
        Job existingJob = jobRepository.findById(id)
                .orElseThrow(() -> new HttpNotFound("Job not found with id: " + id));
        jobMapper.updateEntityFromRequest(request, existingJob);
        return jobMapper.toResponse(update(id, existingJob));
    }

    @Override
    public void deleteJobOrThrow(Long id) {
        if (!jobRepository.existsById(id)) {
            throw new HttpNotFound("Job not found with id: " + id);
        }
        jobRepository.deleteById(id);
    }
}

