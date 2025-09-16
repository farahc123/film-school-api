package com.farah.filmschool.dtos;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;

@Schema(name = "GetCourse")
public record GetCourseDto(
        Integer id,
        String title,
        String description,
        LocalDate enrollDate,
        @Schema(name = "trainer") GetCourseTrainerDto trainer) {
}
