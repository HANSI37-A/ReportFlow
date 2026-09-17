package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dto.ReportTaskRequest;
import com.example.demo.dto.ReportTaskResponse;
import com.example.demo.entity.ReportStatus;
import com.example.demo.entity.ReportTask;
import com.example.demo.entity.WeeklyReport;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.ReportTaskRepository;
import com.example.demo.repository.WeeklyReportRepository;

@Service
public class ReportTaskService {

    private final ReportTaskRepository taskRepository;
    private final WeeklyReportRepository reportRepository;

    public ReportTaskService(
            ReportTaskRepository taskRepository,
            WeeklyReportRepository reportRepository
    ) {
        this.taskRepository = taskRepository;
        this.reportRepository = reportRepository;
    }

    @Transactional
    public ReportTaskResponse addTask(
            Long reportId,
            Long userId,
            ReportTaskRequest request
    ) {
        WeeklyReport report = getEditableReport(reportId, userId);

        ReportTask task = new ReportTask();
        task.setReport(report);
        task.setTaskName(request.getTaskName());
        task.setPriority(request.getPriority());
        task.setPlannedPercentage(request.getPlannedPercentage());
        task.setActualPercentage(request.getActualPercentage());
        task.setStatus(request.getStatus());
        task.setPlannedHours(request.getPlannedHours());
        task.setSpentHours(request.getSpentHours());
        task.setDeliverable(request.getDeliverable());

        ReportTask savedTask = taskRepository.save(task);

        return mapToResponse(savedTask);
    }

    @Transactional(readOnly = true)
    public List<ReportTaskResponse> getTasks(
            Long reportId,
            Long userId
    ) {
        // Check report existence and ownership
        reportRepository.findByIdAndUserId(reportId, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Report not found with ID: " + reportId));

        return taskRepository.findByReportId(reportId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Transactional
    public ReportTaskResponse updateTask(
            Long reportId,
            Long taskId,
            Long userId,
            ReportTaskRequest request
    ) {
        WeeklyReport report = getEditableReport(reportId, userId);

        ReportTask task = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with ID: " + taskId));

        if (!task.getReport().getId().equals(report.getId())) {
            throw new BadRequestException("Task does not belong to report ID: " + reportId);
        }

        task.setTaskName(request.getTaskName());
        task.setPriority(request.getPriority());
        task.setPlannedPercentage(request.getPlannedPercentage());
        task.setActualPercentage(request.getActualPercentage());
        task.setStatus(request.getStatus());
        task.setPlannedHours(request.getPlannedHours());
        task.setSpentHours(request.getSpentHours());
        task.setDeliverable(request.getDeliverable());

        ReportTask updatedTask = taskRepository.save(task);

        return mapToResponse(updatedTask);
    }

    @Transactional
    public void deleteTask(
            Long reportId,
            Long taskId,
            Long userId
    ) {
        WeeklyReport report = getEditableReport(reportId, userId);

        ReportTask task = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with ID: " + taskId));

        if (!task.getReport().getId().equals(report.getId())) {
            throw new BadRequestException("Task does not belong to report ID: " + reportId);
        }

        taskRepository.delete(task);
    }

    private WeeklyReport getEditableReport(
            Long reportId,
            Long userId
    ) {
        WeeklyReport report = reportRepository.findByIdAndUserId(reportId, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Report not found with ID: " + reportId + " for user ID: " + userId));

        if (report.getStatus() != ReportStatus.DRAFT &&
                report.getStatus() != ReportStatus.NEEDS_CORRECTION) {

            throw new BadRequestException("Tasks can only be changed while report is in DRAFT or NEEDS_CORRECTION state");
        }

        return report;
    }

    private ReportTaskResponse mapToResponse(
            ReportTask task
    ) {
        return new ReportTaskResponse(
                task.getId(),
                task.getReport() != null ? task.getReport().getId() : null,
                task.getTaskName(),
                task.getPriority(),
                task.getPlannedPercentage(),
                task.getActualPercentage(),
                task.getStatus(),
                task.getPlannedHours(),
                task.getSpentHours(),
                task.getDeliverable(),
                task.getCreatedAt()
        );
    }
}