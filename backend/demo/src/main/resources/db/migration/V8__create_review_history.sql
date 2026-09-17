CREATE TABLE review_history (
    id BIGSERIAL PRIMARY KEY,

    report_id BIGINT NOT NULL,
    reviewer_id BIGINT NOT NULL,

    report_version_id BIGINT,

    previous_status VARCHAR(30),
    new_status VARCHAR(30),

    comment TEXT,

    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_review_report
        FOREIGN KEY (report_id)
        REFERENCES weekly_reports(id)
        ON DELETE CASCADE,

    CONSTRAINT fk_review_reviewer
        FOREIGN KEY (reviewer_id)
        REFERENCES users(id),

    CONSTRAINT fk_review_version
        FOREIGN KEY (report_version_id)
        REFERENCES report_versions(id)
);