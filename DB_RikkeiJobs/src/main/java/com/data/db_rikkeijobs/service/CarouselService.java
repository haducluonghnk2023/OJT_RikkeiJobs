package com.data.db_rikkeijobs.service;

import com.data.db_rikkeijobs.entity.Carousel;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface CarouselService {
    List<Carousel> findAll();
    Optional<Carousel> findById(Long id);
    List<Carousel> findByStatus(String status);
    List<Carousel> findAllOrderByIndex();
    Carousel save(Carousel carousel);
    Carousel update(Long id, Carousel carousel);
    void deleteById(Long id);

    List<Carousel> getCarousels(String status);
    Carousel getCarouselByIdOrThrow(Long id);
    Carousel createCarouselFromMap(Map<String, Object> request);
    Carousel updateCarouselFromMap(Long id, Map<String, Object> request);
    void deleteCarouselOrThrow(Long id);
}

