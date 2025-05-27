CREATE TABLE IF NOT EXISTS jobs (
    id BIGINT auto_increment PRIMARY KEY,
    name VARCHAR(30) NOT NULL,
    description VARCHAR(200) NULL,
    scm_url VARCHAR(200) NOT NULL,
    is_private_repo BOOLEAN NOT NULL,
    scm_secret_id INTEGER NULL,
    branch VARCHAR(200) NOT NULL,
    knull_file_location VARCHAR NOT NULL,
    is_active BOOLEAN NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    created_by BIGINT NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL,
    updated_by BIGINT NOT NULL
);
