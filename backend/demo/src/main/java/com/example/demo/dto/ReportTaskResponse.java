package com.example.demo.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ReportTaskResponse {

    private Long id;
    private Long reportId;

    private String taskName;
    private String priority;

    private BigDecimal plannedPercentage;
    private BigDecimal actualPercentage;

    private String status;

    private BigDecimal plannedHours;
    private BigDecimal spentHours;

    private String deliverable;

    private LocalDateTime createdAt;

    public ReportTaskResponse() {
    }

    public ReportTaskResponse(
            Long id,
            Long reportId,
            String taskName,
            String priority,
            BigDecimal plannedPercentage,
            BigDecimal actualPercentage,
            String status,
            BigDecimal plannedHours,
            BigDecimal spentHours,
            String deliverable,
            LocalDateTime createdAt
    ) {
        this.id = id;
        this.reportId = reportId;
        this.taskName = taskName;
        this.priority = priority;
        this.plannedPercentage = plannedPercentage;
        this.actualPercentage = actualPercentage;
        this.status = status;
        this.plannedHours = plannedHours;
        this.spentHours = spentHours;
        this.deliverable = deliverable;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getReportId() {
        return reportId;
    }

    public void setReportId(Long reportId) {
        this.reportId = reportId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public BigDecimal getPlannedPercentage() {
        return plannedPercentage;
    }

    public void setPlannedPercentage(BigDecimal plannedPercentage) {
        this.plannedPercentage = plannedPercentage;
    }

    public BigDecimal getActualPercentage() {
        return actualPercentage;
    }

    public void setActualPercentage(BigDecimal actualPercentage) {
        this.actualPercentage = actualPercentage;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public BigDecimal getPlannedHours() {
        return plannedHours;
    }

    public void setPlannedHours(BigDecimal plannedHours) {
        this.plannedHours = plannedHours;
    }

    public BigDecimal getSpentHours() {
        return spentHours;
    }

    public void setSpentHours(BigDecimal spentHours) {
        this.spentHours = spentHours;
    }

    public String getDeliverable() {
        return deliverable;
    }

    public void setDeliverable(String deliverable) {
        this.deliverable = deliverable;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}