CREATE TABLE report_tasks (
    id BIGSERIAL PRIMARY KEY,

    report_id BIGINT NOT NULL,

    task_name VARCHAR(255) NOT NULL,
    priority VARCHAR(30),
    planned_percentage DECIMAL(5,2),
    actual_percentage DECIMAL(5,2),
    status VARCHAR(30),

    planned_hours DECIMAL(8,2),
    spent_hours DECIMAL(8,2),

    deliverable TEXT,

    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_tasks_report
        FOREIGN KEY (report_id)
        REFERENCES weekly_reports(id)
        ON DELETE CASCADE
);