-- drop existing tables-----------------
DROP TABLE IF EXISTS to_do;
DROP TABLE IF EXISTS user_role;
DROP TABLE IF EXISTS "user";
DROP TABLE IF EXISTS role;
DROP TABLE IF EXISTS significance;


-- create tables -----------------------
CREATE TABLE "user"
(
    id         BIGSERIAL PRIMARY KEY NOT NULL,
    username   VARCHAR(255) UNIQUE   NOT NULL,
    password   VARCHAR(255)          NOT NULL,
    first_name VARCHAR(255)          NOT NULL,
    last_name  VARCHAR(255)          NOT NULL,
    age        INTEGER               NOT NULL,
    created    TIMESTAMP             NOT NULL DEFAULT NOW(),
    updated    TIMESTAMP             NOT NULL DEFAULT NOW()
);

CREATE TABLE role
(
    id BIGSERIAL PRIMARY KEY NOT NULL,
    name VARCHAR(255) UNIQUE NOT NULL
);

CREATE TABLE user_role
(
    user_id BIGINT REFERENCES "user"(id),
    role_id BIGINT REFERENCES role(id)
);

CREATE TABLE significance
(
    id BIGSERIAL PRIMARY KEY NOT NULL,
    name VARCHAR(255) UNIQUE NOT NULL
);

CREATE TABLE to_do
(
    id                  BIGSERIAL           NOT NULL PRIMARY KEY,
    text                VARCHAR(255)        NOT NULL,
    created             TIMESTAMP           NOT NULL DEFAULT NOW(),
    updated             TIMESTAMP           NOT NULL DEFAULT NOW(),
    deadline            TIMESTAMP           NOT NULL,
    user_id             BIGINT REFERENCES "user" (id),
    significance_id     BIGINT REFERENCES significance(id)
);

-- insert values ----------------
INSERT INTO role (name)
VALUES ('USER'),
       ('ADMIN');

INSERT INTO significance(name)
VALUES ('Ordinary'),
       ('Important'),
       ('Urgent');