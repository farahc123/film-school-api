package com.farah.filmschool.services;

import com.farah.filmschool.dtos.GetTrainerDto;
import com.farah.filmschool.dtos.PostTrainerDto;
import com.farah.filmschool.dtos.TrainerMapper;
import com.farah.filmschool.dtos.UpdateTrainerDto;
import com.farah.filmschool.entities.Course;
import com.farah.filmschool.entities.Trainer;
import com.farah.filmschool.repositories.CourseRepository;
import com.farah.filmschool.repositories.TrainerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TrainerServiceTests {

    @Mock
    private TrainerRepository trainerRepository;

    @Mock
    private TrainerMapper trainerMapper;

    @Mock
    private CourseRepository courseRepository;

    @InjectMocks
    private TrainerService trainerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllTrainers_returnsTrainerList() {
        Trainer trainer1 = new Trainer("Alice");
        Trainer trainer2 = new Trainer("Bob");

        GetTrainerDto dto1 = new GetTrainerDto(1, "Alice", List.of());
        GetTrainerDto dto2 = new GetTrainerDto(2, "Bob", List.of());

        when(trainerRepository.findAll()).thenReturn(List.of(trainer1, trainer2));
        when(trainerMapper.toDto(trainer1)).thenReturn(dto1);
        when(trainerMapper.toDto(trainer2)).thenReturn(dto2);

        List<GetTrainerDto> result = trainerService.getAllTrainers();

        assertEquals(2, result.size());
        assertEquals("Alice", result.get(0).fullName());
        assertEquals("Bob", result.get(1).fullName());
    }

    @Test
    void getTrainerById_whenExists_returnsDto() {
        Trainer trainer = new Trainer("Alice");
        trainer.setId(1);

        GetTrainerDto dto = new GetTrainerDto(1, "Alice", List.of());

        when(trainerRepository.findById(1)).thenReturn(Optional.of(trainer));
        when(trainerMapper.toDto(trainer)).thenReturn(dto);

        GetTrainerDto result = trainerService.getTrainerById(1);

        assertEquals(1, result.id());
        assertEquals("Alice", result.fullName());
    }

    @Test
    void getTrainerById_whenNotExists_throwsException() {
        when(trainerRepository.findById(99)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> trainerService.getTrainerById(99));
    }

    @Test
    void createTrainer_valid_savesTrainer() {
        PostTrainerDto postDto = new PostTrainerDto("Alice", List.of());
        Trainer trainer = new Trainer("Alice");
        trainer.setCourses(List.of());
        GetTrainerDto dto = new GetTrainerDto(1, "Alice", List.of());

        when(trainerMapper.toEntity(postDto)).thenReturn(trainer);
        when(trainerMapper.mapCourseDtoListToCourseList(postDto.postTrainerCourseDtoList(), trainer))
                .thenReturn(List.of());
        when(trainerRepository.save(trainer)).thenReturn(trainer);
        when(trainerMapper.toDto(trainer)).thenReturn(dto);

        GetTrainerDto result = trainerService.createTrainer(postDto);

        assertEquals("Alice", result.fullName());
        verify(trainerRepository).save(trainer);
    }

    @Test
    void updateTrainer_valid_updatesTrainerAndCourses() {
        Trainer trainer = new Trainer("Alice");
        trainer.setId(1);
        Course course1 = new Course();
        course1.setId(10);
        List<Integer> newCourseIds = List.of(10);

        UpdateTrainerDto updateDto = new UpdateTrainerDto("Alice Updated", newCourseIds);
        when(trainerRepository.findById(1)).thenReturn(Optional.of(trainer));
        when(courseRepository.findById(10)).thenReturn(Optional.of(course1));
        when(trainerRepository.save(trainer)).thenReturn(trainer);
        when(trainerMapper.toDto(trainer)).thenReturn(new GetTrainerDto(1, "Alice Updated", List.of()));

        GetTrainerDto result = trainerService.updateTrainer(1, updateDto);

        assertEquals("Alice Updated", result.fullName());
        assertEquals(trainer, course1.getTrainer());
        verify(trainerRepository).save(trainer);
    }

    @Test
    void deleteTrainer_callsRepository() {
        trainerService.deleteTrainer(1);
        verify(trainerRepository).deleteById(1);
    }
}
