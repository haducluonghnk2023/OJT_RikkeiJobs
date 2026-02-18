package com.data.db_rikkeijobs.controller;

import com.data.db_rikkeijobs.dto.response.ResponseWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Collections;

/**
 * Proxy endpoint to avoid browser CORS when calling provinces.open-api.vn directly from frontend.
 */
@RestController
@RequestMapping("/api/v1/provinces")
@RequiredArgsConstructor
@Slf4j
public class ProvinceController {

    private final RestClient restClient = RestClient.create("https://provinces.open-api.vn");
    private final ObjectMapper objectMapper = new ObjectMapper();

    @GetMapping
    public ResponseEntity<?> getAllProvinces() {
        // Prefer a bundled cached list (stable, no outbound dependency)
        // If you want to refresh, you can still try the external API as a fallback.
        List<?> provinces = null;
        try {
            provinces = objectMapper.readValue(
                    new ClassPathResource("data/provinces.json").getInputStream(),
                    List.class
            );
        } catch (Exception e) {
            log.warn("Failed to read cached provinces.json, trying external API", e);
        }

        if (provinces == null) {
            // Fallback to external API (may fail in restricted environments)
            try {
                provinces = restClient.get()
                        .uri("/api/provinces")
                        .retrieve()
                        .body(List.class);
            } catch (Exception e) {
                log.warn("Failed to fetch provinces from external API; returning empty list to avoid breaking UI", e);
                provinces = Collections.emptyList();
            }
        }

        return ResponseEntity.ok(
                ResponseWrapper.builder()
                        .status(HttpStatus.OK)
                        .code(HttpStatus.OK.value())
                        .data(provinces)
                        .message("Provinces retrieved successfully")
                        .build()
        );
    }
}


