package com.farah.filmschool.dtos;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;

@Schema(name = "PostTrainerCourse")
public record PostTrainerCourseDto(
        String title,
        String description,
        LocalDate enrollDate
) {}
