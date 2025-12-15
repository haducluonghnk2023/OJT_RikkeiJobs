package com.data.db_rikkeijobs.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateEnterpriseRequest {
    @NotBlank(message = "Title is required")
    @Size(max = 255, message = "Title must not exceed 255 characters")
    private String title;

    @Email(message = "Email must be valid")
    private String email;

    private String companySize;
    private String phoneNumber;
    private String industry;
    private String introduction;
    private String websiteUrl;
    private String facebookUrl;
    private String linkedinUrl;
    private String twitterUrl;
    private String businessLicense;
    private String address;

    @NotNull(message = "User ID is required")
    private Long userId;

    private String avatar;
    private String status;
}

