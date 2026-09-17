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

import com.example.demo.dto.ReportTaskRequest;
import com.example.demo.dto.ReportTaskResponse;
import com.example.demo.service.ReportTaskService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/member/reports/{reportId}/tasks")
public class ReportTaskController {

    private final ReportTaskService taskService;

    public ReportTaskController(
            ReportTaskService taskService
    ) {
        this.taskService = taskService;
    }

    @PostMapping
    public ResponseEntity<ReportTaskResponse> addTask(
            @PathVariable Long reportId,
            @Valid @RequestBody ReportTaskRequest request,
            Authentication authentication
    ) {

        Long userId = (Long) authentication.getPrincipal();

        ReportTaskResponse response =
                taskService.addTask(
                        reportId,
                        userId,
                        request
                );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping
    public ResponseEntity<List<ReportTaskResponse>> getTasks(
            @PathVariable Long reportId,
            Authentication authentication
    ) {

        Long userId = (Long) authentication.getPrincipal();

        return ResponseEntity.ok(
                taskService.getTasks(
                        reportId,
                        userId
                )
        );
    }

    @PutMapping("/{taskId}")
    public ResponseEntity<ReportTaskResponse> updateTask(
            @PathVariable Long reportId,
            @PathVariable Long taskId,
            @Valid @RequestBody ReportTaskRequest request,
            Authentication authentication
    ) {

        Long userId = (Long) authentication.getPrincipal();

        return ResponseEntity.ok(
                taskService.updateTask(
                        reportId,
                        taskId,
                        userId,
                        request
                )
        );
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<Void> deleteTask(
            @PathVariable Long reportId,
            @PathVariable Long taskId,
            Authentication authentication
    ) {

        Long userId = (Long) authentication.getPrincipal();

        taskService.deleteTask(
                reportId,
                taskId,
                userId
        );

        return ResponseEntity.noContent().build();
    }
}