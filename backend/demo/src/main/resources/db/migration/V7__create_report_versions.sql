CREATE TABLE report_versions (
    id BIGSERIAL PRIMARY KEY,

    report_id BIGINT NOT NULL,

    version_number INTEGER NOT NULL,

    snapshot_data JSONB NOT NULL,

    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_versions_report
        FOREIGN KEY (report_id)
        REFERENCES weekly_reports(id)
        ON DELETE CASCADE,

    CONSTRAINT uq_report_version
        UNIQUE (report_id, version_number)
);