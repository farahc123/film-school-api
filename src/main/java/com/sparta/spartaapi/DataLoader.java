package com.sparta.spartaapi;

import com.sparta.spartaapi.entities.Course;
import com.sparta.spartaapi.entities.Trainer;
import com.sparta.spartaapi.repositories.CourseRepository;
import com.sparta.spartaapi.repositories.TrainerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Configuration
@ComponentScan(basePackages = "com.sparta.spartaapi.repositories")
public class DataLoader {

    @Bean
    @Transactional
    public CommandLineRunner loadData(TrainerRepository trainerRepository, CourseRepository courseRepository) {

        return args -> {

            if (trainerRepository.count() == 0 && courseRepository.count() == 0) {

                Trainer trainer1 = new Trainer("Sonny Crockett");
                Trainer trainer2 = new Trainer("Gina Calabrese");
                Trainer trainer3 = new Trainer("Rico Tubbs");

                trainerRepository.save(trainer1);
                trainerRepository.save(trainer2);
                trainerRepository.save(trainer3);

                Course course1 = new Course("DevOps", "DevOps training", LocalDate.of(2025, 1, 1), trainer1);
                Course course2 = new Course("Data", "Data training", LocalDate.of(2025, 2, 2), trainer2);
                Course course3 = new Course("Testing", "Testing training", LocalDate.of(2025, 3, 3), trainer3);

                courseRepository.save(course1);
                courseRepository.save(course2);
                courseRepository.save(course3);
            }
        };
    }
}
