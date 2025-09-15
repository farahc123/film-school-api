package com.sparta.spartaapi.services;

import com.sparta.spartaapi.dtos.GetTrainerDto;
import com.sparta.spartaapi.dtos.PostTrainerDto;
import com.sparta.spartaapi.dtos.TrainerMapper;
import com.sparta.spartaapi.entities.Course;
import com.sparta.spartaapi.entities.Trainer;
import com.sparta.spartaapi.repositories.TrainerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class TrainerService {

    private final TrainerRepository trainerRepository;
    private final TrainerMapper trainerMapper;

    TrainerService(TrainerRepository trainerRepository, TrainerMapper trainerMapper) {
        this.trainerRepository = trainerRepository;
        this.trainerMapper = trainerMapper;
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

    private static Trainer updateCoursesTrainer(Trainer trainer) {

        for (Course course: trainer.getCourses()){
            course.setTrainer(trainer);
        }
        return trainer;
    }

    public void deleteTrainer(Integer id){
        trainerRepository.deleteById(id);
    }
}
