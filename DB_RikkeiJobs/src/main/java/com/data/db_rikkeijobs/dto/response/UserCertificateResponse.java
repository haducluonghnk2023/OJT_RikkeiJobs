package com.data.db_rikkeijobs.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserCertificateResponse {
    private Long id;
    private String certificateType;
    private String certificateValue;
    private LocalDate receivedDate;
    private LocalDate expirationDate;
    private Long userId;
    private Long certificateId;
}


