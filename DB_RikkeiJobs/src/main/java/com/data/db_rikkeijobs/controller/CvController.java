package com.data.db_rikkeijobs.controller;

import com.data.db_rikkeijobs.dto.request.CreateCvRequest;
import com.data.db_rikkeijobs.dto.request.UpdateCvRequest;
import com.data.db_rikkeijobs.dto.response.CvResponse;
import com.data.db_rikkeijobs.dto.response.ResponseWrapper;
import com.data.db_rikkeijobs.service.CvService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cvs")
@RequiredArgsConstructor
public class CvController {

    private final CvService cvService;

    @GetMapping
    public ResponseEntity<?> getAllCvs() {
        List<CvResponse> cvs = cvService.getAllCvResponses();
        return ResponseEntity.ok(
                ResponseWrapper.builder()
                        .status(HttpStatus.OK)
                        .code(HttpStatus.OK.value())
                        .data(cvs)
                        .message("CVs retrieved successfully")
                        .build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCvById(@PathVariable Long id) {
        CvResponse cv = cvService.getCvResponseById(id);
        return ResponseEntity.ok(
                ResponseWrapper.builder()
                        .status(HttpStatus.OK)
                        .code(HttpStatus.OK.value())
                        .data(cv)
                        .message("CV retrieved successfully")
                        .build());
    }

    @PostMapping
    public ResponseEntity<?> createCv(@RequestBody CreateCvRequest request) {
        CvResponse created = cvService.createCv(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ResponseWrapper.builder()
                        .status(HttpStatus.CREATED)
                        .code(HttpStatus.CREATED.value())
                        .data(created)
                        .message("CV created successfully")
                        .build());
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> patchCv(@PathVariable Long id, @RequestBody UpdateCvRequest request) {
        CvResponse updatedCv = cvService.patchCv(id, request);
        return ResponseEntity.ok(
                ResponseWrapper.builder()
                        .status(HttpStatus.OK)
                        .code(HttpStatus.OK.value())
                        .data(updatedCv)
                        .message("CV updated successfully")
                        .build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCv(@PathVariable Long id) {
        cvService.deleteCvOrThrow(id);
        return ResponseEntity.ok(
                ResponseWrapper.builder()
                        .status(HttpStatus.OK)
                        .code(HttpStatus.OK.value())
                        .data("CV deleted successfully")
                        .message("CV deleted successfully")
                        .build());
    }
}
