package com.data.db_rikkeijobs.repository;

import com.data.db_rikkeijobs.entity.Carousel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarouselRepository extends JpaRepository<Carousel, Long> {
    List<Carousel> findByStatus(String status);
    List<Carousel> findByOrderByIndexAsc();
}

