package com.farah.filmschool.services;

import com.farah.filmschool.dtos.GetTrainerDto;
import com.farah.filmschool.dtos.PostTrainerDto;
import com.farah.filmschool.dtos.TrainerMapper;
import com.farah.filmschool.dtos.UpdateTrainerDto;
import com.farah.filmschool.entities.Course;
import com.farah.filmschool.entities.Trainer;
import com.farah.filmschool.repositories.CourseRepository;
import com.farah.filmschool.repositories.TrainerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class TrainerService {

    private final TrainerRepository trainerRepository;
    private final TrainerMapper trainerMapper;
    private final CourseRepository courseRepository;

    TrainerService(TrainerRepository trainerRepository, TrainerMapper trainerMapper, CourseRepository courseRepository) {
        this.trainerRepository = trainerRepository;
        this.trainerMapper = trainerMapper;
        this.courseRepository = courseRepository;
    }

    public List<GetTrainerDto> getAllTrainers() {

        return trainerRepository.findAll()
                .stream()
                .map(trainerMapper::toDto)
                .toList();
    }

    public GetTrainerDto getTrainerById(Integer id) {

        return trainerRepository.findById(id)
                .map(trainerMapper::toDto)
                .orElseThrow(() -> new NoSuchElementException("Trainer not found") );
    }

    public GetTrainerDto createTrainer(PostTrainerDto postTrainerDto) {

        if (postTrainerDto == null) {
            throw new IllegalArgumentException("PostTrainer cannot be null");
        }

        Trainer trainer = trainerMapper.toEntity(postTrainerDto);

        trainer.setCourses(trainerMapper.mapCourseDtoListToCourseList(postTrainerDto.postTrainerCourseDtoList(), trainer));

        trainer = trainerRepository.save(trainer);

        return trainerMapper.toDto(trainer);
    }

    public GetTrainerDto updateTrainer(Integer id, UpdateTrainerDto updateTrainerDto) {
        Trainer trainer = trainerRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Trainer not found"));

        // Update the name
        trainer.setFullName(updateTrainerDto.fullName());

        // Update the courses if provided
        if (updateTrainerDto.courseIds() != null) {
            List<Course> courses = updateTrainerDto.courseIds().stream()
                    .map(courseId -> courseRepository.findById(courseId)
                            .orElseThrow(() -> new NoSuchElementException("Course not found with ID: " + courseId)))
                    .toList();

            // Link courses to this trainer
            for (Course course : courses) {
                course.setTrainer(trainer);
            }

            // Replace existing courses
            trainer.getCourses().clear();
            trainer.getCourses().addAll(courses);
        }

        Trainer savedTrainer = trainerRepository.save(trainer);
        return trainerMapper.toDto(savedTrainer);
    }


    public void deleteTrainer(Integer id){
        trainerRepository.deleteById(id);
    }
}
