package com.example.demo.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.example.demo.entity.ReportStatus;

public class WeeklyReportDetailResponse {

    private Long id;
    private Long userId;
    private String userName;

    private Long projectId;
    private String projectName;

    private LocalDate weekStart;
    private LocalDate weekEnd;

    private ReportStatus status;

    private String blockers;
    private String keyAchievement;
    private BigDecimal hoursWorked;
    private String notes;

    private List<ReportTaskResponse> tasks;
    private List<NextWeekTaskResponse> nextWeekTasks;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public WeeklyReportDetailResponse() {
    }

    public WeeklyReportDetailResponse(
            Long id,
            Long userId,
            String userName,
            Long projectId,
            String projectName,
            LocalDate weekStart,
            LocalDate weekEnd,
            ReportStatus status,
            String blockers,
            String keyAchievement,
            BigDecimal hoursWorked,
            String notes,
            List<ReportTaskResponse> tasks,
            List<NextWeekTaskResponse> nextWeekTasks,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {
        this.id = id;
        this.userId = userId;
        this.userName = userName;
        this.projectId = projectId;
        this.projectName = projectName;
        this.weekStart = weekStart;
        this.weekEnd = weekEnd;
        this.status = status;
        this.blockers = blockers;
        this.keyAchievement = keyAchievement;
        this.hoursWorked = hoursWorked;
        this.notes = notes;
        this.tasks = tasks;
        this.nextWeekTasks = nextWeekTasks;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
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

    public ReportStatus getStatus() {
        return status;
    }

    public void setStatus(ReportStatus status) {
        this.status = status;
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

    public List<ReportTaskResponse> getTasks() {
        return tasks;
    }

    public void setTasks(List<ReportTaskResponse> tasks) {
        this.tasks = tasks;
    }

    public List<NextWeekTaskResponse> getNextWeekTasks() {
        return nextWeekTasks;
    }

    public void setNextWeekTasks(List<NextWeekTaskResponse> nextWeekTasks) {
        this.nextWeekTasks = nextWeekTasks;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}