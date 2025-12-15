package com.data.db_rikkeijobs.mapper;

import com.data.db_rikkeijobs.dto.request.CreateJobRequest;
import com.data.db_rikkeijobs.dto.request.UpdateJobRequest;
import com.data.db_rikkeijobs.dto.response.JobResponse;
import com.data.db_rikkeijobs.entity.Job;
import com.data.db_rikkeijobs.entity.JobBenefit;
import com.data.db_rikkeijobs.entity.JobDescription;
import com.data.db_rikkeijobs.entity.JobRank;
import com.data.db_rikkeijobs.entity.JobRequirement;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JobMapper {

    public JobResponse toResponse(Job job) {
        if (job == null) {
            return null;
        }
        return JobResponse.builder()
                .id(job.getId())
                .title(job.getTitle())
                .quantity(job.getQuantity())
                .description(convertDescriptionsToStringList(job.getDescriptions()))
                .rank(convertRanksToStringList(job.getRanks()))
                .gender(job.getGender())
                .skills(job.getSkills())
                .salaryCurrent(job.getSalaryCurrent())
                .salary(job.getSalary())
                .province(job.getProvince())
                .district(job.getDistrict())
                .image(job.getImage())
                .address(job.getAddress())
                .benefitsDescription(convertBenefitsToStringList(job.getBenefits()))
                .workingTime(job.getWorkingTime())
                .deadline(job.getDeadline())
                .required(convertRequirementsToStringList(job.getRequirements()))
                .industry(job.getIndustry())
                .enterpriseId(job.getEnterpriseId())
                .flight(job.getFlight())
                .updateDate(job.getUpdateDate())
                .enterprise(null) // Will be set separately if needed
                .build();
    }

    public Job toEntity(CreateJobRequest request) {
        if (request == null) {
            return null;
        }
        Job job = new Job();
        job.setTitle(request.getTitle());
        job.setQuantity(request.getQuantity());
        // Convert List<String> to List<JobDescription>
        if (request.getDescription() != null && !request.getDescription().isEmpty()) {
            List<JobDescription> descriptions = request.getDescription().stream()
                    .map(desc -> {
                        JobDescription jobDescription = new JobDescription();
                        jobDescription.setDescription(desc);
                        jobDescription.setJob(job);
                        return jobDescription;
                    })
                    .collect(Collectors.toList());
            job.setDescriptions(descriptions);
        }
        // Convert List<String> to List<JobRank>
        if (request.getRank() != null && !request.getRank().isEmpty()) {
            List<JobRank> ranks = request.getRank().stream()
                    .map(rankValue -> {
                        JobRank jobRank = new JobRank();
                        jobRank.setRank(rankValue);
                        jobRank.setJob(job);
                        return jobRank;
                    })
                    .collect(Collectors.toList());
            job.setRanks(ranks);
        }
        job.setGender(request.getGender());
        job.setSkills(request.getSkills());
        job.setSalaryCurrent(request.getSalaryCurrent());
        job.setSalary(request.getSalary());
        job.setProvince(request.getProvince());
        job.setDistrict(request.getDistrict());
        job.setImage(request.getImage());
        job.setAddress(request.getAddress());
        // Convert List<String> to List<JobBenefit>
        if (request.getBenefitsDescription() != null && !request.getBenefitsDescription().isEmpty()) {
            List<JobBenefit> benefits = request.getBenefitsDescription().stream()
                    .map(benefit -> {
                        JobBenefit jobBenefit = new JobBenefit();
                        jobBenefit.setBenefit(benefit);
                        jobBenefit.setJob(job);
                        return jobBenefit;
                    })
                    .collect(Collectors.toList());
            job.setBenefits(benefits);
        }
        job.setWorkingTime(request.getWorkingTime());
        job.setDeadline(request.getDeadline());
        // Convert List<String> to List<JobRequirement>
        if (request.getRequired() != null && !request.getRequired().isEmpty()) {
            List<JobRequirement> requirements = request.getRequired().stream()
                    .map(req -> {
                        JobRequirement jobRequirement = new JobRequirement();
                        jobRequirement.setRequirement(req);
                        jobRequirement.setJob(job);
                        return jobRequirement;
                    })
                    .collect(Collectors.toList());
            job.setRequirements(requirements);
        }
        job.setIndustry(request.getIndustry());
        job.setEnterpriseId(request.getEnterpriseId());
        job.setFlight(request.getFlight() != null ? request.getFlight() : "pending");
        job.setUpdateDate(LocalDateTime.now());
        return job;
    }

    public void updateEntityFromRequest(UpdateJobRequest request, Job job) {
        if (request == null || job == null) {
            return;
        }
        if (request.getTitle() != null) job.setTitle(request.getTitle());
        if (request.getQuantity() != null) job.setQuantity(request.getQuantity());
        if (request.getDescription() != null) {
            job.getDescriptions().clear();
            if (!request.getDescription().isEmpty()) {
                List<JobDescription> descriptions = request.getDescription().stream()
                        .map(desc -> {
                            JobDescription jobDescription = new JobDescription();
                            jobDescription.setDescription(desc);
                            jobDescription.setJob(job);
                            return jobDescription;
                        })
                        .collect(Collectors.toList());
                job.getDescriptions().addAll(descriptions);
            }
        }
        if (request.getRank() != null) {
            job.getRanks().clear();
            if (!request.getRank().isEmpty()) {
                List<JobRank> ranks = request.getRank().stream()
                        .map(rankValue -> {
                            JobRank jobRank = new JobRank();
                            jobRank.setRank(rankValue);
                            jobRank.setJob(job);
                            return jobRank;
                        })
                        .collect(Collectors.toList());
                job.getRanks().addAll(ranks);
            }
        }
        if (request.getGender() != null) job.setGender(request.getGender());
        if (request.getSkills() != null) job.setSkills(request.getSkills());
        if (request.getSalaryCurrent() != null) job.setSalaryCurrent(request.getSalaryCurrent());
        if (request.getSalary() != null) job.setSalary(request.getSalary());
        if (request.getProvince() != null) job.setProvince(request.getProvince());
        if (request.getDistrict() != null) job.setDistrict(request.getDistrict());
        if (request.getImage() != null) job.setImage(request.getImage());
        if (request.getAddress() != null) job.setAddress(request.getAddress());
        if (request.getBenefitsDescription() != null) {
            job.getBenefits().clear();
            if (!request.getBenefitsDescription().isEmpty()) {
                List<JobBenefit> benefits = request.getBenefitsDescription().stream()
                        .map(benefit -> {
                            JobBenefit jobBenefit = new JobBenefit();
                            jobBenefit.setBenefit(benefit);
                            jobBenefit.setJob(job);
                            return jobBenefit;
                        })
                        .collect(Collectors.toList());
                job.getBenefits().addAll(benefits);
            }
        }
        if (request.getWorkingTime() != null) job.setWorkingTime(request.getWorkingTime());
        if (request.getDeadline() != null) job.setDeadline(request.getDeadline());
        if (request.getRequired() != null) {
            job.getRequirements().clear();
            if (!request.getRequired().isEmpty()) {
                List<JobRequirement> requirements = request.getRequired().stream()
                        .map(req -> {
                            JobRequirement jobRequirement = new JobRequirement();
                            jobRequirement.setRequirement(req);
                            jobRequirement.setJob(job);
                            return jobRequirement;
                        })
                        .collect(Collectors.toList());
                job.getRequirements().addAll(requirements);
            }
        }
        if (request.getIndustry() != null) job.setIndustry(request.getIndustry());
        if (request.getEnterpriseId() != null) job.setEnterpriseId(request.getEnterpriseId());
        if (request.getFlight() != null) job.setFlight(request.getFlight());
        job.setUpdateDate(LocalDateTime.now());
    }
    
    // Helper methods to convert between List<String> and List<Entity>
    private List<String> convertDescriptionsToStringList(List<JobDescription> descriptions) {
        if (descriptions == null || descriptions.isEmpty()) {
            return new ArrayList<>();
        }
        return descriptions.stream()
                .map(JobDescription::getDescription)
                .collect(Collectors.toList());
    }
    
    private List<String> convertRanksToStringList(List<JobRank> ranks) {
        if (ranks == null || ranks.isEmpty()) {
            return new ArrayList<>();
        }
        return ranks.stream()
                .map(JobRank::getRank)
                .collect(Collectors.toList());
    }
    
    private List<String> convertBenefitsToStringList(List<JobBenefit> benefits) {
        if (benefits == null || benefits.isEmpty()) {
            return new ArrayList<>();
        }
        return benefits.stream()
                .map(JobBenefit::getBenefit)
                .collect(Collectors.toList());
    }
    
    private List<String> convertRequirementsToStringList(List<JobRequirement> requirements) {
        if (requirements == null || requirements.isEmpty()) {
            return new ArrayList<>();
        }
        return requirements.stream()
                .map(JobRequirement::getRequirement)
                .collect(Collectors.toList());
    }
}

