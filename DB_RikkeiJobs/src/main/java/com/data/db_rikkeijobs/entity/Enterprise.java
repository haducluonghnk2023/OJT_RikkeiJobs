package com.data.db_rikkeijobs.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "enterprises")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Enterprise {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "title")
    private String title;
    
    @Column(name = "email")
    private String email;
    
    @Column(name = "company_size")
    private String companySize;
    
    @Column(name = "phone_number")
    private String phoneNumber;
    
    @Column(name = "industry")
    private String industry;
    
    @Column(name = "introduction", columnDefinition = "TEXT")
    private String introduction;
    
    @Column(name = "website_url", columnDefinition = "TEXT")
    private String websiteUrl;
    
    @Column(name = "facebook_url", columnDefinition = "TEXT")
    private String facebookUrl;
    
    @Column(name = "linkedin_url", columnDefinition = "TEXT")
    private String linkedinUrl;
    
    @Column(name = "twitter_url", columnDefinition = "TEXT")
    private String twitterUrl;
    
    @Column(name = "business_license")
    private String businessLicense;
    
    @Column(name = "address")
    private String address;
    
    @Column(name = "user_id")
    private Long userId;
    
    @Column(name = "avatar", columnDefinition = "TEXT")
    private String avatar;
    
    @Column(name = "status")
    private String status;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;
}

