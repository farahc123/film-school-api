package com.farah.filmschool.services;

import com.farah.filmschool.dtos.*;
import com.farah.filmschool.entities.Course;
import com.farah.filmschool.entities.Trainer;
import com.farah.filmschool.repositories.CourseRepository;
import com.farah.filmschool.repositories.TrainerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CourseServiceTest {

    private CourseRepository courseRepository;
    private TrainerRepository trainerRepository;
    private CourseMapper courseMapper;
    private CourseService courseService;

    @BeforeEach
    void setUp() {
        courseRepository = mock(CourseRepository.class);
        trainerRepository = mock(TrainerRepository.class);
        courseMapper = mock(CourseMapper.class);
        courseService = new CourseService(courseRepository, trainerRepository, courseMapper);
    }

    @Test
    void getCourseById_whenExists_returnsDto() {
        // creates course entity
        Course course = new Course();
        course.setId(1);
        course.setTitle("Test");
        course.setDescription("Desc");
        course.setEnrollDate(LocalDate.now());

        // creates trainer DTO for the course
        GetCourseTrainerDto trainerDto = new GetCourseTrainerDto(1, "Trainer Name");

        // creates course DTO that will be returned by the mapper
        GetCourseDto dto = new GetCourseDto(
                1,
                "Test",
                "Desc",
                course.getEnrollDate(),
                trainerDto
        );

        // mock repository and mapper behavior
        when(courseRepository.findById(1)).thenReturn(Optional.of(course));
        when(courseMapper.toDto(course)).thenReturn(dto);

        // calls service
        GetCourseDto result = courseService.getCourseById(1);

        // asserts results
        assertEquals(1, result.id());
        assertEquals("Test", result.title());
        assertEquals("Desc", result.description());
        assertEquals(trainerDto, result.trainer());
    }


    @Test
    void getCourseById_whenNotFound_throwsException() {
        when(courseRepository.findById(1)).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class, () -> courseService.getCourseById(1));
    }

    @Test
    void createCourse_withNull_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> courseService.createCourse(null));
    }

    @Test
    void createCourse_valid_savesCourse() {
        // arranges input DTO
        PostCourseDto postDto = new PostCourseDto(
                "Title",
                "Desc",
                LocalDate.now(),
                1
        );

        // mocked trainer entity
        Trainer trainer = new Trainer();
        trainer.setId(1);
        trainer.setFullName("Trainer Name");

        // mocked course entity
        Course course = new Course();
        course.setTitle("Title");
        course.setDescription("Desc");
        course.setEnrollDate(postDto.enrollDate());
        course.setTrainer(trainer);

        // Trainer DTO for GetCourseDto
        GetCourseTrainerDto trainerDto = new GetCourseTrainerDto(1, "Trainer Name");

        // expected course DTO returned by mapper
        GetCourseDto dto = new GetCourseDto(
                1,
                "Title",
                "Desc",
                postDto.enrollDate(),
                trainerDto
        );

        // mocks repository and mapper behaviours
        when(trainerRepository.getReferenceById(1)).thenReturn(trainer);
        when(courseMapper.toEntity(postDto, trainer)).thenReturn(course);
        when(courseRepository.save(course)).thenReturn(course);
        when(courseMapper.toDto(course)).thenReturn(dto);

        // calls service method
        GetCourseDto result = courseService.createCourse(postDto);

        // verifies results
        assertEquals("Title", result.title());
        assertEquals("Desc", result.description());
        assertEquals(trainerDto, result.trainer());

        // Verify repository save was called
        verify(courseRepository).save(course);
    }


    // Edge case: updating non-existent course
    @Test
    void updateCourse_nonExistent_throwsException() {
        when(courseRepository.findById(99)).thenReturn(Optional.empty());
        UpdateCourseDto updateDto = new UpdateCourseDto(null, null, null, null);
        assertThrows(NoSuchElementException.class, () -> courseService.updateCourse(99, updateDto));
    }
}
