package com.sparta.spartaapi.controllers;


import com.sparta.spartaapi.dtos.GetCourseDto;
import com.sparta.spartaapi.dtos.PostCourseDto;
import com.sparta.spartaapi.services.CourseService;
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

}
