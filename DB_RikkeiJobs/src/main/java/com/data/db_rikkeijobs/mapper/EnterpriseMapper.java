package com.data.db_rikkeijobs.mapper;

import com.data.db_rikkeijobs.dto.request.CreateEnterpriseRequest;
import com.data.db_rikkeijobs.dto.request.UpdateEnterpriseRequest;
import com.data.db_rikkeijobs.dto.response.EnterpriseResponse;
import com.data.db_rikkeijobs.entity.Enterprise;
import org.springframework.stereotype.Component;

@Component
public class EnterpriseMapper {

    public EnterpriseResponse toResponse(Enterprise enterprise) {
        if (enterprise == null) {
            return null;
        }
        return EnterpriseResponse.builder()
                .id(enterprise.getId())
                .title(enterprise.getTitle())
                .email(enterprise.getEmail())
                .companySize(enterprise.getCompanySize())
                .phoneNumber(enterprise.getPhoneNumber())
                .industry(enterprise.getIndustry())
                .introduction(enterprise.getIntroduction())
                .websiteUrl(enterprise.getWebsiteUrl())
                .facebookUrl(enterprise.getFacebookUrl())
                .linkedinUrl(enterprise.getLinkedinUrl())
                .twitterUrl(enterprise.getTwitterUrl())
                .businessLicense(enterprise.getBusinessLicense())
                .address(enterprise.getAddress())
                .userId(enterprise.getUserId())
                .avatar(enterprise.getAvatar())
                .status(enterprise.getStatus())
                .user(null) // Will be set separately if needed
                .build();
    }

    public Enterprise toEntity(CreateEnterpriseRequest request) {
        if (request == null) {
            return null;
        }
        Enterprise enterprise = new Enterprise();
        enterprise.setTitle(request.getTitle());
        enterprise.setEmail(request.getEmail());
        enterprise.setCompanySize(request.getCompanySize());
        enterprise.setPhoneNumber(request.getPhoneNumber());
        enterprise.setIndustry(request.getIndustry());
        enterprise.setIntroduction(request.getIntroduction());
        enterprise.setWebsiteUrl(request.getWebsiteUrl());
        enterprise.setFacebookUrl(request.getFacebookUrl());
        enterprise.setLinkedinUrl(request.getLinkedinUrl());
        enterprise.setTwitterUrl(request.getTwitterUrl());
        enterprise.setBusinessLicense(request.getBusinessLicense());
        enterprise.setAddress(request.getAddress());
        enterprise.setUserId(request.getUserId());
        enterprise.setAvatar(request.getAvatar());
        enterprise.setStatus(request.getStatus() != null ? request.getStatus() : "pending");
        return enterprise;
    }

    public void updateEntityFromRequest(UpdateEnterpriseRequest request, Enterprise enterprise) {
        if (request == null || enterprise == null) {
            return;
        }
        if (request.getTitle() != null) enterprise.setTitle(request.getTitle());
        if (request.getEmail() != null) enterprise.setEmail(request.getEmail());
        if (request.getCompanySize() != null) enterprise.setCompanySize(request.getCompanySize());
        if (request.getPhoneNumber() != null) enterprise.setPhoneNumber(request.getPhoneNumber());
        if (request.getIndustry() != null) enterprise.setIndustry(request.getIndustry());
        if (request.getIntroduction() != null) enterprise.setIntroduction(request.getIntroduction());
        if (request.getWebsiteUrl() != null) enterprise.setWebsiteUrl(request.getWebsiteUrl());
        if (request.getFacebookUrl() != null) enterprise.setFacebookUrl(request.getFacebookUrl());
        if (request.getLinkedinUrl() != null) enterprise.setLinkedinUrl(request.getLinkedinUrl());
        if (request.getTwitterUrl() != null) enterprise.setTwitterUrl(request.getTwitterUrl());
        if (request.getBusinessLicense() != null) enterprise.setBusinessLicense(request.getBusinessLicense());
        if (request.getAddress() != null) enterprise.setAddress(request.getAddress());
        if (request.getUserId() != null) enterprise.setUserId(request.getUserId());
        if (request.getAvatar() != null) enterprise.setAvatar(request.getAvatar());
        if (request.getStatus() != null) enterprise.setStatus(request.getStatus());
    }
}

