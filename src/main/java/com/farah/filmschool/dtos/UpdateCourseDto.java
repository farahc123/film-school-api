package com.farah.filmschool.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;

@Schema(name = "UpdateCourse")
public record UpdateCourseDto(
        String title,
        String description,
        LocalDate enrollDate,
        Integer trainerId // allows reassigning trainers if desired (as does the update trainer method)
) {}

