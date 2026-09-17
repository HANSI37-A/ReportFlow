CREATE TABLE weekly_reports (
    id BIGSERIAL PRIMARY KEY,

    user_id BIGINT NOT NULL,
    project_id BIGINT,

    week_start DATE NOT NULL,
    week_end DATE NOT NULL,

    status VARCHAR(30) NOT NULL,

    blockers TEXT,
    key_achievement TEXT,

    hours_worked DECIMAL(8,2),

    notes TEXT,

    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_reports_user
        FOREIGN KEY (user_id)
        REFERENCES users(id),

    CONSTRAINT fk_reports_project
        FOREIGN KEY (project_id)
        REFERENCES projects(id),

    CONSTRAINT chk_report_status
        CHECK (
            status IN (
                'DRAFT',
                'SUBMITTED',
                'NEEDS_CORRECTION',
                'APPROVED'
            )
        )
);