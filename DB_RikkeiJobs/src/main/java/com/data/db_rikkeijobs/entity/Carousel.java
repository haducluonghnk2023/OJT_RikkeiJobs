package com.data.db_rikkeijobs.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "carousel")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Carousel {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "title")
    private String title;
    
    @Column(name = "`index`")
    private Integer index;
    
    @Column(name = "img_url", columnDefinition = "TEXT")
    private String imgUrl;
    
    @Column(name = "status")
    private String status;
}

