package com.farah.filmschool.services;

import com.farah.filmschool.dtos.CourseMapper;
import com.farah.filmschool.dtos.GetCourseDto;
import com.farah.filmschool.dtos.PostCourseDto;
import com.farah.filmschool.dtos.UpdateCourseDto;
import com.farah.filmschool.entities.Course;
import com.farah.filmschool.entities.Trainer;
import com.farah.filmschool.repositories.CourseRepository;
import com.farah.filmschool.repositories.TrainerRepository;
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

    public GetCourseDto updateCourse(Integer courseId, UpdateCourseDto updateCourseDto) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new NoSuchElementException("Course not found with ID: " + courseId));

        if (updateCourseDto.title() != null) {
            course.setTitle(updateCourseDto.title());
        }
        if (updateCourseDto.description() != null) {
            course.setDescription(updateCourseDto.description());
        }
        if (updateCourseDto.enrollDate() != null) {
            course.setEnrollDate(updateCourseDto.enrollDate());
        }

        if (updateCourseDto.trainerId() != null) {
            Trainer trainer = trainerRepository.findById(updateCourseDto.trainerId())
                    .orElseThrow(() -> new NoSuchElementException("Trainer not found with ID: " + updateCourseDto.trainerId()));
            course.setTrainer(trainer);
        }

        Course savedCourse = courseRepository.save(course);
        return courseMapper.toDto(savedCourse);
    }


    public void deleteCourse(Integer id){
        courseRepository.deleteById(id);
    }
}
