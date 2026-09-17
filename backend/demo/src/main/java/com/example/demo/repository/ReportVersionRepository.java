package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.ReportVersion;

public interface ReportVersionRepository
        extends JpaRepository<ReportVersion, Long> {

    List<ReportVersion> findByReportIdOrderByVersionNumberDesc(
            Long reportId
    );

    Optional<ReportVersion> findByReportIdAndVersionNumber(
            Long reportId,
            Integer versionNumber
    );
}