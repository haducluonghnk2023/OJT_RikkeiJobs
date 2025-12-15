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
public class JobResponse {
    private Long id;
    private String title;
    private Integer quantity;
    private List<String> description;
    private List<String> rank;
    private String gender;
    private String skills;
    private String salaryCurrent;
    private String salary;
    private String province;
    private String district;
    private String image;
    private String address;
    private List<String> benefitsDescription;
    private String workingTime;
    private String deadline;
    private List<String> required;
    private String industry;
    private Long enterpriseId;
    private String flight;
    private LocalDateTime updateDate;
    private EnterpriseResponse enterprise; // Optional, can be null if lazy loaded
}

