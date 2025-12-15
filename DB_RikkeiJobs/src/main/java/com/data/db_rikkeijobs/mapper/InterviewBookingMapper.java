package com.data.db_rikkeijobs.mapper;

import com.data.db_rikkeijobs.dto.request.CreateInterviewBookingRequest;
import com.data.db_rikkeijobs.dto.request.UpdateInterviewBookingRequest;
import com.data.db_rikkeijobs.dto.response.InterviewBookingResponse;
import com.data.db_rikkeijobs.entity.InterviewBooking;
import com.data.db_rikkeijobs.entity.InterviewBookingRank;
import com.data.db_rikkeijobs.entity.InterviewBookingUpdateTime;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class InterviewBookingMapper {

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
        interviewBooking.setTime(request.getTime());
        interviewBooking.setDate(request.getDate());
        interviewBooking.setUserId(request.getUserId());
        interviewBooking.setStatus(request.getStatus() != null ? request.getStatus() : "pending");
        interviewBooking.setCreateAt(LocalDateTime.now());
        interviewBooking.setMeetingLink(request.getMeetingLink());
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
        interviewBooking.setCancelReason(request.getCancelReason());
        interviewBooking.setInterviewMode(request.getInterviewMode());
        interviewBooking.setDescription(request.getDescription());
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
        interviewBooking.setSkills(request.getSkills());
        interviewBooking.setProvince(request.getProvince());
        interviewBooking.setDistrict(request.getDistrict());
        interviewBooking.setAddress(request.getAddress());
        interviewBooking.setBenefitsDescription(request.getBenefitsDescription());
        interviewBooking.setWorkingTime(request.getWorkingTime());
        return interviewBooking;
    }

    public void updateEntityFromRequest(UpdateInterviewBookingRequest request, InterviewBooking interviewBooking) {
        if (request == null || interviewBooking == null) {
            return;
        }
        if (request.getEnterpriseId() != null) interviewBooking.setEnterpriseId(request.getEnterpriseId());
        if (request.getJobId() != null) interviewBooking.setJobId(request.getJobId());
        if (request.getTime() != null) interviewBooking.setTime(request.getTime());
        if (request.getDate() != null) interviewBooking.setDate(request.getDate());
        if (request.getUserId() != null) interviewBooking.setUserId(request.getUserId());
        if (request.getStatus() != null) interviewBooking.setStatus(request.getStatus());
        if (request.getMeetingLink() != null) interviewBooking.setMeetingLink(request.getMeetingLink());
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
        if (request.getCancelReason() != null) interviewBooking.setCancelReason(request.getCancelReason());
        if (request.getInterviewMode() != null) interviewBooking.setInterviewMode(request.getInterviewMode());
        if (request.getDescription() != null) interviewBooking.setDescription(request.getDescription());
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
        if (request.getSkills() != null) interviewBooking.setSkills(request.getSkills());
        if (request.getProvince() != null) interviewBooking.setProvince(request.getProvince());
        if (request.getDistrict() != null) interviewBooking.setDistrict(request.getDistrict());
        if (request.getAddress() != null) interviewBooking.setAddress(request.getAddress());
        if (request.getBenefitsDescription() != null) interviewBooking.setBenefitsDescription(request.getBenefitsDescription());
        if (request.getWorkingTime() != null) interviewBooking.setWorkingTime(request.getWorkingTime());
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

