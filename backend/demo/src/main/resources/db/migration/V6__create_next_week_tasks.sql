CREATE TABLE next_week_tasks (
    id BIGSERIAL PRIMARY KEY,

    report_id BIGINT NOT NULL,

    task_name VARCHAR(255) NOT NULL,
    priority VARCHAR(30),

    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_next_week_tasks_report
        FOREIGN KEY (report_id)
        REFERENCES weekly_reports(id)
        ON DELETE CASCADE
);