package com.data.db_rikkeijobs.dto.request;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateJobRequest {
    @Size(max = 255, message = "Title must not exceed 255 characters")
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
}

