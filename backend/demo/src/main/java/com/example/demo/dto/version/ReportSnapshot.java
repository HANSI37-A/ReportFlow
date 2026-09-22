package com.example.demo.dto.version;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class ReportSnapshot {

    private Long reportId;

    private Long userId;
    private String userName;

    private Long projectId;
    private String projectName;

    private LocalDate weekStart;
    private LocalDate weekEnd;

    private String blockers;
    private String keyAchievement;

    private BigDecimal hoursWorked;

    private String notes;

    private List<ReportTaskSnapshot> tasks;

    private List<NextWeekTaskSnapshot> nextWeekTasks;

    public ReportSnapshot() {
    }

    public ReportSnapshot(
            Long reportId,
            Long userId,
            String userName,
            Long projectId,
            String projectName,
            LocalDate weekStart,
            LocalDate weekEnd,
            String blockers,
            String keyAchievement,
            BigDecimal hoursWorked,
            String notes,
            List<ReportTaskSnapshot> tasks,
            List<NextWeekTaskSnapshot> nextWeekTasks
    ) {
        this.reportId = reportId;
        this.userId = userId;
        this.userName = userName;
        this.projectId = projectId;
        this.projectName = projectName;
        this.weekStart = weekStart;
        this.weekEnd = weekEnd;
        this.blockers = blockers;
        this.keyAchievement = keyAchievement;
        this.hoursWorked = hoursWorked;
        this.notes = notes;
        this.tasks = tasks;
        this.nextWeekTasks = nextWeekTasks;
    }

    public Long getReportId() {
        return reportId;
    }

    public void setReportId(Long reportId) {
        this.reportId = reportId;
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

    public List<ReportTaskSnapshot> getTasks() {
        return tasks;
    }

    public void setTasks(List<ReportTaskSnapshot> tasks) {
        this.tasks = tasks;
    }

    public List<NextWeekTaskSnapshot> getNextWeekTasks() {
        return nextWeekTasks;
    }

    public void setNextWeekTasks(List<NextWeekTaskSnapshot> nextWeekTasks) {
        this.nextWeekTasks = nextWeekTasks;
    }

}