package com.data.db_rikkeijobs.mapper;

import com.data.db_rikkeijobs.dto.request.CreateCvRequest;
import com.data.db_rikkeijobs.dto.request.UpdateCvRequest;
import com.data.db_rikkeijobs.dto.response.CvResponse;
import com.data.db_rikkeijobs.entity.Cv;
import org.springframework.stereotype.Component;

@Component
public class CvMapper {

    public CvResponse toResponse(Cv cv) {
        if (cv == null) return null;
        return CvResponse.builder()
                .id(cv.getId())
                .languageId(cv.getLanguageId())
                .language(cv.getLanguage())
                .title(cv.getTitle())
                .pdf(cv.getPdf())
                .pdfDataUrl(cv.getPdfDataUrl())
                .userId(cv.getUserId())
                .date(cv.getDate())
                .status(cv.getStatus())
                .build();
    }

    public Cv toEntity(CreateCvRequest request) {
        if (request == null) return null;
        Cv cv = new Cv();
        cv.setUserId(request.getUserId());
        cv.setLanguageId(request.getLanguageId());
        cv.setLanguage(request.getLanguage());
        cv.setTitle(request.getTitle());
        cv.setPdf(request.getPdf());
        cv.setPdfDataUrl(request.getPdfDataUrl());
        cv.setDate(request.getDate());
        cv.setStatus(request.getStatus());
        return cv;
    }

    public void updateEntityFromRequest(UpdateCvRequest request, Cv cv) {
        if (request == null || cv == null) return;
        if (request.getUserId() != null) cv.setUserId(request.getUserId());
        if (request.getLanguageId() != null) cv.setLanguageId(request.getLanguageId());
        if (request.getLanguage() != null) cv.setLanguage(request.getLanguage());
        if (request.getTitle() != null) cv.setTitle(request.getTitle());
        if (request.getPdf() != null) cv.setPdf(request.getPdf());
        if (request.getPdfDataUrl() != null) cv.setPdfDataUrl(request.getPdfDataUrl());
        if (request.getDate() != null) cv.setDate(request.getDate());
        if (request.getStatus() != null) cv.setStatus(request.getStatus());
    }
}


