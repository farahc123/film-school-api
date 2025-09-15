package com.farah.filmschool.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(name = "PostTrainer")
public record PostTrainerDto(String fullName, @Schema(name = "courses") @JsonProperty("courses") List<PostTrainerCourseDto> postTrainerCourseDtoList) {
}
