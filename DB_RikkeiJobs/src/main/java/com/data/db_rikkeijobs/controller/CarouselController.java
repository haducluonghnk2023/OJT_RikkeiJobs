package com.data.db_rikkeijobs.controller;

import com.data.db_rikkeijobs.dto.response.ResponseWrapper;
import com.data.db_rikkeijobs.entity.Carousel;
import com.data.db_rikkeijobs.exception.HttpNotFound;
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
        List<Carousel> carousels;
        if (status != null && !status.isEmpty()) {
            carousels = carouselService.findByStatus(status);
        } else {
            carousels = carouselService.findAll();
        }
        
        return ResponseEntity.ok(
                ResponseWrapper.builder()
                        .status(HttpStatus.OK)
                        .code(HttpStatus.OK.value())
                        .data(carousels)
                        .message("Carousels retrieved successfully")
                        .build()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCarouselById(@PathVariable Long id) {
        Carousel carousel = carouselService.findById(id)
                .orElseThrow(() -> new HttpNotFound("Carousel not found with id: " + id));
        
        return ResponseEntity.ok(
                ResponseWrapper.builder()
                        .status(HttpStatus.OK)
                        .code(HttpStatus.OK.value())
                        .data(carousel)
                        .message("Carousel retrieved successfully")
                        .build()
        );
    }

    @PostMapping
    public ResponseEntity<?> createCarousel(@RequestBody Map<String, Object> request) {
        @SuppressWarnings("unchecked")
        Map<String, Object> newData = (Map<String, Object>) request.get("newData");
        
        Carousel carousel = new Carousel();
        if (newData != null) {
            if (newData.containsKey("title")) {
                carousel.setTitle((String) newData.get("title"));
            }
            if (newData.containsKey("index")) {
                carousel.setIndex(newData.get("index") instanceof Integer 
                    ? (Integer) newData.get("index") 
                    : Integer.valueOf(newData.get("index").toString()));
            }
            if (newData.containsKey("imgUrl")) {
                carousel.setImgUrl((String) newData.get("imgUrl"));
            }
            if (newData.containsKey("status")) {
                carousel.setStatus((String) newData.get("status"));
            }
        }
        
        Carousel createdCarousel = carouselService.save(carousel);
        
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ResponseWrapper.builder()
                        .status(HttpStatus.CREATED)
                        .code(HttpStatus.CREATED.value())
                        .data(createdCarousel)
                        .message("Carousel created successfully")
                        .build()
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCarousel(@PathVariable Long id, @RequestBody Map<String, Object> request) {
        Carousel existingCarousel = carouselService.findById(id)
                .orElseThrow(() -> new HttpNotFound("Carousel not found with id: " + id));
        
        @SuppressWarnings("unchecked")
        Map<String, Object> updatedData = (Map<String, Object>) request.get("updatedData");
        
        if (updatedData != null) {
            if (updatedData.containsKey("title")) {
                existingCarousel.setTitle((String) updatedData.get("title"));
            }
            if (updatedData.containsKey("index")) {
                existingCarousel.setIndex(updatedData.get("index") instanceof Integer 
                    ? (Integer) updatedData.get("index") 
                    : Integer.valueOf(updatedData.get("index").toString()));
            }
            if (updatedData.containsKey("imgUrl")) {
                existingCarousel.setImgUrl((String) updatedData.get("imgUrl"));
            }
            if (updatedData.containsKey("status")) {
                existingCarousel.setStatus((String) updatedData.get("status"));
            }
        }
        
        Carousel updatedCarousel = carouselService.update(id, existingCarousel);
        
        return ResponseEntity.ok(
                ResponseWrapper.builder()
                        .status(HttpStatus.OK)
                        .code(HttpStatus.OK.value())
                        .data(updatedCarousel)
                        .message("Carousel updated successfully")
                        .build()
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCarousel(@PathVariable Long id) {
        if (!carouselService.findById(id).isPresent()) {
            throw new HttpNotFound("Carousel not found with id: " + id);
        }
        
        carouselService.deleteById(id);
        
        return ResponseEntity.ok(
                ResponseWrapper.builder()
                        .status(HttpStatus.OK)
                        .code(HttpStatus.OK.value())
                        .data("Carousel deleted successfully")
                        .message("Carousel deleted successfully")
                        .build()
        );
    }
}

