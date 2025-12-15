package com.data.db_rikkeijobs.service.impl;

import com.data.db_rikkeijobs.entity.Carousel;
import com.data.db_rikkeijobs.repository.CarouselRepository;
import com.data.db_rikkeijobs.service.CarouselService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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
}

