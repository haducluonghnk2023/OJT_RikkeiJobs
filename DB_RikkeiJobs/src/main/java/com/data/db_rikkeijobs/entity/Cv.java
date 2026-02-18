package com.data.db_rikkeijobs.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "cvs")
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Cv {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "language_id")
    private Long languageId;
    
    @Column(name = "language")
    private String language;
    
    @Column(name = "title")
    private String title;
    
    @Column(name = "pdf")
    private String pdf;
    
    @Column(name = "pdf_data_url", columnDefinition = "TEXT")
    private String pdfDataUrl;
    
    @Column(name = "user_id")
    private Long userId;
    
    @Column(name = "date")
    private String date;
    
    @Column(name = "status")
    private Boolean status;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "language_id", insertable = false, updatable = false)
    @JsonIgnore
    private CvLanguage cvLanguage;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    @JsonIgnore
    private User user;
}

