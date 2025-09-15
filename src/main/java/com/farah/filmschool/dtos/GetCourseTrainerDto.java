package com.farah.filmschool.dtos;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "GetCourseTrainer")
public record GetCourseTrainerDto(Integer id, String fullName) {
}
