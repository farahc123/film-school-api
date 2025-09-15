package com.sparta.spartaapi.services;

import com.sparta.spartaapi.dtos.CourseMapper;
import com.sparta.spartaapi.dtos.GetCourseDto;
import com.sparta.spartaapi.dtos.PostCourseDto;
import com.sparta.spartaapi.dtos.TrainerMapper;
import com.sparta.spartaapi.repositories.CourseRepository;
import com.sparta.spartaapi.repositories.TrainerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class CourseService {

    private final CourseRepository courseRepository;
    private final TrainerRepository trainerRepository;
    private final CourseMapper courseMapper;

    public CourseService(CourseRepository courseRepository, TrainerRepository trainerRepository, CourseMapper mapper) {
        this.courseRepository = courseRepository;
        this.trainerRepository = trainerRepository;
        this.courseMapper = mapper;
    }

    public List<GetCourseDto> getAllCourses(){

        return courseRepository.findAll()
                .stream()
                .map(courseMapper::toDto)
                .toList();
    }

    public GetCourseDto getCourseById(Integer id){

        return courseRepository.findById(id)
                .map(courseMapper::toDto)
                .orElseThrow(() -> new NoSuchElementException("Course not found"));
    }

    public GetCourseDto createCourse(PostCourseDto postCourseDto){

        if(postCourseDto == null){
            throw new IllegalArgumentException("PostCourseDto cannot be null");
        }

        var newCourse = courseMapper.toEntity(postCourseDto, trainerRepository.getReferenceById(postCourseDto.trainerId()));

        return courseMapper.toDto(courseRepository.save(newCourse));
    }

    public void deleteCourse(Integer id){
        courseRepository.deleteById(id);
    }
}
