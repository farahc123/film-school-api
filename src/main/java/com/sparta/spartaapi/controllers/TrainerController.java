package com.sparta.spartaapi.controllers;

import com.sparta.spartaapi.dtos.GetTrainerDto;
import com.sparta.spartaapi.dtos.PostTrainerDto;
import com.sparta.spartaapi.services.TrainerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/trainers")
@Tag(name = "Trainers")
class TrainerController {

    private final TrainerService trainerService;

    TrainerController(TrainerService trainerService) {
        this.trainerService = trainerService;
    }

    @Operation(summary = "Get all trainers", description = "Get a list of all trainers")
    @GetMapping
    ResponseEntity<List<GetTrainerDto>> getAllTrainers(){
        return ResponseEntity.ok(trainerService.getAllTrainers());
    }

    @Operation(summary = "Get a trainer", description = "Get a particular trainer by ID")
    @GetMapping("/{id}")
    ResponseEntity<GetTrainerDto> getTrainerById(@PathVariable Integer id) {

        try {
            return ResponseEntity.ok(trainerService.getTrainerById(id));
        }
        catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Create a trainer", description = "Create a new trainer")
    @PostMapping
    ResponseEntity<GetTrainerDto> createTrainer(@RequestBody PostTrainerDto postTrainerDto) {

        return ResponseEntity.status(HttpStatus.CREATED).body(
                trainerService.createTrainer(postTrainerDto)
        );
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete a trainer", description = "Permanently delete a trainer")
    @DeleteMapping("/{id}")
    void deleteTrainer(@PathVariable Integer id) {

        trainerService.deleteTrainer(id);
    }

}
