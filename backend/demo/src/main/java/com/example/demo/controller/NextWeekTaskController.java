package com.example.demo.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.NextWeekTaskRequest;
import com.example.demo.dto.NextWeekTaskResponse;
import com.example.demo.service.NextWeekTaskService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/member/reports/{reportId}/next-week-tasks")
public class NextWeekTaskController {

    private final NextWeekTaskService taskService;

    public NextWeekTaskController(NextWeekTaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    public ResponseEntity<NextWeekTaskResponse> addTask(
            @PathVariable Long reportId,
            @Valid @RequestBody NextWeekTaskRequest request,
            Authentication authentication
    ) {
        Long userId = extractUserId(authentication);
        NextWeekTaskResponse response = taskService.addTask(reportId, userId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<NextWeekTaskResponse>> getTasks(
            @PathVariable Long reportId,
            Authentication authentication
    ) {
        Long userId = extractUserId(authentication);
        return ResponseEntity.ok(taskService.getTasks(reportId, userId));
    }

    @PutMapping("/{taskId}")
    public ResponseEntity<NextWeekTaskResponse> updateTask(
            @PathVariable Long reportId,
            @PathVariable Long taskId,
            @Valid @RequestBody NextWeekTaskRequest request,
            Authentication authentication
    ) {
        Long userId = extractUserId(authentication);
        return ResponseEntity.ok(taskService.updateTask(reportId, taskId, userId, request));
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<Void> deleteTask(
            @PathVariable Long reportId,
            @PathVariable Long taskId,
            Authentication authentication
    ) {
        Long userId = extractUserId(authentication);
        taskService.deleteTask(reportId, taskId, userId);
        return ResponseEntity.noContent().build();
    }

    /**
     * Safely extracts the user ID from Spring Security's Authentication object.
     */
    private Long extractUserId(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new IllegalArgumentException("User is not authenticated");
        }

        Object principal = authentication.getPrincipal();

        if (principal instanceof Long id) {
            return id;
        } else if (principal instanceof String principalString) {
            return Long.parseLong(principalString);
        } else if (principal instanceof org.springframework.security.core.userdetails.UserDetails userDetails) {
            // If your UserDetails implementation stores username as ID string
            return Long.parseLong(userDetails.getUsername());
        }

        throw new IllegalStateException("Unexpected principal type: " + principal.getClass().getName());
    }
}