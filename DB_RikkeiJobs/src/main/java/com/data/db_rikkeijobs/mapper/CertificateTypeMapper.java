package com.data.db_rikkeijobs.mapper;

import com.data.db_rikkeijobs.dto.request.CreateCertificateTypeRequest;
import com.data.db_rikkeijobs.dto.request.UpdateCertificateTypeRequest;
import com.data.db_rikkeijobs.entity.CertificateType;
import com.data.db_rikkeijobs.entity.CertificateTypeValue;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CertificateTypeMapper {

    public CertificateType toEntity(CreateCertificateTypeRequest request) {
        if (request == null) return null;
        CertificateType ct = new CertificateType();
        ct.setType(request.getType());
        ct.setLanguage(request.getLanguage());
        ct.setStatus(request.getStatus());
        ct.setCode(request.getCode());

        if (request.getValues() != null) {
            List<CertificateTypeValue> values = request.getValues().stream()
                    .map(v -> {
                        CertificateTypeValue e = new CertificateTypeValue();
                        e.setValue(v.getValue());
                        e.setCertificateType(ct);
                        return e;
                    })
                    .toList();
            ct.getValues().clear();
            ct.getValues().addAll(values);
        }
        return ct;
    }

    public void updateEntityFromRequest(UpdateCertificateTypeRequest request, CertificateType ct) {
        if (request == null || ct == null) return;
        if (request.getType() != null) ct.setType(request.getType());
        if (request.getLanguage() != null) ct.setLanguage(request.getLanguage());
        if (request.getStatus() != null) ct.setStatus(request.getStatus());
        if (request.getCode() != null) ct.setCode(request.getCode());

        if (request.getValues() != null) {
            ct.getValues().clear();
            List<CertificateTypeValue> values = request.getValues().stream()
                    .map(v -> {
                        CertificateTypeValue e = new CertificateTypeValue();
                        e.setValue(v.getValue());
                        e.setCertificateType(ct);
                        return e;
                    })
                    .toList();
            ct.getValues().addAll(values);
        }
    }
}


