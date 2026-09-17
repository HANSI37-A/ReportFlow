package com.example.demo.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class WeeklyReportRequest {

    @NotNull(message = "Week start is required")
    private LocalDate weekStart;

    @NotNull(message = "Week end is required")
    private LocalDate weekEnd;

    @NotNull(message = "Project is required")
    private Long projectId;

    @Size(max = 2000, message = "Blockers cannot exceed 2000 characters")
    private String blockers;

    @Size(max = 2000, message = "Key achievement cannot exceed 2000 characters")
    private String keyAchievement;

    @DecimalMin(value = "0.0", message = "Hours worked cannot be negative")
    @Digits(integer = 6, fraction = 2, message = "Hours worked cannot exceed 6 integer digits and 2 decimal places")
    private BigDecimal hoursWorked;

    @Size(max = 5000, message = "Notes cannot exceed 5000 characters")
    private String notes;

    public WeeklyReportRequest() {
    }

    public LocalDate getWeekStart() {
        return weekStart;
    }

    public void setWeekStart(LocalDate weekStart) {
        this.weekStart = weekStart;
    }

    public LocalDate getWeekEnd() {
        return weekEnd;
    }

    public void setWeekEnd(LocalDate weekEnd) {
        this.weekEnd = weekEnd;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public String getBlockers() {
        return blockers;
    }

    public void setBlockers(String blockers) {
        this.blockers = blockers;
    }

    public String getKeyAchievement() {
        return keyAchievement;
    }

    public void setKeyAchievement(String keyAchievement) {
        this.keyAchievement = keyAchievement;
    }

    public BigDecimal getHoursWorked() {
        return hoursWorked;
    }

    public void setHoursWorked(BigDecimal hoursWorked) {
        this.hoursWorked = hoursWorked;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}