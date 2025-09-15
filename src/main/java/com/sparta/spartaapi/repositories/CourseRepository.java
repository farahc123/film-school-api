package com.sparta.spartaapi.repositories;

import com.sparta.spartaapi.entities.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(exported = false)
public interface CourseRepository extends JpaRepository<Course, Integer> {

    List<Course> findByTitleContainingIgnoreCase(String title);
}
