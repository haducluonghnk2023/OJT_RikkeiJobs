package com.data.db_rikkeijobs.controller;

import com.data.db_rikkeijobs.dto.response.ResponseWrapper;
import com.data.db_rikkeijobs.entity.Carousel;
import com.data.db_rikkeijobs.service.CarouselService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/carousel")
@RequiredArgsConstructor
public class CarouselController {

    private final CarouselService carouselService;

    @GetMapping
    public ResponseEntity<?> getAllCarousels(@RequestParam(required = false) String status) {
        List<Carousel> carousels = carouselService.getCarousels(status);
        return ResponseEntity.ok(
                ResponseWrapper.builder()
                        .status(HttpStatus.OK)
                        .code(HttpStatus.OK.value())
                        .data(carousels)
                        .message("Carousels retrieved successfully")
                        .build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCarouselById(@PathVariable Long id) {
        Carousel carousel = carouselService.getCarouselByIdOrThrow(id);
        return ResponseEntity.ok(
                ResponseWrapper.builder()
                        .status(HttpStatus.OK)
                        .code(HttpStatus.OK.value())
                        .data(carousel)
                        .message("Carousel retrieved successfully")
                        .build());
    }

    @PostMapping
    public ResponseEntity<?> createCarousel(@RequestBody Map<String, Object> request) {
        Carousel created = carouselService.createCarouselFromMap(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ResponseWrapper.builder()
                        .status(HttpStatus.CREATED)
                        .code(HttpStatus.CREATED.value())
                        .data(created)
                        .message("Carousel created successfully")
                        .build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCarousel(@PathVariable Long id, @RequestBody Map<String, Object> request) {
        Carousel updated = carouselService.updateCarouselFromMap(id, request);
        return ResponseEntity.ok(
                ResponseWrapper.builder()
                        .status(HttpStatus.OK)
                        .code(HttpStatus.OK.value())
                        .data(updated)
                        .message("Carousel updated successfully")
                        .build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCarousel(@PathVariable Long id) {
        carouselService.deleteCarouselOrThrow(id);
        return ResponseEntity.ok(
                ResponseWrapper.builder()
                        .status(HttpStatus.OK)
                        .code(HttpStatus.OK.value())
                        .data("Carousel deleted successfully")
                        .message("Carousel deleted successfully")
                        .build());
    }
}
