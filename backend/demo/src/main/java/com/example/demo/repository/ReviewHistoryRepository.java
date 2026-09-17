package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.ReviewHistory;

public interface ReviewHistoryRepository
        extends JpaRepository<ReviewHistory, Long> {

    List<ReviewHistory> findByReportIdOrderByCreatedAtDesc(
            Long reportId
    );
}