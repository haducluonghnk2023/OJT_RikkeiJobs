package com.data.db_rikkeijobs.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateCvLanguageRequest {
    private String language;
    private String code;
    private Boolean status;
}


