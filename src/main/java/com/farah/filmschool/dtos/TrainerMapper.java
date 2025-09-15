package com.farah.filmschool.dtos;

import com.farah.filmschool.entities.Trainer;
import com.farah.filmschool.entities.Course;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TrainerMapper {
    TrainerMapper INSTANCE = Mappers.getMapper(TrainerMapper.class);

    GetTrainerDto toDto(Trainer trainer);

    @Mapping(target = "courses", ignore = true)
    Trainer toEntity(PostTrainerDto trainerDto);

    default Course mapCourseDtoToCourse(PostTrainerCourseDto dto, Trainer trainer) {
        Course course = new Course();
        course.setTitle(dto.title());
        course.setDescription(dto.description());
        course.setEnrollDate(dto.enrollDate());
        course.setTrainer(trainer);
        return course;
    }

    // Map list of PostTrainerCourseDto → list of Course
    default List<Course> mapCourseDtoListToCourseList(List<PostTrainerCourseDto> dtos, Trainer trainer) {
        return dtos.stream()
                .map(dto -> mapCourseDtoToCourse(dto, trainer))
                .toList();
    }
}
