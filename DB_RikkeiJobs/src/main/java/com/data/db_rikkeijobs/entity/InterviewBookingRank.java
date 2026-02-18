package com.data.db_rikkeijobs.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "interview_booking_ranks")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InterviewBookingRank {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "rank_value", nullable = false)
    private String rankValue;
    
    // Quan hệ với InterviewBooking
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "interview_booking_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private InterviewBooking interviewBooking;
}

