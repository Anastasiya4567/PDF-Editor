CREATE TABLE IF NOT EXISTS users (
    id BIGINT NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
    name VARCHAR(50) NOT NULL,
    email VARCHAR(120) UNIQUE NOT NULL,
    image_url VARCHAR(255),
    email_verified BOOLEAN DEFAULT FALSE,
    password VARCHAR(60),
    provider VARCHAR(20) NOT NULL,
    provider_id VARCHAR(50),

    PRIMARY KEY (id)
);