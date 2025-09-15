package com.farah.filmschool.dtos;

import com.farah.filmschool.entities.Course;
import com.farah.filmschool.entities.Trainer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CourseMapper {

    CourseMapper INSTANCE = Mappers.getMapper(CourseMapper.class);

    @Mapping(target = "trainer", source = "trainer")
    GetCourseDto toDto(Course course);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "trainer", source = "trainer")
    Course toEntity(PostCourseDto postCourseDto, Trainer trainer);
}
