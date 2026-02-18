package com.data.db_rikkeijobs.mapper;

import com.data.db_rikkeijobs.dto.request.CreateInterviewBookingRequest;
import com.data.db_rikkeijobs.dto.request.UpdateInterviewBookingRequest;
import com.data.db_rikkeijobs.dto.response.InterviewBookingResponse;
import com.data.db_rikkeijobs.entity.InterviewBooking;
import com.data.db_rikkeijobs.entity.InterviewBookingRank;
import com.data.db_rikkeijobs.entity.InterviewBookingUpdateTime;
import com.data.db_rikkeijobs.entity.InterviewBookingStatus;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class InterviewBookingMapper {
    private static boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }

    public InterviewBookingResponse toResponse(InterviewBooking interviewBooking) {
        if (interviewBooking == null) {
            return null;
        }
        return InterviewBookingResponse.builder()
                .id(interviewBooking.getId())
                .enterpriseId(interviewBooking.getEnterpriseId())
                .jobId(interviewBooking.getJobId())
                .time(interviewBooking.getTime())
                .date(interviewBooking.getDate())
                .userId(interviewBooking.getUserId())
                .status(interviewBooking.getStatus())
                .createAt(interviewBooking.getCreateAt())
                .meetingLink(interviewBooking.getMeetingLink())
                .updateStatusTime(convertUpdateTimesToStringList(interviewBooking.getUpdateStatusTimes()))
                .cancelReason(interviewBooking.getCancelReason())
                .interviewMode(interviewBooking.getInterviewMode())
                .description(interviewBooking.getDescription())
                .rank(convertRanksToStringList(interviewBooking.getRanks()))
                .skills(interviewBooking.getSkills())
                .province(interviewBooking.getProvince())
                .district(interviewBooking.getDistrict())
                .address(interviewBooking.getAddress())
                .benefitsDescription(interviewBooking.getBenefitsDescription())
                .workingTime(interviewBooking.getWorkingTime())
                .enterprise(null) // Will be set separately if needed
                .job(null) // Will be set separately if needed
                .user(null) // Will be set separately if needed
                .build();
    }

    public InterviewBooking toEntity(CreateInterviewBookingRequest request) {
        if (request == null) {
            return null;
        }
        InterviewBooking interviewBooking = new InterviewBooking();
        interviewBooking.setEnterpriseId(request.getEnterpriseId());
        interviewBooking.setJobId(request.getJobId());
        // Normalize blank strings to null so UI fallback logic works and PATCH doesn't wipe values.
        interviewBooking.setTime(isBlank(request.getTime()) ? null : request.getTime().trim());
        interviewBooking.setDate(isBlank(request.getDate()) ? null : request.getDate().trim());
        interviewBooking.setUserId(request.getUserId());
        interviewBooking.setStatus(request.getStatus() != null ? request.getStatus() : InterviewBookingStatus.PENDING);
        interviewBooking.setCreateAt(LocalDateTime.now());
        interviewBooking.setMeetingLink(isBlank(request.getMeetingLink()) ? null : request.getMeetingLink().trim());
        // Convert List<String> to List<InterviewBookingUpdateTime>
        if (request.getUpdateStatusTime() != null && !request.getUpdateStatusTime().isEmpty()) {
            List<InterviewBookingUpdateTime> updateTimes = request.getUpdateStatusTime().stream()
                    .map(time -> {
                        InterviewBookingUpdateTime updateTime = new InterviewBookingUpdateTime();
                        updateTime.setUpdateTime(time);
                        updateTime.setInterviewBooking(interviewBooking); // Set reference instead of ID
                        return updateTime;
                    })
                    .collect(Collectors.toList());
            interviewBooking.setUpdateStatusTimes(updateTimes);
        }
        interviewBooking.setCancelReason(isBlank(request.getCancelReason()) ? null : request.getCancelReason().trim());
        interviewBooking.setInterviewMode(isBlank(request.getInterviewMode()) ? null : request.getInterviewMode().trim());
        interviewBooking.setDescription(isBlank(request.getDescription()) ? null : request.getDescription().trim());
        // Convert List<String> to List<InterviewBookingRank>
        if (request.getRank() != null && !request.getRank().isEmpty()) {
            List<InterviewBookingRank> ranks = request.getRank().stream()
                    .map(rankValue -> {
                        InterviewBookingRank rank = new InterviewBookingRank();
                        rank.setRankValue(rankValue);
                        rank.setInterviewBooking(interviewBooking); // Set reference instead of ID
                        return rank;
                    })
                    .collect(Collectors.toList());
            interviewBooking.setRanks(ranks);
        }
        interviewBooking.setSkills(isBlank(request.getSkills()) ? null : request.getSkills().trim());
        interviewBooking.setProvince(isBlank(request.getProvince()) ? null : request.getProvince().trim());
        interviewBooking.setDistrict(isBlank(request.getDistrict()) ? null : request.getDistrict().trim());
        interviewBooking.setAddress(isBlank(request.getAddress()) ? null : request.getAddress().trim());
        interviewBooking.setBenefitsDescription(isBlank(request.getBenefitsDescription()) ? null : request.getBenefitsDescription().trim());
        interviewBooking.setWorkingTime(isBlank(request.getWorkingTime()) ? null : request.getWorkingTime().trim());
        return interviewBooking;
    }

    public void updateEntityFromRequest(UpdateInterviewBookingRequest request, InterviewBooking interviewBooking) {
        if (request == null || interviewBooking == null) {
            return;
        }
        if (request.getEnterpriseId() != null) interviewBooking.setEnterpriseId(request.getEnterpriseId());
        if (request.getJobId() != null) interviewBooking.setJobId(request.getJobId());
        if (!isBlank(request.getTime())) interviewBooking.setTime(request.getTime().trim());
        if (!isBlank(request.getDate())) interviewBooking.setDate(request.getDate().trim());
        if (request.getUserId() != null) interviewBooking.setUserId(request.getUserId());
        if (request.getStatus() != null) interviewBooking.setStatus(request.getStatus());
        if (!isBlank(request.getMeetingLink())) interviewBooking.setMeetingLink(request.getMeetingLink().trim());
        if (request.getUpdateStatusTime() != null) {
            // Clear existing and set new update times
            interviewBooking.getUpdateStatusTimes().clear();
            if (!request.getUpdateStatusTime().isEmpty()) {
                List<InterviewBookingUpdateTime> updateTimes = request.getUpdateStatusTime().stream()
                        .map(time -> {
                            InterviewBookingUpdateTime updateTime = new InterviewBookingUpdateTime();
                            updateTime.setUpdateTime(time);
                            updateTime.setInterviewBooking(interviewBooking); // Set reference instead of ID
                            return updateTime;
                        })
                        .collect(Collectors.toList());
                interviewBooking.getUpdateStatusTimes().addAll(updateTimes);
            }
        }
        if (!isBlank(request.getCancelReason())) interviewBooking.setCancelReason(request.getCancelReason().trim());
        if (!isBlank(request.getInterviewMode())) interviewBooking.setInterviewMode(request.getInterviewMode().trim());
        if (!isBlank(request.getDescription())) interviewBooking.setDescription(request.getDescription().trim());
        if (request.getRank() != null) {
            // Clear existing and set new ranks
            interviewBooking.getRanks().clear();
            if (!request.getRank().isEmpty()) {
                List<InterviewBookingRank> ranks = request.getRank().stream()
                        .map(rankValue -> {
                            InterviewBookingRank rank = new InterviewBookingRank();
                            rank.setRankValue(rankValue);
                            rank.setInterviewBooking(interviewBooking); // Set reference instead of ID
                            return rank;
                        })
                        .collect(Collectors.toList());
                interviewBooking.getRanks().addAll(ranks);
            }
        }
        if (!isBlank(request.getSkills())) interviewBooking.setSkills(request.getSkills().trim());
        if (!isBlank(request.getProvince())) interviewBooking.setProvince(request.getProvince().trim());
        if (!isBlank(request.getDistrict())) interviewBooking.setDistrict(request.getDistrict().trim());
        if (!isBlank(request.getAddress())) interviewBooking.setAddress(request.getAddress().trim());
        if (!isBlank(request.getBenefitsDescription())) interviewBooking.setBenefitsDescription(request.getBenefitsDescription().trim());
        if (!isBlank(request.getWorkingTime())) interviewBooking.setWorkingTime(request.getWorkingTime().trim());
    }
    
    // Helper methods to convert between List<String> and List<Entity>
    private List<String> convertUpdateTimesToStringList(List<InterviewBookingUpdateTime> updateTimes) {
        if (updateTimes == null || updateTimes.isEmpty()) {
            return new ArrayList<>();
        }
        return updateTimes.stream()
                .map(InterviewBookingUpdateTime::getUpdateTime)
                .collect(Collectors.toList());
    }
    
    private List<String> convertRanksToStringList(List<InterviewBookingRank> ranks) {
        if (ranks == null || ranks.isEmpty()) {
            return new ArrayList<>();
        }
        return ranks.stream()
                .map(InterviewBookingRank::getRankValue)
                .collect(Collectors.toList());
    }
}

