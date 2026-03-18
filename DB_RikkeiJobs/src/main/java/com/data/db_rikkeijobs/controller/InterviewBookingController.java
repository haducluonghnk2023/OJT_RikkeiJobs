package com.data.db_rikkeijobs.controller;

import com.data.db_rikkeijobs.dto.request.CreateInterviewBookingRequest;
import com.data.db_rikkeijobs.dto.request.UpdateInterviewBookingRequest;
import com.data.db_rikkeijobs.dto.response.InterviewBookingResponse;
import com.data.db_rikkeijobs.dto.response.ResponseWrapper;
import com.data.db_rikkeijobs.service.InterviewBookingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/interview-bookings")
@RequiredArgsConstructor
public class InterviewBookingController {

    private final InterviewBookingService interviewBookingService;

    @GetMapping
    public ResponseEntity<?> getAllInterviewBookings() {
        List<InterviewBookingResponse> interviewBookings = interviewBookingService.getAllInterviewBookingResponses();
        return ResponseEntity.ok(
                ResponseWrapper.builder()
                        .status(HttpStatus.OK)
                        .code(HttpStatus.OK.value())
                        .data(interviewBookings)
                        .message("Interview bookings retrieved successfully")
                        .build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getInterviewBookingById(@PathVariable Long id) {
        InterviewBookingResponse interviewBooking = interviewBookingService.getInterviewBookingResponseById(id);
        return ResponseEntity.ok(
                ResponseWrapper.builder()
                        .status(HttpStatus.OK)
                        .code(HttpStatus.OK.value())
                        .data(interviewBooking)
                        .message("Interview booking retrieved successfully")
                        .build());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getInterviewBookingsByUserId(@PathVariable Long userId) {
        List<InterviewBookingResponse> interviewBookings = interviewBookingService.getInterviewBookingResponsesByUserId(userId);
        return ResponseEntity.ok(
                ResponseWrapper.builder()
                        .status(HttpStatus.OK)
                        .code(HttpStatus.OK.value())
                        .data(interviewBookings)
                        .message("Interview bookings retrieved successfully")
                        .build());
    }

    @GetMapping("/enterprise/{enterpriseId}")
    public ResponseEntity<?> getInterviewBookingsByEnterpriseId(@PathVariable Long enterpriseId) {
        List<InterviewBookingResponse> interviewBookings = interviewBookingService.getInterviewBookingResponsesByEnterpriseId(enterpriseId);
        return ResponseEntity.ok(
                ResponseWrapper.builder()
                        .status(HttpStatus.OK)
                        .code(HttpStatus.OK.value())
                        .data(interviewBookings)
                        .message("Interview bookings retrieved successfully")
                        .build());
    }

    @GetMapping("/job/{jobId}")
    public ResponseEntity<?> getInterviewBookingsByJobId(@PathVariable Long jobId) {
        List<InterviewBookingResponse> interviewBookings = interviewBookingService.getInterviewBookingResponsesByJobId(jobId);
        return ResponseEntity.ok(
                ResponseWrapper.builder()
                        .status(HttpStatus.OK)
                        .code(HttpStatus.OK.value())
                        .data(interviewBookings)
                        .message("Interview bookings retrieved successfully")
                        .build());
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<?> getInterviewBookingsByStatus(@PathVariable String status) {
        List<InterviewBookingResponse> interviewBookings = interviewBookingService.getInterviewBookingResponsesByStatusString(status);
        return ResponseEntity.ok(
                ResponseWrapper.builder()
                        .status(HttpStatus.OK)
                        .code(HttpStatus.OK.value())
                        .data(interviewBookings)
                        .message("Interview bookings retrieved successfully")
                        .build());
    }

    @PostMapping
    public ResponseEntity<?> createInterviewBooking(@Valid @RequestBody CreateInterviewBookingRequest request) {
        InterviewBookingResponse created = interviewBookingService.createInterviewBooking(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ResponseWrapper.builder()
                        .status(HttpStatus.CREATED)
                        .code(HttpStatus.CREATED.value())
                        .data(created)
                        .message("Interview booking created successfully")
                        .build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateInterviewBooking(@PathVariable Long id,
                                                    @Valid @RequestBody UpdateInterviewBookingRequest request) {
        InterviewBookingResponse updated = interviewBookingService.updateInterviewBooking(id, request);
        return ResponseEntity.ok(
                ResponseWrapper.builder()
                        .status(HttpStatus.OK)
                        .code(HttpStatus.OK.value())
                        .data(updated)
                        .message("Interview booking updated successfully")
                        .build());
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> patchInterviewBooking(@PathVariable Long id, @RequestBody UpdateInterviewBookingRequest request) {
        InterviewBookingResponse updated = interviewBookingService.patchInterviewBooking(id, request);
        return ResponseEntity.ok(
                ResponseWrapper.builder()
                        .status(HttpStatus.OK)
                        .code(HttpStatus.OK.value())
                        .data(updated)
                        .message("Interview booking updated successfully")
                        .build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteInterviewBooking(@PathVariable Long id) {
        interviewBookingService.deleteInterviewBookingOrThrow(id);
        return ResponseEntity.ok(
                ResponseWrapper.builder()
                        .status(HttpStatus.OK)
                        .code(HttpStatus.OK.value())
                        .data("Interview booking deleted successfully")
                        .message("Interview booking deleted successfully")
                        .build());
    }
}
