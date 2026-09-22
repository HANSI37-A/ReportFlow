package com.example.demo.dto.version;

public class NextWeekTaskSnapshot {

    private Long id;

    private String taskName;

    private String priority;

    public NextWeekTaskSnapshot() {
    }

    public NextWeekTaskSnapshot(
            Long id,
            String taskName,
            String priority
    ) {
        this.id = id;
        this.taskName = taskName;
        this.priority = priority;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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