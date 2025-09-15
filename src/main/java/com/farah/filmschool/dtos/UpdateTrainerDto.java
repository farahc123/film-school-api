package com.farah.filmschool.dtos;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(name = "UpdateTrainer")
public record UpdateTrainerDto(String fullName, List<Integer> courseIds) {
}