package com.data.db_rikkeijobs.service.impl;

import com.data.db_rikkeijobs.dto.request.CreateInterviewBookingRequest;
import com.data.db_rikkeijobs.dto.request.UpdateInterviewBookingRequest;
import com.data.db_rikkeijobs.dto.response.InterviewBookingResponse;
import com.data.db_rikkeijobs.entity.InterviewBooking;
import com.data.db_rikkeijobs.entity.InterviewBookingStatus;
import com.data.db_rikkeijobs.exception.HttpBadRequest;
import com.data.db_rikkeijobs.exception.HttpNotFound;
import com.data.db_rikkeijobs.mapper.InterviewBookingMapper;
import com.data.db_rikkeijobs.repository.InterviewBookingRepository;
import com.data.db_rikkeijobs.service.InterviewBookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class InterviewBookingServiceImpl implements InterviewBookingService {
    
    @Autowired
    private InterviewBookingRepository interviewBookingRepository;

    @Autowired
    private InterviewBookingMapper interviewBookingMapper;
    
    @Override
    public List<InterviewBooking> findAll() {
        return interviewBookingRepository.findAll();
    }
    
    @Override
    public Optional<InterviewBooking> findById(Long id) {
        return interviewBookingRepository.findById(id);
    }
    
    @Override
    public List<InterviewBooking> findByUserId(Long userId) {
        return interviewBookingRepository.findByUserId(userId);
    }
    
    @Override
    public List<InterviewBooking> findByEnterpriseId(Long enterpriseId) {
        return interviewBookingRepository.findByEnterpriseId(enterpriseId);
    }
    
    @Override
    public List<InterviewBooking> findByJobId(Long jobId) {
        return interviewBookingRepository.findByJobId(jobId);
    }
    
    @Override
    public List<InterviewBooking> findByStatus(InterviewBookingStatus status) {
        return interviewBookingRepository.findByStatus(status);
    }
    
    @Override
    public InterviewBooking save(InterviewBooking interviewBooking) {
        return interviewBookingRepository.save(interviewBooking);
    }
    
    @Override
    public InterviewBooking update(Long id, InterviewBooking interviewBooking) {
        Optional<InterviewBooking> existing = interviewBookingRepository.findById(id);
        if (existing.isPresent()) {
            InterviewBooking toUpdate = existing.get();
            toUpdate.setEnterpriseId(interviewBooking.getEnterpriseId());
            toUpdate.setJobId(interviewBooking.getJobId());
            toUpdate.setTime(interviewBooking.getTime());
            toUpdate.setDate(interviewBooking.getDate());
            toUpdate.setUserId(interviewBooking.getUserId());
            toUpdate.setStatus(interviewBooking.getStatus());
            toUpdate.setMeetingLink(interviewBooking.getMeetingLink());
            toUpdate.setUpdateStatusTimes(interviewBooking.getUpdateStatusTimes());
            toUpdate.setCancelReason(interviewBooking.getCancelReason());
            toUpdate.setInterviewMode(interviewBooking.getInterviewMode());
            toUpdate.setDescription(interviewBooking.getDescription());
            toUpdate.setRanks(interviewBooking.getRanks());
            toUpdate.setSkills(interviewBooking.getSkills());
            toUpdate.setProvince(interviewBooking.getProvince());
            toUpdate.setDistrict(interviewBooking.getDistrict());
            toUpdate.setAddress(interviewBooking.getAddress());
            toUpdate.setBenefitsDescription(interviewBooking.getBenefitsDescription());
            toUpdate.setWorkingTime(interviewBooking.getWorkingTime());
            return interviewBookingRepository.save(toUpdate);
        }
        throw new RuntimeException("InterviewBooking not found with id: " + id);
    }
    
    @Override
    public void deleteById(Long id) {
        interviewBookingRepository.deleteById(id);
    }

    @Override
    public List<InterviewBookingResponse> getAllInterviewBookingResponses() {
        return interviewBookingRepository.findAll().stream()
                .map(interviewBookingMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public InterviewBookingResponse getInterviewBookingResponseById(Long id) {
        return interviewBookingRepository.findById(id)
                .map(interviewBookingMapper::toResponse)
                .orElseThrow(() -> new HttpNotFound("Interview booking not found with id: " + id));
    }

    @Override
    public List<InterviewBookingResponse> getInterviewBookingResponsesByUserId(Long userId) {
        return interviewBookingRepository.findByUserId(userId).stream()
                .map(interviewBookingMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<InterviewBookingResponse> getInterviewBookingResponsesByEnterpriseId(Long enterpriseId) {
        return interviewBookingRepository.findByEnterpriseId(enterpriseId).stream()
                .map(interviewBookingMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<InterviewBookingResponse> getInterviewBookingResponsesByJobId(Long jobId) {
        return interviewBookingRepository.findByJobId(jobId).stream()
                .map(interviewBookingMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<InterviewBookingResponse> getInterviewBookingResponsesByStatus(InterviewBookingStatus status) {
        return interviewBookingRepository.findByStatus(status).stream()
                .map(interviewBookingMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<InterviewBookingResponse> getInterviewBookingResponsesByStatusString(String status) {
        InterviewBookingStatus parsedStatus;
        try {
            parsedStatus = InterviewBookingStatus.fromJson(status);
        } catch (IllegalArgumentException ex) {
            throw new HttpBadRequest(ex.getMessage());
        }
        return getInterviewBookingResponsesByStatus(parsedStatus);
    }

    @Override
    public InterviewBookingResponse createInterviewBooking(CreateInterviewBookingRequest request) {
        InterviewBooking interviewBooking = interviewBookingMapper.toEntity(request);
        return interviewBookingMapper.toResponse(save(interviewBooking));
    }

    @Override
    public InterviewBookingResponse updateInterviewBooking(Long id, UpdateInterviewBookingRequest request) {
        InterviewBooking existing = interviewBookingRepository.findById(id)
                .orElseThrow(() -> new HttpNotFound("Interview booking not found with id: " + id));
        interviewBookingMapper.updateEntityFromRequest(request, existing);
        return interviewBookingMapper.toResponse(update(id, existing));
    }

    @Override
    public InterviewBookingResponse patchInterviewBooking(Long id, UpdateInterviewBookingRequest request) {
        InterviewBooking existing = interviewBookingRepository.findById(id)
                .orElseThrow(() -> new HttpNotFound("Interview booking not found with id: " + id));
        interviewBookingMapper.updateEntityFromRequest(request, existing);
        return interviewBookingMapper.toResponse(update(id, existing));
    }

    @Override
    public void deleteInterviewBookingOrThrow(Long id) {
        if (!interviewBookingRepository.existsById(id)) {
            throw new HttpNotFound("Interview booking not found with id: " + id);
        }
        interviewBookingRepository.deleteById(id);
    }
}

