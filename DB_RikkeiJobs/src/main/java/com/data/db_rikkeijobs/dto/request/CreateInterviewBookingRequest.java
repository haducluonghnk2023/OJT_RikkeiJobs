package com.data.db_rikkeijobs.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateInterviewBookingRequest {
    @NotNull(message = "Enterprise ID is required")
    private Long enterpriseId;

    @NotNull(message = "Job ID is required")
    private Long jobId;

    private String time;
    private String date;

    @NotNull(message = "User ID is required")
    private Long userId;

    private String status;
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

