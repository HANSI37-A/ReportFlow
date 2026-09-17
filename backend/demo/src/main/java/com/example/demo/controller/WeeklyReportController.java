package com.example.demo.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.WeeklyReportRequest;
import com.example.demo.dto.WeeklyReportResponse;
import com.example.demo.service.WeeklyReportService;
import org.springframework.web.server.ResponseStatusException;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/member/reports")
public class WeeklyReportController {

    private final WeeklyReportService reportService;

    public WeeklyReportController(
            WeeklyReportService reportService
    ) {
        this.reportService = reportService;
    }

    private Long getAuthenticatedUserId(Authentication authentication) {
        if (authentication == null || !(authentication.getPrincipal() instanceof Long userId)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized");
        }
        return userId;
    }

    @PostMapping
    public ResponseEntity<WeeklyReportResponse> createReport(
            @Valid @RequestBody WeeklyReportRequest request,
            Authentication authentication
    ) {

        Long userId = getAuthenticatedUserId(authentication);

        WeeklyReportResponse response =
                reportService.createReport(userId, request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping
    public ResponseEntity<List<WeeklyReportResponse>> getMyReports(
            Authentication authentication
    ) {

        Long userId = getAuthenticatedUserId(authentication);

        return ResponseEntity.ok(
                reportService.getMyReports(userId)
        );
    }

    @GetMapping("/{reportId}")
    public ResponseEntity<WeeklyReportResponse> getMyReport(
            @PathVariable Long reportId,
            Authentication authentication
    ) {

        Long userId = getAuthenticatedUserId(authentication);

        return ResponseEntity.ok(
                reportService.getMyReport(reportId, userId)
        );
    }

    @PutMapping("/{reportId}")
    public ResponseEntity<WeeklyReportResponse> updateMyReport(
            @PathVariable Long reportId,
            @Valid @RequestBody WeeklyReportRequest request,
            Authentication authentication
    ) {

        Long userId = getAuthenticatedUserId(authentication);

        return ResponseEntity.ok(
                reportService.updateMyReport(
                        reportId,
                        userId,
                        request
                )
        );
    }

    @PostMapping("/{reportId}/submit")
    public ResponseEntity<Void> submitReport(
            @PathVariable Long reportId,
            Authentication authentication
    ) {

        Long userId = getAuthenticatedUserId(authentication);

        reportService.submitMyReport(reportId, userId);

        return ResponseEntity.noContent().build();
    }
}