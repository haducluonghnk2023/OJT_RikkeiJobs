package com.data.db_rikkeijobs.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "cv_languages")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CvLanguage {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "language")
    private String language;
    
    @Column(name = "code")
    private String code;
    
    @Column(name = "status")
    private Boolean status;
}

