package com.data.db_rikkeijobs.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_save_jobs", 
       uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "job_id"}))
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserSaveJob {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    // FK column is derived from `user` relation when persisting.
    // Keep a read-only copy for querying/serialization.
    @Column(name = "user_id", insertable = false, updatable = false)
    private Long userId;
    
    @Column(name = "job_id", nullable = false)
    private Long jobId;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    // Quan hệ với User
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;
    
    // Quan hệ với Job
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "job_id", insertable = false, updatable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Job job;
}

