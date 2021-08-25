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
    id                  BIGSERIAL           PRIMARY KEY NOT NULL,
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

INSERT INTO "user"(id, username, password, first_name, last_name, age, created, updated)
VALUES (14,'admin', '{bcrypt}$2a$10$qVtt52/CMIGDuVLMvPpwheEdgtZiq/qDvOLlXJu.TpBKNgUqaJ2FW', 'admin', 'admin', 25, now(), now()),
       (20,'deletableUser1', '{bcrypt}$2a$10$qVtt52/CMIGDuVLMvPpwheEdgtZiq/qDvOLlXJu.TpBKNgUqaJ2FW', 'User1', 'User1', 25, now(), now()),
       (21,'deletableUser2', '{bcrypt}$2a$10$qVtt52/CMIGDuVLMvPpwheEdgtZiq/qDvOLlXJu.TpBKNgUqaJ2FW', 'User2', 'User2', 25, now(), now()),
       (22,'deletableUser3', '{bcrypt}$2a$10$qVtt52/CMIGDuVLMvPpwheEdgtZiq/qDvOLlXJu.TpBKNgUqaJ2FW', 'User3', 'User3', 25, now(), now()),
       (23,'deletableUser4', '{bcrypt}$2a$10$qVtt52/CMIGDuVLMvPpwheEdgtZiq/qDvOLlXJu.TpBKNgUqaJ2FW', 'User4', 'User4', 25, now(), now());

INSERT INTO user_role(user_id, role_id)
VALUES (14,1),
       (14,2),
       (20,1),
       (21,1),
       (22,1),
       (23,1);

INSERT INTO to_do (id ,text, created, updated, deadline, user_id, significance_id)
VALUES (100, 'Test Text Test Text Test Text', now(), now(),'2021-08-24 16:51:00.542929', 14, 1),
       (101, 'Test Text Test Text Test Text', now(), now(),'2021-09-13 16:51:00.542929', 14, 2),
       (102, 'Test Text Test Text Test Text', now(), now(),'2021-08-24 16:51:00.542929', 14, 3),
       (103, 'Test Text Test Text Test Text', now(), now(),'2021-08-24 16:51:00.542929', 14, 1),
       (104, 'Test Text Test Text Test Text', now(), now(),'2022-08-24 16:51:00.542929', 14, 2),
       (105, 'Test Text Test Text Test Text', now(), now(),'2022-08-24 16:51:00.542929', 14, 3),
       (106, 'Test Text Test Text Test Text', now(), now(),'2022-08-24 16:51:00.542929', 14, 1);