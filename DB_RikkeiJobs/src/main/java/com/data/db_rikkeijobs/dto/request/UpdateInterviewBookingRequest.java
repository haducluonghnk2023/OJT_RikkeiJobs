package com.data.db_rikkeijobs.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.data.db_rikkeijobs.entity.InterviewBookingStatus;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateInterviewBookingRequest {
    private Long enterpriseId;
    private Long jobId;
    private String time;
    private String date;
    private Long userId;
    private InterviewBookingStatus status;
    private String meetingLink;
    private List<String> updateStatusTime;
    private String cancelReason;
    private String interviewMode;
    private String description;
    private List<String> rank;
    private String skills;
    private String province;
    private String district;
    private String address;
    private String benefitsDescription;
    private String workingTime;
}

