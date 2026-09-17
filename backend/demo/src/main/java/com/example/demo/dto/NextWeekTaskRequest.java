package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class NextWeekTaskRequest {

    @NotBlank(message = "Task name is required")
    @Size(max = 255, message = "Task name must not exceed 255 characters")
    private String taskName;

    @NotBlank(message = "Priority is required")
    private String priority;

    public NextWeekTaskRequest() {
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }
}