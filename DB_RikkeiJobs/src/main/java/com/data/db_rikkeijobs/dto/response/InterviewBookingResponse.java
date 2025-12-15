package com.data.db_rikkeijobs.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InterviewBookingResponse {
    private Long id;
    private Long enterpriseId;
    private Long jobId;
    private String time;
    private String date;
    private Long userId;
    private String status;
    private LocalDateTime createAt;
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
    private EnterpriseResponse enterprise; // Optional
    private JobResponse job; // Optional
    private UserResponse user; // Optional
}

