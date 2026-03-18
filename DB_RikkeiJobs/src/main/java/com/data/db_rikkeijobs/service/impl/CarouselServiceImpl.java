package com.data.db_rikkeijobs.service.impl;

import com.data.db_rikkeijobs.entity.Carousel;
import com.data.db_rikkeijobs.exception.HttpNotFound;
import com.data.db_rikkeijobs.repository.CarouselRepository;
import com.data.db_rikkeijobs.service.CarouselService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional
public class CarouselServiceImpl implements CarouselService {
    
    @Autowired
    private CarouselRepository carouselRepository;
    
    @Override
    public List<Carousel> findAll() {
        return carouselRepository.findAll();
    }
    
    @Override
    public Optional<Carousel> findById(Long id) {
        return carouselRepository.findById(id);
    }
    
    @Override
    public List<Carousel> findByStatus(String status) {
        return carouselRepository.findByStatus(status);
    }
    
    @Override
    public List<Carousel> findAllOrderByIndex() {
        return carouselRepository.findByOrderByIndexAsc();
    }
    
    @Override
    public Carousel save(Carousel carousel) {
        return carouselRepository.save(carousel);
    }
    
    @Override
    public Carousel update(Long id, Carousel carousel) {
        Optional<Carousel> existing = carouselRepository.findById(id);
        if (existing.isPresent()) {
            Carousel toUpdate = existing.get();
            toUpdate.setTitle(carousel.getTitle());
            toUpdate.setIndex(carousel.getIndex());
            toUpdate.setImgUrl(carousel.getImgUrl());
            toUpdate.setStatus(carousel.getStatus());
            return carouselRepository.save(toUpdate);
        }
        throw new RuntimeException("Carousel not found with id: " + id);
    }
    
    @Override
    public void deleteById(Long id) {
        carouselRepository.deleteById(id);
    }

    @Override
    public List<Carousel> getCarousels(String status) {
        if (status != null && !status.isEmpty()) {
            return carouselRepository.findByStatus(status);
        }
        return carouselRepository.findAll();
    }

    @Override
    public Carousel getCarouselByIdOrThrow(Long id) {
        return carouselRepository.findById(id)
                .orElseThrow(() -> new HttpNotFound("Carousel not found with id: " + id));
    }

    @Override
    @SuppressWarnings("unchecked")
    public Carousel createCarouselFromMap(Map<String, Object> request) {
        Map<String, Object> newData = (Map<String, Object>) request.get("newData");
        Carousel carousel = new Carousel();
        if (newData != null) {
            if (newData.containsKey("title")) carousel.setTitle((String) newData.get("title"));
            if (newData.containsKey("index")) {
                Object idx = newData.get("index");
                carousel.setIndex(idx instanceof Integer ? (Integer) idx : Integer.valueOf(idx.toString()));
            }
            if (newData.containsKey("imgUrl")) carousel.setImgUrl((String) newData.get("imgUrl"));
            if (newData.containsKey("status")) carousel.setStatus((String) newData.get("status"));
        }
        return save(carousel);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Carousel updateCarouselFromMap(Long id, Map<String, Object> request) {
        Carousel existing = getCarouselByIdOrThrow(id);
        Map<String, Object> updatedData = (Map<String, Object>) request.get("updatedData");
        if (updatedData != null) {
            if (updatedData.containsKey("title")) existing.setTitle((String) updatedData.get("title"));
            if (updatedData.containsKey("index")) {
                Object idx = updatedData.get("index");
                existing.setIndex(idx instanceof Integer ? (Integer) idx : Integer.valueOf(idx.toString()));
            }
            if (updatedData.containsKey("imgUrl")) existing.setImgUrl((String) updatedData.get("imgUrl"));
            if (updatedData.containsKey("status")) existing.setStatus((String) updatedData.get("status"));
        }
        return update(id, existing);
    }

    @Override
    public void deleteCarouselOrThrow(Long id) {
        if (!carouselRepository.existsById(id)) {
            throw new HttpNotFound("Carousel not found with id: " + id);
        }
        carouselRepository.deleteById(id);
    }
}

