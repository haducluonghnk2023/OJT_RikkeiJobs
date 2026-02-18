package com.data.db_rikkeijobs.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CvResponse {
    private Long id;
    private Long languageId;
    private String language;
    private String title;
    private String pdf;
    private String pdfDataUrl;
    private Long userId;
    private String date;
    private Boolean status;
}


