package com.data.db_rikkeijobs.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserCertificateRequest {
    @NotNull(message = "User ID is required")
    private Long userId;

    @NotNull(message = "Certificate ID is required")
    private Long certificateId;

    private String certificateType;
    private String certificateValue;
    private LocalDate receivedDate;
    private LocalDate expirationDate;
}


