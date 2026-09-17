package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dto.NextWeekTaskRequest;
import com.example.demo.dto.NextWeekTaskResponse;
import com.example.demo.entity.NextWeekTask;
import com.example.demo.entity.ReportStatus;
import com.example.demo.entity.WeeklyReport;
import com.example.demo.repository.NextWeekTaskRepository;
import com.example.demo.repository.WeeklyReportRepository;


@Service
public class NextWeekTaskService {

    private final NextWeekTaskRepository taskRepository;
    private final WeeklyReportRepository reportRepository;

    public NextWeekTaskService(
            NextWeekTaskRepository taskRepository,
            WeeklyReportRepository reportRepository
    ) {
        this.taskRepository = taskRepository;
        this.reportRepository = reportRepository;
    }

    @Transactional
    public NextWeekTaskResponse addTask(
            Long reportId,
            Long userId,
            NextWeekTaskRequest request
    ) {

        WeeklyReport report = getEditableReport(reportId, userId);

        NextWeekTask task = new NextWeekTask();

        task.setReport(report);
        task.setTaskName(request.getTaskName());
        task.setPriority(request.getPriority());

        NextWeekTask savedTask = taskRepository.save(task);

        return mapToResponse(savedTask);
    }


    @Transactional(readOnly = true)
    public List<NextWeekTaskResponse> getTasks(
            Long reportId,
            Long userId
    ) {

        // Make sure this report belongs to the logged-in user
        reportRepository.findByIdAndUserId(reportId, userId)
                .orElseThrow(() ->
                        new RuntimeException("Report not found")
                );

        return taskRepository.findByReportId(reportId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }


    @Transactional
    public NextWeekTaskResponse updateTask(
            Long reportId,
            Long taskId,
            Long userId,
            NextWeekTaskRequest request
    ) {

        WeeklyReport report = getEditableReport(reportId, userId);

        NextWeekTask task = taskRepository.findById(taskId)
                .orElseThrow(() ->
                        new RuntimeException("Task not found")
                );

        // Security check
        if (!task.getReport().getId().equals(report.getId())) {
            throw new RuntimeException(
                    "Task does not belong to this report"
            );
        }

        task.setTaskName(request.getTaskName());
        task.setPriority(request.getPriority());

        NextWeekTask updatedTask =
                taskRepository.save(task);

        return mapToResponse(updatedTask);
    }


    @Transactional
    public void deleteTask(
            Long reportId,
            Long taskId,
            Long userId
    ) {

        WeeklyReport report = getEditableReport(reportId, userId);

        NextWeekTask task = taskRepository.findById(taskId)
                .orElseThrow(() ->
                        new RuntimeException("Task not found")
                );

        // Security check
        if (!task.getReport().getId().equals(report.getId())) {
            throw new RuntimeException(
                    "Task does not belong to this report"
            );
        }

        taskRepository.delete(task);
    }


    private WeeklyReport getEditableReport(
            Long reportId,
            Long userId
    ) {

        WeeklyReport report =
                reportRepository.findByIdAndUserId(reportId, userId)
                        .orElseThrow(() ->
                                new RuntimeException("Report not found")
                        );

        if (report.getStatus() != ReportStatus.DRAFT &&
                report.getStatus() != ReportStatus.NEEDS_CORRECTION) {

            throw new RuntimeException(
                    "Next week tasks can only be changed while report is draft or needs correction"
            );
        }

        return report;
    }


    private NextWeekTaskResponse mapToResponse(
            NextWeekTask task
    ) {

        return new NextWeekTaskResponse(
                task.getId(),
                task.getReport().getId(),
                task.getTaskName(),
                task.getPriority(),
                task.getCreatedAt()
        );
    }
}