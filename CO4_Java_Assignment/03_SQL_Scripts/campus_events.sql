CREATE DATABASE IF NOT EXISTS campus_events;
USE campus_events;
CREATE TABLE IF NOT EXISTS registrations (
 registration_id BIGINT PRIMARY KEY AUTO_INCREMENT,
 student_id VARCHAR(30) NOT NULL,
 student_name VARCHAR(120) NOT NULL,
 event_name VARCHAR(100) NOT NULL,
 created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
 CONSTRAINT uq_student_event UNIQUE(student_id,event_name)
);
