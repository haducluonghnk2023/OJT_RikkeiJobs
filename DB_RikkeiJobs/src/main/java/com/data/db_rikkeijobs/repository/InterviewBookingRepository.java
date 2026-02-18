package com.data.db_rikkeijobs.repository;

import com.data.db_rikkeijobs.entity.InterviewBooking;
import com.data.db_rikkeijobs.entity.InterviewBookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InterviewBookingRepository extends JpaRepository<InterviewBooking, Long> {
    List<InterviewBooking> findByUserId(Long userId);
    List<InterviewBooking> findByEnterpriseId(Long enterpriseId);
    List<InterviewBooking> findByJobId(Long jobId);
    List<InterviewBooking> findByStatus(InterviewBookingStatus status);
}

