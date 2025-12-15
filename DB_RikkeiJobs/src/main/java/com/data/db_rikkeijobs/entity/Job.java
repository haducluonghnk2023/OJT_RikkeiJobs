package com.data.db_rikkeijobs.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "jobs")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Job {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "title")
    private String title;
    
    @Column(name = "quantity")
    private Integer quantity;
    
    // Quan hệ One-to-Many với JobDescription
    @OneToMany(mappedBy = "job", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<JobDescription> descriptions = new ArrayList<>();
    
    // Quan hệ One-to-Many với JobRank
    @OneToMany(mappedBy = "job", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<JobRank> ranks = new ArrayList<>();
    
    @Column(name = "gender")
    private String gender;
    
    @Column(name = "skills")
    private String skills;
    
    @Column(name = "salary_current")
    private String salaryCurrent;
    
    @Column(name = "salary")
    private String salary;
    
    @Column(name = "province")
    private String province;
    
    @Column(name = "district")
    private String district;
    
    @Column(name = "image", columnDefinition = "TEXT")
    private String image;
    
    @Column(name = "address")
    private String address;
    
    // Quan hệ One-to-Many với JobBenefit
    @OneToMany(mappedBy = "job", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<JobBenefit> benefits = new ArrayList<>();
    
    @Column(name = "working_time")
    private String workingTime;
    
    @Column(name = "deadline")
    private String deadline;
    
    // Quan hệ One-to-Many với JobRequirement
    @OneToMany(mappedBy = "job", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<JobRequirement> requirements = new ArrayList<>();
    
    @Column(name = "industry")
    private String industry;
    
    @Column(name = "enterprise_id")
    private Long enterpriseId;
    
    @Column(name = "flight")
    private String flight;
    
    @Column(name = "update_date")
    private LocalDateTime updateDate;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "enterprise_id", insertable = false, updatable = false)
    private Enterprise enterprise;
    
    // Quan hệ One-to-Many với UserSaveJob (để biết user nào đã lưu job này)
    @OneToMany(mappedBy = "job", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserSaveJob> savedByUsers = new ArrayList<>();
}

