package com.data.db_rikkeijobs.service;

import com.data.db_rikkeijobs.dto.request.CreateInterviewBookingRequest;
import com.data.db_rikkeijobs.dto.request.UpdateInterviewBookingRequest;
import com.data.db_rikkeijobs.dto.response.InterviewBookingResponse;
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

    List<InterviewBookingResponse> getAllInterviewBookingResponses();
    InterviewBookingResponse getInterviewBookingResponseById(Long id);
    List<InterviewBookingResponse> getInterviewBookingResponsesByUserId(Long userId);
    List<InterviewBookingResponse> getInterviewBookingResponsesByEnterpriseId(Long enterpriseId);
    List<InterviewBookingResponse> getInterviewBookingResponsesByJobId(Long jobId);
    List<InterviewBookingResponse> getInterviewBookingResponsesByStatus(InterviewBookingStatus status);
    List<InterviewBookingResponse> getInterviewBookingResponsesByStatusString(String status);
    InterviewBookingResponse createInterviewBooking(CreateInterviewBookingRequest request);
    InterviewBookingResponse updateInterviewBooking(Long id, UpdateInterviewBookingRequest request);
    InterviewBookingResponse patchInterviewBooking(Long id, UpdateInterviewBookingRequest request);
    void deleteInterviewBookingOrThrow(Long id);
}

