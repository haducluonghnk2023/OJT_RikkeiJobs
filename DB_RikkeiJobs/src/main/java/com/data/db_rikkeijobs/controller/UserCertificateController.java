package com.data.db_rikkeijobs.controller;

import com.data.db_rikkeijobs.dto.request.CreateUserCertificateRequest;
import com.data.db_rikkeijobs.dto.request.UpdateUserCertificateRequest;
import com.data.db_rikkeijobs.dto.response.ResponseWrapper;
import com.data.db_rikkeijobs.dto.response.UserCertificateResponse;
import com.data.db_rikkeijobs.service.UserCertificateService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/UserCertificates")
@RequiredArgsConstructor
public class UserCertificateController {

    private final UserCertificateService userCertificateService;

    @GetMapping
    public ResponseEntity<?> getAllUserCertificates(
            @RequestParam(value = "userId", required = false) Long userId,
            @RequestParam(value = "certificateId", required = false) Long certificateId) {
        List<UserCertificateResponse> data = userCertificateService.getUserCertificateResponses(userId, certificateId);
        return ResponseEntity.ok(
                ResponseWrapper.builder()
                        .status(HttpStatus.OK)
                        .code(HttpStatus.OK.value())
                        .data(data)
                        .message("User certificates retrieved successfully")
                        .build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserCertificateById(@PathVariable Long id) {
        UserCertificateResponse data = userCertificateService.getUserCertificateResponseById(id);
        return ResponseEntity.ok(
                ResponseWrapper.builder()
                        .status(HttpStatus.OK)
                        .code(HttpStatus.OK.value())
                        .data(data)
                        .message("User certificate retrieved successfully")
                        .build());
    }

    @PostMapping
    public ResponseEntity<?> createUserCertificate(@Valid @RequestBody CreateUserCertificateRequest request) {
        UserCertificateResponse data = userCertificateService.createUserCertificate(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ResponseWrapper.builder()
                        .status(HttpStatus.CREATED)
                        .code(HttpStatus.CREATED.value())
                        .data(data)
                        .message("User certificate created successfully")
                        .build());
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> patchUserCertificate(@PathVariable Long id, @RequestBody UpdateUserCertificateRequest request) {
        UserCertificateResponse data = userCertificateService.patchUserCertificate(id, request);
        return ResponseEntity.ok(
                ResponseWrapper.builder()
                        .status(HttpStatus.OK)
                        .code(HttpStatus.OK.value())
                        .data(data)
                        .message("User certificate updated successfully")
                        .build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUserCertificate(@PathVariable Long id) {
        userCertificateService.deleteUserCertificateOrThrow(id);
        return ResponseEntity.ok(
                ResponseWrapper.builder()
                        .status(HttpStatus.OK)
                        .code(HttpStatus.OK.value())
                        .data("User certificate deleted successfully")
                        .message("User certificate deleted successfully")
                        .build());
    }
}
