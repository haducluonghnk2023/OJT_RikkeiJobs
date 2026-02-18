package com.data.db_rikkeijobs.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateCertificateTypeRequest {
    private String type;
    private String language;
    private Boolean status;
    private String code;
    private List<CertificateTypeValueRequest> values;
}


