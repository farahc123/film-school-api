package com.farah.filmschool.controllers;


import com.farah.filmschool.dtos.GetCourseDto;
import com.farah.filmschool.dtos.PostCourseDto;
import com.farah.filmschool.dtos.UpdateCourseDto;
import com.farah.filmschool.services.CourseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/courses")
@Tag(name = "Courses")
public class CourseController {

    private final CourseService courseService;

    CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @Operation(summary = "Get all courses", description = "Retrieve a list of all available courses")
    @GetMapping
    ResponseEntity<List<GetCourseDto>> getAllCourses() {
        return ResponseEntity.ok(courseService.getAllCourses());
    }

    @Operation(summary = "Get a course", description = "Get a particular course by ID")
    @GetMapping("/{id}")
    ResponseEntity<GetCourseDto> getCourseById(@PathVariable Integer id) {

        try {
            return ResponseEntity.ok(courseService.getCourseById(id));
        }
        catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Create a course", description = "Create a new course")
    @PostMapping
    ResponseEntity<GetCourseDto> createCourse(@RequestBody PostCourseDto postCourseDto) {

        return ResponseEntity.status(HttpStatus.CREATED).body(
                courseService.createCourse(postCourseDto)
        );
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a course and its trainer (optional)", description = "Update course details")
    public ResponseEntity<GetCourseDto> updateCourse(@PathVariable Integer id,
                                                     @RequestBody UpdateCourseDto updateCourseDto) {
        try {
            return ResponseEntity.ok(courseService.updateCourse(id, updateCourseDto));
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete a course", description = "Permanently delete a course")
    @DeleteMapping("/{id}")
    void deleteTrainer(@PathVariable Integer id) {

        courseService.deleteCourse(id);
    }


}
