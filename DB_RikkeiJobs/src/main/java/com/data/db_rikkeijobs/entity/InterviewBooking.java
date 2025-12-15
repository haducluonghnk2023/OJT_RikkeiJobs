package com.data.db_rikkeijobs.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "interview_bookings")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InterviewBooking {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "enterprise_id")
    private Long enterpriseId;
    
    @Column(name = "job_id")
    private Long jobId;
    
    @Column(name = "time")
    private String time;
    
    @Column(name = "date")
    private String date;
    
    @Column(name = "user_id")
    private Long userId;
    
    @Column(name = "status")
    private String status;
    
    @Column(name = "create_at")
    private LocalDateTime createAt;
    
    @Column(name = "meeting_link", columnDefinition = "TEXT")
    private String meetingLink;
    
    // Quan hệ One-to-Many với InterviewBookingUpdateTime
    @OneToMany(mappedBy = "interviewBooking", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<InterviewBookingUpdateTime> updateStatusTimes = new ArrayList<>();
    
    @Column(name = "cancel_reason", columnDefinition = "TEXT")
    private String cancelReason;
    
    @Column(name = "interview_mode")
    private String interviewMode;
    
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;
    
    // Quan hệ One-to-Many với InterviewBookingRank
    @OneToMany(mappedBy = "interviewBooking", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<InterviewBookingRank> ranks = new ArrayList<>();
    
    @Column(name = "skills")
    private String skills;
    
    @Column(name = "province")
    private String province;
    
    @Column(name = "district")
    private String district;
    
    @Column(name = "address")
    private String address;
    
    @Column(name = "benefits_description", columnDefinition = "TEXT")
    private String benefitsDescription;
    
    @Column(name = "working_time")
    private String workingTime;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "enterprise_id", insertable = false, updatable = false)
    private Enterprise enterprise;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "job_id", insertable = false, updatable = false)
    private Job job;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;
}

