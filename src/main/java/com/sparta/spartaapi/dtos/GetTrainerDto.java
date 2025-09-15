package com.sparta.spartaapi.dtos;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(name = "GetTrainer")
public record GetTrainerDto(Integer id, String fullName, List<GetTrainerCourseDto> courses) {
}
