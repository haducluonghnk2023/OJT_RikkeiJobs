package com.data.db_rikkeijobs.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "certificate_type_values")
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class CertificateTypeValue {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "certificate_type_id", nullable = false)
    private Long certificateTypeId;
    
    @Column(name = "value", nullable = false)
    private String value;
    
    // Quan hệ với CertificateType
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "certificate_type_id", insertable = false, updatable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonBackReference
    private CertificateType certificateType;
}

