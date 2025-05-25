CREATE TABLE secrets (
    id BIGINT auto_increment PRIMARY KEY,
    name VARCHAR(200) NOT NULL,
    secret_value VARCHAR NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL
);
