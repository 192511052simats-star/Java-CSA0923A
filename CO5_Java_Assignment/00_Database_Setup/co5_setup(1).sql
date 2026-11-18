CREATE DATABASE IF NOT EXISTS campus_transport;
USE campus_transport;

CREATE TABLE IF NOT EXISTS bus_pass (
    pass_id VARCHAR(30) PRIMARY KEY,
    reg_no VARCHAR(30) NOT NULL,
    name VARCHAR(100) NOT NULL,
    route_no INT NOT NULL,
    amount DOUBLE NOT NULL
);

CREATE TABLE IF NOT EXISTS users (
    id VARCHAR(60) PRIMARY KEY,
    pwd VARCHAR(100) NOT NULL
);

INSERT INTO users(id, pwd)
VALUES ('student1', 'pass123')
ON DUPLICATE KEY UPDATE pwd = VALUES(pwd);

SELECT DATABASE();
SHOW TABLES;
SELECT id FROM users;
