package com.example.demo.dto;

import jakarta.validation.constraints.Size;

public class ReviewReportRequest {

    @Size(
        max = 2000,
        message = "Comment must not exceed 2000 characters"
    )
    private String comment;

    public ReviewReportRequest() {
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}