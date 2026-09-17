package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.NextWeekTask;

public interface NextWeekTaskRepository
        extends JpaRepository<NextWeekTask, Long> {

    List<NextWeekTask> findByReportId(Long reportId);
}