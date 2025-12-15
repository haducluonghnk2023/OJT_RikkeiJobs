package com.data.db_rikkeijobs.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EnterpriseResponse {
    private Long id;
    private String title;
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
    private Long userId;
    private String avatar;
    private String status;
    private UserResponse user; // Optional, can be null if lazy loaded
}

