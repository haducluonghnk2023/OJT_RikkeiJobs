package com.data.db_rikkeijobs.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CertificateTypeValueRequest {
    @NotBlank(message = "Value is required")
    private String value;
}


