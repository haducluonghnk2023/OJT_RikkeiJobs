package com.data.db_rikkeijobs.dto.request;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateCvRequest {
    private Long userId;
    private Long languageId;
    private String language;

    @Size(max = 255, message = "Title must not exceed 255 characters")
    private String title;

    private String pdf;
    private String pdfDataUrl;
    private String date;
    private Boolean status;
}


