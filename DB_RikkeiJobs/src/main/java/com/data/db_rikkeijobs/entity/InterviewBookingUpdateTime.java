package com.data.db_rikkeijobs.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "interview_booking_update_times")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InterviewBookingUpdateTime {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "update_time", nullable = false)
    private String updateTime;
    
    // Quan hệ với InterviewBooking
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "interview_booking_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private InterviewBooking interviewBooking;
}

