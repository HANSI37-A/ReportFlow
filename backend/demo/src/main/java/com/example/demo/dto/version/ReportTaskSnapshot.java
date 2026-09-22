package com.example.demo.dto.version;

import java.math.BigDecimal;

public class ReportTaskSnapshot {

    private Long id;

    private String taskName;
    private String priority;

    private BigDecimal plannedPercentage;
    private BigDecimal actualPercentage;

    private String status;

    private BigDecimal plannedHours;
    private BigDecimal spentHours;

    private String deliverable;

    public ReportTaskSnapshot() {
    }

    public ReportTaskSnapshot(
            Long id,
            String taskName,
            String priority,
            BigDecimal plannedPercentage,
            BigDecimal actualPercentage,
            String status,
            BigDecimal plannedHours,
            BigDecimal spentHours,
            String deliverable
    ) {
        this.id = id;
        this.taskName = taskName;
        this.priority = priority;
        this.plannedPercentage = plannedPercentage;
        this.actualPercentage = actualPercentage;
        this.status = status;
        this.plannedHours = plannedHours;
        this.spentHours = spentHours;
        this.deliverable = deliverable;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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