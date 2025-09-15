package com.sparta.spartaapi.dtos;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;

@Schema(name = "PostCourse")
public record PostCourseDto(String title, String description, LocalDate enrollDate, Integer trainerId) {
}
