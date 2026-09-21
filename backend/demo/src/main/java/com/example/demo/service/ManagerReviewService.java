package com.example.demo.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dto.ReviewHistoryResponse;
import com.example.demo.dto.ReviewReportRequest;
import com.example.demo.entity.ReportStatus;
import com.example.demo.entity.ReviewHistory;
import com.example.demo.entity.User;
import com.example.demo.entity.WeeklyReport;
import com.example.demo.repository.ReportVersionRepository;
import com.example.demo.repository.ReviewHistoryRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.WeeklyReportRepository;

@Service
public class ManagerReviewService {

    private final WeeklyReportRepository reportRepository;
    private final UserRepository userRepository;
    private final ReportVersionRepository versionRepository;
    private final ReviewHistoryRepository reviewHistoryRepository;

    public ManagerReviewService(
            WeeklyReportRepository reportRepository,
            UserRepository userRepository,
            ReportVersionRepository versionRepository,
            ReviewHistoryRepository reviewHistoryRepository
    ) {
        this.reportRepository = reportRepository;
        this.userRepository = userRepository;
        this.versionRepository = versionRepository;
        this.reviewHistoryRepository = reviewHistoryRepository;
    }

    @Transactional
    public ReviewHistoryResponse approveReport(
            Long reportId,
            Long managerId,
            ReviewReportRequest request
    ) {
        WeeklyReport report = reportRepository.findById(reportId)
                .orElseThrow(() -> new RuntimeException("Report not found with id: " + reportId));

        if (report.getStatus() != ReportStatus.SUBMITTED) {
            throw new RuntimeException("Only submitted reports can be approved");
        }

        User manager = userRepository.findById(managerId)
                .orElseThrow(() -> new RuntimeException("Manager not found with id: " + managerId));

        ReportStatus previousStatus = report.getStatus();

        report.setStatus(ReportStatus.APPROVED);
        reportRepository.save(report);

        ReviewHistory history = new ReviewHistory();
        history.setReport(report);
        history.setReviewer(manager);
        history.setPreviousStatus(previousStatus);
        history.setNewStatus(ReportStatus.APPROVED);
        history.setComment(request != null ? request.getComment() : null);

        if (report.getCurrentVersion() != null) {
            history.setReportVersion(report.getCurrentVersion());
        }

        ReviewHistory savedHistory = reviewHistoryRepository.save(history);

        return mapToResponse(savedHistory);
    }

    @Transactional
    public ReviewHistoryResponse requestChanges(
            Long reportId,
            Long managerId,
            ReviewReportRequest request
    ) {
        WeeklyReport report = reportRepository.findById(reportId)
                .orElseThrow(() -> new RuntimeException("Report not found with id: " + reportId));

        if (report.getStatus() != ReportStatus.SUBMITTED) {
            throw new RuntimeException("Only submitted reports can be sent for revision");
        }

        User manager = userRepository.findById(managerId)
                .orElseThrow(() -> new RuntimeException("Manager not found with id: " + managerId));

        ReportStatus previousStatus = report.getStatus();

        report.setStatus(ReportStatus.DRAFT); // Change status to DRAFT to indicate that changes are requested
        reportRepository.save(report);

        ReviewHistory history = new ReviewHistory();
        history.setReport(report);
        history.setReviewer(manager);
        history.setPreviousStatus(previousStatus);
        history.setNewStatus(ReportStatus.DRAFT);
        history.setComment(request != null ? request.getComment() : null);

        if (report.getCurrentVersion() != null) {
            history.setReportVersion(report.getCurrentVersion());
        }

        ReviewHistory savedHistory = reviewHistoryRepository.save(history);

        return mapToResponse(savedHistory);
    }

    private ReviewHistoryResponse mapToResponse(ReviewHistory history) {
        Long reportId = history.getReport() != null ? history.getReport().getId() : null;
        Long reviewerId = history.getReviewer() != null ? history.getReviewer().getId() : null;
        String reviewerName = history.getReviewer() != null ? history.getReviewer().getName() : null;

        Long reportVersionId = null;
        Integer versionNumber = null;

        if (history.getReportVersion() != null) {
            reportVersionId = history.getReportVersion().getId();
            versionNumber = history.getReportVersion().getVersionNumber();
        }

        return new ReviewHistoryResponse(
                history.getId(),
                reportId,
                reviewerId,
                reviewerName,
                reportVersionId,
                versionNumber,
                history.getPreviousStatus(),
                history.getNewStatus(),
                history.getComment(),
                history.getCreatedAt()
        );
    }
}