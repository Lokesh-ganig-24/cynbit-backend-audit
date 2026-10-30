-- Cynbit Backend Audit
-- Independent reference implementation database schema
-- MySQL 8.0

CREATE DATABASE IF NOT EXISTS cynbit_audit
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_0900_ai_ci;

USE cynbit_audit;

CREATE TABLE IF NOT EXISTS contact_inquiries (
    id BIGINT NOT NULL AUTO_INCREMENT,
    company VARCHAR(150) DEFAULT NULL,
    created_at DATETIME(6) NOT NULL,
    email VARCHAR(150) NOT NULL,
    full_name VARCHAR(100) NOT NULL,
    industry VARCHAR(100) DEFAULT NULL,
    message VARCHAR(2000) NOT NULL,
    PRIMARY KEY (id)
) ENGINE=InnoDB
  DEFAULT CHARSET=utf8mb4
  COLLATE=utf8mb4_0900_ai_ci;