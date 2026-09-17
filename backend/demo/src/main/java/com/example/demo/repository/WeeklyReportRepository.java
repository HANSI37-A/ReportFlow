package com.example.demo.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.ReportStatus;
import com.example.demo.entity.WeeklyReport;

public interface WeeklyReportRepository
        extends JpaRepository<WeeklyReport, Long> {

    List<WeeklyReport> findByUserId(Long userId);

    Optional<WeeklyReport> findByIdAndUserId(Long id, Long userId);

    List<WeeklyReport> findByStatus(ReportStatus status);

    List<WeeklyReport> findByWeekStartBetween(
            LocalDate start,
            LocalDate end
    );
}