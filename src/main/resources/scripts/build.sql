CREATE TABLE IF NOT EXISTS builds (
    id BIGINT auto_increment PRIMARY KEY,
    job_id BIGINT NOT NULL,
    status VARCHAR(20) NOT NULL,
    ref VARCHAR(200) NOT NULL,
    head_commit VARCHAR(200) NULL,
    repository_id INTEGER NULL,
    repository_name VARCHAR(200) NOT NULL,
    repository_full_name VARCHAR(200) NOT NULL,
    repository_url VARCHAR(200) NOT NULL,
    repository_clone_crl VARCHAR(200) NOT NULL,
    repository_owner VARCHAR(200) NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL
);
