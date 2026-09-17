package com.example.demo.dto;

import java.time.LocalDateTime;

public class NextWeekTaskResponse {

    private Long id;
    private Long reportId;
    private String taskName;
    private String priority;
    private LocalDateTime createdAt;

    public NextWeekTaskResponse(
            Long id,
            Long reportId,
            String taskName,
            String priority,
            LocalDateTime createdAt
    ) {
        this.id = id;
        this.reportId = reportId;
        this.taskName = taskName;
        this.priority = priority;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public Long getReportId() {
        return reportId;
    }

    public String getTaskName() {
        return taskName;
    }

    public String getPriority() {
        return priority;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}