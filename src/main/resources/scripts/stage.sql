CREATE TABLE stages (
    id BIGINT auto_increment PRIMARY KEY,
    name VARCHAR(30) NOT NULL,
    build_id BIGINT NOT NULL,
    command VARCHAR(200) NOT NULL,
    status VARCHAR(100) NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL
);
