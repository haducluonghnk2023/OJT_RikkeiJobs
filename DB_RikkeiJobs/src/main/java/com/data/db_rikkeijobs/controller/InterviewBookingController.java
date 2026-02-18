package com.data.db_rikkeijobs.controller;

import com.data.db_rikkeijobs.dto.request.CreateInterviewBookingRequest;
import com.data.db_rikkeijobs.dto.request.UpdateInterviewBookingRequest;
import com.data.db_rikkeijobs.dto.response.InterviewBookingResponse;
import com.data.db_rikkeijobs.dto.response.ResponseWrapper;
import com.data.db_rikkeijobs.entity.InterviewBooking;
import com.data.db_rikkeijobs.entity.InterviewBookingStatus;
import com.data.db_rikkeijobs.exception.HttpBadRequest;
import com.data.db_rikkeijobs.exception.HttpNotFound;
import com.data.db_rikkeijobs.mapper.InterviewBookingMapper;
import com.data.db_rikkeijobs.service.InterviewBookingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/interview-bookings")
@RequiredArgsConstructor
public class InterviewBookingController {

    private final InterviewBookingService interviewBookingService;
    private final InterviewBookingMapper interviewBookingMapper;

    @GetMapping
    public ResponseEntity<?> getAllInterviewBookings() {
        List<InterviewBookingResponse> interviewBookings = interviewBookingService.findAll().stream()
                .map(interviewBookingMapper::toResponse)
                .collect(Collectors.toList());
        
        return ResponseEntity.ok(
                ResponseWrapper.builder()
                        .status(HttpStatus.OK)
                        .code(HttpStatus.OK.value())
                        .data(interviewBookings)
                        .message("Interview bookings retrieved successfully")
                        .build()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getInterviewBookingById(@PathVariable Long id) {
        InterviewBookingResponse interviewBooking = interviewBookingService.findById(id)
                .map(interviewBookingMapper::toResponse)
                .orElseThrow(() -> new HttpNotFound("Interview booking not found with id: " + id));
        
        return ResponseEntity.ok(
                ResponseWrapper.builder()
                        .status(HttpStatus.OK)
                        .code(HttpStatus.OK.value())
                        .data(interviewBooking)
                        .message("Interview booking retrieved successfully")
                        .build()
        );
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getInterviewBookingsByUserId(@PathVariable Long userId) {
        List<InterviewBookingResponse> interviewBookings = interviewBookingService.findByUserId(userId).stream()
                .map(interviewBookingMapper::toResponse)
                .collect(Collectors.toList());
        
        return ResponseEntity.ok(
                ResponseWrapper.builder()
                        .status(HttpStatus.OK)
                        .code(HttpStatus.OK.value())
                        .data(interviewBookings)
                        .message("Interview bookings retrieved successfully")
                        .build()
        );
    }

    @GetMapping("/enterprise/{enterpriseId}")
    public ResponseEntity<?> getInterviewBookingsByEnterpriseId(@PathVariable Long enterpriseId) {
        List<InterviewBookingResponse> interviewBookings = interviewBookingService.findByEnterpriseId(enterpriseId).stream()
                .map(interviewBookingMapper::toResponse)
                .collect(Collectors.toList());
        
        return ResponseEntity.ok(
                ResponseWrapper.builder()
                        .status(HttpStatus.OK)
                        .code(HttpStatus.OK.value())
                        .data(interviewBookings)
                        .message("Interview bookings retrieved successfully")
                        .build()
        );
    }

    @GetMapping("/job/{jobId}")
    public ResponseEntity<?> getInterviewBookingsByJobId(@PathVariable Long jobId) {
        List<InterviewBookingResponse> interviewBookings = interviewBookingService.findByJobId(jobId).stream()
                .map(interviewBookingMapper::toResponse)
                .collect(Collectors.toList());
        
        return ResponseEntity.ok(
                ResponseWrapper.builder()
                        .status(HttpStatus.OK)
                        .code(HttpStatus.OK.value())
                        .data(interviewBookings)
                        .message("Interview bookings retrieved successfully")
                        .build()
        );
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<?> getInterviewBookingsByStatus(@PathVariable String status) {
        final InterviewBookingStatus parsedStatus;
        try {
            parsedStatus = InterviewBookingStatus.fromJson(status);
        } catch (IllegalArgumentException ex) {
            throw new HttpBadRequest(ex.getMessage());
        }

        List<InterviewBookingResponse> interviewBookings = interviewBookingService.findByStatus(parsedStatus).stream()
                .map(interviewBookingMapper::toResponse)
                .collect(Collectors.toList());
        
        return ResponseEntity.ok(
                ResponseWrapper.builder()
                        .status(HttpStatus.OK)
                        .code(HttpStatus.OK.value())
                        .data(interviewBookings)
                        .message("Interview bookings retrieved successfully")
                        .build()
        );
    }

    @PostMapping
    public ResponseEntity<?> createInterviewBooking(@Valid @RequestBody CreateInterviewBookingRequest request) {
        InterviewBooking interviewBooking = interviewBookingMapper.toEntity(request);
        InterviewBookingResponse createdInterviewBooking = interviewBookingMapper.toResponse(
                interviewBookingService.save(interviewBooking));
        
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ResponseWrapper.builder()
                        .status(HttpStatus.CREATED)
                        .code(HttpStatus.CREATED.value())
                        .data(createdInterviewBooking)
                        .message("Interview booking created successfully")
                        .build()
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateInterviewBooking(@PathVariable Long id, 
                                                  @Valid @RequestBody UpdateInterviewBookingRequest request) {
        InterviewBooking existingInterviewBooking = interviewBookingService.findById(id)
                .orElseThrow(() -> new HttpNotFound("Interview booking not found with id: " + id));
        
        interviewBookingMapper.updateEntityFromRequest(request, existingInterviewBooking);
        InterviewBookingResponse updatedInterviewBooking = interviewBookingMapper.toResponse(
                interviewBookingService.update(id, existingInterviewBooking));
        
        return ResponseEntity.ok(
                ResponseWrapper.builder()
                        .status(HttpStatus.OK)
                        .code(HttpStatus.OK.value())
                        .data(updatedInterviewBooking)
                        .message("Interview booking updated successfully")
                        .build()
        );
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> patchInterviewBooking(@PathVariable Long id, @RequestBody UpdateInterviewBookingRequest request) {
        InterviewBooking existingInterviewBooking = interviewBookingService.findById(id)
                .orElseThrow(() -> new HttpNotFound("Interview booking not found with id: " + id));
        
        interviewBookingMapper.updateEntityFromRequest(request, existingInterviewBooking);
        
        InterviewBookingResponse updatedInterviewBooking = interviewBookingMapper.toResponse(
                interviewBookingService.update(id, existingInterviewBooking));
        
        return ResponseEntity.ok(
                ResponseWrapper.builder()
                        .status(HttpStatus.OK)
                        .code(HttpStatus.OK.value())
                        .data(updatedInterviewBooking)
                        .message("Interview booking updated successfully")
                        .build()
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteInterviewBooking(@PathVariable Long id) {
        if (!interviewBookingService.findById(id).isPresent()) {
            throw new HttpNotFound("Interview booking not found with id: " + id);
        }
        
        interviewBookingService.deleteById(id);
        
        return ResponseEntity.ok(
                ResponseWrapper.builder()
                        .status(HttpStatus.OK)
                        .code(HttpStatus.OK.value())
                        .data("Interview booking deleted successfully")
                        .message("Interview booking deleted successfully")
                        .build()
        );
    }
}

