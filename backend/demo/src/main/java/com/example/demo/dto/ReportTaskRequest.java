package com.example.demo.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class ReportTaskRequest {

    @NotBlank(message = "Task name is required")
    @Size(max = 255, message = "Task name cannot exceed 255 characters")
    private String taskName;

    @NotBlank(message = "Priority is required")
    private String priority;

    @NotNull(message = "Planned percentage is required")
    @DecimalMin(value = "0.0")
    @DecimalMax(value = "100.0")
    private BigDecimal plannedPercentage;

    @NotNull(message = "Actual percentage is required")
    @DecimalMin(value = "0.0")
    @DecimalMax(value = "100.0")
    private BigDecimal actualPercentage;

    @NotBlank(message = "Task status is required")
    private String status;

    @DecimalMin(value = "0.0")
    private BigDecimal plannedHours;

    @DecimalMin(value = "0.0")
    private BigDecimal spentHours;

    @Size(max = 2000, message = "Deliverable cannot exceed 2000 characters")
    private String deliverable;

    public ReportTaskRequest() {
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
}