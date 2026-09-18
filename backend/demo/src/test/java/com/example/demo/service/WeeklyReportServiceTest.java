package com.example.demo.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.mockito.ArgumentMatchers.any;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import com.example.demo.dto.WeeklyReportRequest;
import com.example.demo.dto.WeeklyReportResponse;
import com.example.demo.entity.Project;
import com.example.demo.entity.ReportStatus;
import com.example.demo.entity.User;
import com.example.demo.entity.WeeklyReport;
import com.example.demo.repository.NextWeekTaskRepository;
import com.example.demo.repository.ProjectRepository;
import com.example.demo.repository.ReportTaskRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.WeeklyReportRepository;

@ExtendWith(MockitoExtension.class)
class WeeklyReportServiceTest {

    @Mock
    private WeeklyReportRepository weeklyReportRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private ReportTaskRepository reportTaskRepository;

    @Mock
    private NextWeekTaskRepository nextWeekTaskRepository;

    private WeeklyReportService weeklyReportService;

    @BeforeEach
    void setUp() {
        weeklyReportService = new WeeklyReportService(
                weeklyReportRepository,
                userRepository,
                projectRepository,
                reportTaskRepository,
                nextWeekTaskRepository
        );
    }

    @Test
    void testCreateReportSuccess() {
        WeeklyReportRequest request = new WeeklyReportRequest();
        request.setWeekStart(LocalDate.of(2026, 9, 1));
        request.setWeekEnd(LocalDate.of(2026, 9, 7));
        request.setProjectId(10L);
        request.setKeyAchievement("Implemented features");
        request.setHoursWorked(BigDecimal.valueOf(40.0));

        User user = new User();
        user.setName("Test User");
        Project project = new Project();
        project.setName("Main Project");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(projectRepository.findById(10L)).thenReturn(Optional.of(project));
        when(weeklyReportRepository.save(any(WeeklyReport.class))).thenAnswer(invocation -> {
            WeeklyReport r = invocation.getArgument(0);
            return r;
        });

        WeeklyReportResponse response = weeklyReportService.createReport(1L, request);

        assertNotNull(response);
        assertEquals(ReportStatus.DRAFT, response.getStatus());
        assertEquals("Implemented features", response.getKeyAchievement());
        assertEquals("Main Project", response.getProjectName());
    }

    @Test
    void testCreateReportEndBeforeStartThrowsBadRequest() {
        WeeklyReportRequest request = new WeeklyReportRequest();
        request.setWeekStart(LocalDate.of(2026, 9, 10));
        request.setWeekEnd(LocalDate.of(2026, 9, 5)); // End before start
        request.setProjectId(10L);

        ResponseStatusException ex = assertThrows(ResponseStatusException.class, () ->
                weeklyReportService.createReport(1L, request)
        );

        assertEquals(HttpStatus.BAD_REQUEST, ex.getStatusCode());
    }

    @Test
    void testGetMyReportNotFoundThrowsNotFound() {
        when(weeklyReportRepository.findByIdAndUserId(99L, 1L)).thenReturn(Optional.empty());

        ResponseStatusException ex = assertThrows(ResponseStatusException.class, () ->
                weeklyReportService.getMyReport(99L, 1L)
        );

        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
    }

    @Test
    void testUpdateSubmittedReportThrowsBadRequest() {
        WeeklyReport report = new WeeklyReport();
        report.setStatus(ReportStatus.SUBMITTED); // Already submitted

        when(weeklyReportRepository.findByIdAndUserId(5L, 1L)).thenReturn(Optional.of(report));

        WeeklyReportRequest request = new WeeklyReportRequest();
        request.setWeekStart(LocalDate.of(2026, 9, 1));
        request.setWeekEnd(LocalDate.of(2026, 9, 7));

        ResponseStatusException ex = assertThrows(ResponseStatusException.class, () ->
                weeklyReportService.updateMyReport(5L, 1L, request)
        );

        assertEquals(HttpStatus.BAD_REQUEST, ex.getStatusCode());
    }

    @Test
    void testSubmitReportSuccess() {
        WeeklyReport report = new WeeklyReport();
        report.setStatus(ReportStatus.DRAFT);

        when(weeklyReportRepository.findByIdAndUserId(5L, 1L)).thenReturn(Optional.of(report));

        weeklyReportService.submitMyReport(5L, 1L);

        assertEquals(ReportStatus.SUBMITTED, report.getStatus());
        verify(weeklyReportRepository).save(report);
    }

    @Test
    void testMapToResponseWithNullProjectDoesNotThrowNpe() {
        WeeklyReport report = new WeeklyReport();
        report.setUser(new User());
        report.setProject(null); 
        report.setWeekStart(LocalDate.of(2026, 9, 1));
        report.setWeekEnd(LocalDate.of(2026, 9, 7));
        report.setStatus(ReportStatus.DRAFT);

        when(weeklyReportRepository.findByIdAndUserId(1L, 1L)).thenReturn(Optional.of(report));

        WeeklyReportResponse response = weeklyReportService.getMyReport(1L, 1L);

        assertNotNull(response);
        assertNull(response.getProjectId());
        assertNull(response.getProjectName());
    }
}