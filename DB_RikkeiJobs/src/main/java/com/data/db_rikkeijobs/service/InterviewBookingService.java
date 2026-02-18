package com.data.db_rikkeijobs.service;

import com.data.db_rikkeijobs.entity.InterviewBooking;
import com.data.db_rikkeijobs.entity.InterviewBookingStatus;

import java.util.List;
import java.util.Optional;

public interface InterviewBookingService {
    List<InterviewBooking> findAll();
    Optional<InterviewBooking> findById(Long id);
    List<InterviewBooking> findByUserId(Long userId);
    List<InterviewBooking> findByEnterpriseId(Long enterpriseId);
    List<InterviewBooking> findByJobId(Long jobId);
    List<InterviewBooking> findByStatus(InterviewBookingStatus status);
    InterviewBooking save(InterviewBooking interviewBooking);
    InterviewBooking update(Long id, InterviewBooking interviewBooking);
    void deleteById(Long id);
}

