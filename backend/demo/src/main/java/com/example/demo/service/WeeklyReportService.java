package com.example.demo.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.example.demo.dto.WeeklyReportRequest;
import com.example.demo.dto.WeeklyReportResponse;
import com.example.demo.entity.Project;
import com.example.demo.entity.ReportStatus;
import com.example.demo.entity.User;
import com.example.demo.entity.WeeklyReport;
import com.example.demo.repository.ProjectRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.WeeklyReportRepository;


@Service
public class WeeklyReportService {

    private final WeeklyReportRepository reportRepository;
    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;

    public WeeklyReportService(
            WeeklyReportRepository reportRepository,
            UserRepository userRepository,
            ProjectRepository projectRepository
    ) {
        this.reportRepository = reportRepository;
        this.userRepository = userRepository;
        this.projectRepository = projectRepository;
    }

    @Transactional
    public WeeklyReportResponse createReport(
            Long userId,
            WeeklyReportRequest request
    ) {

        validateDates(request);

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with ID: " + userId));

        Project project = projectRepository.findById(request.getProjectId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, 
                        "Project not found with ID: " + request.getProjectId()
                ));

        WeeklyReport report = new WeeklyReport();

        report.setUser(user);
        report.setProject(project);
        report.setWeekStart(request.getWeekStart());
        report.setWeekEnd(request.getWeekEnd());

        // Every newly created report starts as DRAFT
        report.setStatus(ReportStatus.DRAFT);

        report.setBlockers(request.getBlockers());
        report.setKeyAchievement(request.getKeyAchievement());
        report.setHoursWorked(request.getHoursWorked());
        report.setNotes(request.getNotes());

        WeeklyReport savedReport =
                reportRepository.save(report);

        return mapToResponse(savedReport);
    }

    @Transactional(readOnly = true)
    public List<WeeklyReportResponse> getMyReports(Long userId) {

        return reportRepository.findByUserId(userId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public WeeklyReportResponse getMyReport(
            Long reportId,
            Long userId
    ) {

        WeeklyReport report =
                reportRepository.findByIdAndUserId(reportId, userId)
                        .orElseThrow(() ->
                                new ResponseStatusException(HttpStatus.NOT_FOUND, "Report not found with ID: " + reportId));

        return mapToResponse(report);
    }

    @Transactional
    public WeeklyReportResponse updateMyReport(
            Long reportId,
            Long userId,
            WeeklyReportRequest request
    ) {

        validateDates(request);

        WeeklyReport report =
                reportRepository.findByIdAndUserId(reportId, userId)
                        .orElseThrow(() ->
                                new ResponseStatusException(HttpStatus.NOT_FOUND, "Report not found with ID: " + reportId));

        // Members can only edit Draft or Needs Correction reports
        if (report.getStatus() != ReportStatus.DRAFT &&
                report.getStatus() != ReportStatus.NEEDS_CORRECTION) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Only draft or correction-required reports can be edited"
            );
        }

        Project project = projectRepository.findById(request.getProjectId())
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Project not found with ID: " + request.getProjectId()));

        report.setProject(project);
        report.setWeekStart(request.getWeekStart());
        report.setWeekEnd(request.getWeekEnd());
        report.setBlockers(request.getBlockers());
        report.setKeyAchievement(request.getKeyAchievement());
        report.setHoursWorked(request.getHoursWorked());
        report.setNotes(request.getNotes());

        WeeklyReport updatedReport =
                reportRepository.save(report);

        return mapToResponse(updatedReport);
    }

    @Transactional
    public void submitMyReport(
            Long reportId,
            Long userId
    ) {

        WeeklyReport report =
                reportRepository.findByIdAndUserId(reportId, userId)
                        .orElseThrow(() ->
                                new ResponseStatusException(HttpStatus.NOT_FOUND, "Report not found with ID: " + reportId));

        if (report.getStatus() != ReportStatus.DRAFT &&
                report.getStatus() != ReportStatus.NEEDS_CORRECTION) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Only draft or correction-required reports can be submitted"
            );
        }

        report.setStatus(ReportStatus.SUBMITTED);

        reportRepository.save(report);
    }

    private void validateDates(WeeklyReportRequest request) {

        if (request.getWeekEnd()
                .isBefore(request.getWeekStart())) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Week end cannot be before week start"
            );
        }
    }

    private WeeklyReportResponse mapToResponse(
            WeeklyReport report
    ) {
        Long userId = report.getUser() != null ? report.getUser().getId() : null;
        String userName = report.getUser() != null ? report.getUser().getName() : null;
        Long projectId = report.getProject() != null ? report.getProject().getId() : null;
        String projectName = report.getProject() != null ? report.getProject().getName() : null;

        return new WeeklyReportResponse(
                report.getId(),
                userId,
                userName,
                projectId,
                projectName,
                report.getWeekStart(),
                report.getWeekEnd(),
                report.getStatus(),
                report.getBlockers(),
                report.getKeyAchievement(),
                report.getHoursWorked(),
                report.getNotes(),
                report.getCreatedAt(),
                report.getUpdatedAt()
        );
    }
}