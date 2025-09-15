-- src/main/resources/init-schema.sql

CREATE SCHEMA IF NOT EXISTS film_school;

CREATE TABLE IF NOT EXISTS film_school.trainers (
                                                    id SERIAL PRIMARY KEY,
                                                    full_name VARCHAR(100) NOT NULL
    );

CREATE TABLE IF NOT EXISTS film_school.courses (
                                                   id SERIAL PRIMARY KEY,
                                                   title VARCHAR(50) NOT NULL,
    description TEXT NOT NULL,
    enroll_date DATE NOT NULL,
    trainer_id INT NOT NULL REFERENCES film_school.trainers(id) ON DELETE CASCADE
    );
