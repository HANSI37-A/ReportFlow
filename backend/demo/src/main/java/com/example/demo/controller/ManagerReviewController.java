package com.example.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.ReviewHistoryResponse;
import com.example.demo.dto.ReviewReportRequest;
import com.example.demo.service.ManagerReviewService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/manager/reports")
public class ManagerReviewController {

    private final ManagerReviewService reviewService;

    public ManagerReviewController(ManagerReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping("/{reportId}/approve")
    public ResponseEntity<ReviewHistoryResponse> approveReport(
            @PathVariable Long reportId,
            @Valid @RequestBody(required = false) ReviewReportRequest request,
            Authentication authentication
    ) {
        Long managerId = extractUserId(authentication);

        return ResponseEntity.ok(
                reviewService.approveReport(
                        reportId,
                        managerId,
                        request
                )
        );
    }

    @PostMapping("/{reportId}/request-changes")
    public ResponseEntity<ReviewHistoryResponse> requestChanges(
            @PathVariable Long reportId,
            @Valid @RequestBody ReviewReportRequest request,
            Authentication authentication
    ) {
        Long managerId = extractUserId(authentication);

        return ResponseEntity.ok(
                reviewService.requestChanges(
                        reportId,
                        managerId,
                        request
                )
        );
    }

    private Long extractUserId(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new RuntimeException("User is not authenticated");
        }

        Object principal = authentication.getPrincipal();

        if (principal instanceof Long) {
            return (Long) principal;
        }

        throw new IllegalStateException("Unexpected authentication principal type: " + principal.getClass().getName());
    }
}