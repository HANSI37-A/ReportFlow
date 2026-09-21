package com.example.demo.dto;

import java.time.LocalDateTime;

import com.example.demo.entity.ReportStatus;

public class ReviewHistoryResponse {

    private Long id;
    private Long reportId;
    private Long reviewerId;
    private String reviewerName;
    private Long reportVersionId;
    private Integer versionNumber;
    private ReportStatus previousStatus;
    private ReportStatus newStatus;
    private String comment;
    private LocalDateTime createdAt;

    public ReviewHistoryResponse() {
    }

    public ReviewHistoryResponse(
            Long id,
            Long reportId,
            Long reviewerId,
            String reviewerName,
            Long reportVersionId,
            Integer versionNumber,
            ReportStatus previousStatus,
            ReportStatus newStatus,
            String comment,
            LocalDateTime createdAt
    ) {
        this.id = id;
        this.reportId = reportId;
        this.reviewerId = reviewerId;
        this.reviewerName = reviewerName;
        this.reportVersionId = reportVersionId;
        this.versionNumber = versionNumber;
        this.previousStatus = previousStatus;
        this.newStatus = newStatus;
        this.comment = comment;
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

    public Long getReviewerId() {
        return reviewerId;
    }

    public void setReviewerId(Long reviewerId) {
        this.reviewerId = reviewerId;
    }

    public String getReviewerName() {
        return reviewerName;
    }

    public void setReviewerName(String reviewerName) {
        this.reviewerName = reviewerName;
    }

    public Long getReportVersionId() {
        return reportVersionId;
    }

    public void setReportVersionId(Long reportVersionId) {
        this.reportVersionId = reportVersionId;
    }

    public Integer getVersionNumber() {
        return versionNumber;
    }

    public void setVersionNumber(Integer versionNumber) {
        this.versionNumber = versionNumber;
    }

    public ReportStatus getPreviousStatus() {
        return previousStatus;
    }

    public void setPreviousStatus(ReportStatus previousStatus) {
        this.previousStatus = previousStatus;
    }

    public ReportStatus getNewStatus() {
        return newStatus;
    }

    public void setNewStatus(ReportStatus newStatus) {
        this.newStatus = newStatus;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}