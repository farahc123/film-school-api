package com.sparta.spartaapi.dtos;

import com.sparta.spartaapi.entities.Course;
import com.sparta.spartaapi.entities.Trainer;
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
