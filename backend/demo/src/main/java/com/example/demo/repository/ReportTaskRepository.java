package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.ReportTask;

public interface ReportTaskRepository
        extends JpaRepository<ReportTask, Long> {

    List<ReportTask> findByReportId(Long reportId);
}