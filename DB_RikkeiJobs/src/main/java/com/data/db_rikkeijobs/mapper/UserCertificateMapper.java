package com.data.db_rikkeijobs.mapper;

import com.data.db_rikkeijobs.dto.request.CreateUserCertificateRequest;
import com.data.db_rikkeijobs.dto.request.UpdateUserCertificateRequest;
import com.data.db_rikkeijobs.dto.response.UserCertificateResponse;
import com.data.db_rikkeijobs.entity.UserCertificate;
import org.springframework.stereotype.Component;

@Component
public class UserCertificateMapper {
    public UserCertificateResponse toResponse(UserCertificate uc) {
        if (uc == null) return null;
        return UserCertificateResponse.builder()
                .id(uc.getId())
                .certificateType(uc.getCertificateType())
                .certificateValue(uc.getCertificateValue())
                .receivedDate(uc.getReceivedDate())
                .expirationDate(uc.getExpirationDate())
                .userId(uc.getUserId())
                .certificateId(uc.getCertificateId())
                .build();
    }

    public UserCertificate toEntity(CreateUserCertificateRequest request) {
        if (request == null) return null;
        UserCertificate uc = new UserCertificate();
        uc.setUserId(request.getUserId());
        uc.setCertificateId(request.getCertificateId());
        uc.setCertificateType(request.getCertificateType());
        uc.setCertificateValue(request.getCertificateValue());
        uc.setReceivedDate(request.getReceivedDate());
        uc.setExpirationDate(request.getExpirationDate());
        return uc;
    }

    public void updateEntityFromRequest(UpdateUserCertificateRequest request, UserCertificate uc) {
        if (request == null || uc == null) return;
        if (request.getUserId() != null) uc.setUserId(request.getUserId());
        if (request.getCertificateId() != null) uc.setCertificateId(request.getCertificateId());
        if (request.getCertificateType() != null) uc.setCertificateType(request.getCertificateType());
        if (request.getCertificateValue() != null) uc.setCertificateValue(request.getCertificateValue());
        if (request.getReceivedDate() != null) uc.setReceivedDate(request.getReceivedDate());
        if (request.getExpirationDate() != null) uc.setExpirationDate(request.getExpirationDate());
    }
}


