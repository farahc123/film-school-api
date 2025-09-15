package com.farah.filmschool.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

@Entity
@Table(name = "courses", schema = "film_school", indexes = {
        @Index(name = "id", columnList = "id"),
        @Index(name = "idx_title", columnList = "title"),
        @Index(name = "idx_enroll_date", columnList = "enroll_date"),
        @Index(name = "idx_trainer_id", columnList = "trainer_id")
})
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 50)
    @NotNull
    @Column(name = "title", nullable = false, length = 50)
    private String title;

    @NotNull
    @Column(name = "description", nullable = false)
    private String description;

    @NotNull
    @Column(name = "enroll_date", nullable = false)
    private LocalDate enrollDate;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "trainer_id", nullable = false)
    @JsonBackReference  // prevents infinite recursion
    private Trainer trainer;

    public Course(String title, Trainer trainer) {
        this.title = title;
        this.trainer = trainer;
    }

    public Course(String title, String description, LocalDate enrollDate, Trainer trainer) {
        this.title = title;
        this.description = description;
        this.enrollDate = enrollDate;
        this.trainer = trainer;
    }

    public Course() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getEnrollDate() {
        return enrollDate;
    }

    public void setEnrollDate(LocalDate enrollDate) {
        this.enrollDate = enrollDate;
    }

    public Trainer getTrainer() {
        return trainer;
    }

    public void setTrainer(Trainer trainer) {
        this.trainer = trainer;
    }
}

