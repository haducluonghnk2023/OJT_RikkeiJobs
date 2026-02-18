package com.data.db_rikkeijobs.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateCertificateTypeRequest {
    @NotBlank(message = "Type is required")
    private String type;

    private String language;
    private Boolean status;
    private String code;

    // Matches frontend payload: values: [{ value: "7.0" }, ...]
    private List<CertificateTypeValueRequest> values;
}


