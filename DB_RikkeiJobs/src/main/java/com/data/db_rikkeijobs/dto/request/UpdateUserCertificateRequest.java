package com.data.db_rikkeijobs.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserCertificateRequest {
    private Long userId;
    private Long certificateId;
    private String certificateType;
    private String certificateValue;
    private LocalDate receivedDate;
    private LocalDate expirationDate;
}


