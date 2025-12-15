package com.data.db_rikkeijobs.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "certificate_types")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CertificateType {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "type")
    private String type;
    
    // Quan hệ One-to-Many với CertificateTypeValue
    @OneToMany(mappedBy = "certificateType", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CertificateTypeValue> values = new ArrayList<>();
    
    @Column(name = "language")
    private String language;
    
    @Column(name = "status")
    private Boolean status;
    
    @Column(name = "code")
    private String code;
}

