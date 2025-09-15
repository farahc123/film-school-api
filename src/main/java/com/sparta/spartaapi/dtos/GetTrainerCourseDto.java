package com.sparta.spartaapi.dtos;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "GetTrainerCourse")
public record GetTrainerCourseDto(Integer id, String title) {
}
