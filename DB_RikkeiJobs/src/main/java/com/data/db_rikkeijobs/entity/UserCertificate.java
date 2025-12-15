package com.data.db_rikkeijobs.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "user_certificates")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserCertificate {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "certificate_type")
    private String certificateType;
    
    @Column(name = "certificate_value")
    private String certificateValue;
    
    @Column(name = "received_date")
    private LocalDate receivedDate;
    
    @Column(name = "expiration_date")
    private LocalDate expirationDate;
    
    @Column(name = "user_id")
    private Long userId;
    
    @Column(name = "certificate_id")
    private Long certificateId;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "certificate_id", insertable = false, updatable = false)
    private CertificateType certificateTypeEntity;
}

