package com.farah.filmschool;

import com.farah.filmschool.entities.Course;
import com.farah.filmschool.entities.Trainer;
import com.farah.filmschool.repositories.CourseRepository;
import com.farah.filmschool.repositories.TrainerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Configuration
@ComponentScan(basePackages = "com.farah.filmschool.repositories")
public class DataLoader {

    @Bean
    @Transactional
    public CommandLineRunner loadData(TrainerRepository trainerRepository, CourseRepository courseRepository) {
        return args -> {
            if (trainerRepository.count() == 0 && courseRepository.count() == 0) {

                // create trainers
                Trainer trainer1 = new Trainer("Martin Scorsese");
                Trainer trainer2 = new Trainer("Thelma Schoonmaker");
                Trainer trainer3 = new Trainer("Ruth Prawer Jhabvala");

                // saves trainers
                trainerRepository.save(trainer1);
                trainerRepository.save(trainer2);
                trainerRepository.save(trainer3);

                // flushes to ensure IDs are generated before assigning them to courses
                trainerRepository.flush();

                // creates courses linked to trainers
                Course course1 = new Course(
                        "Directing",
                        "Learn how to be a director from one of the best",
                        LocalDate.of(2025, 1, 1),
                        trainer1
                );

                Course course2 = new Course(
                        "Editing",
                        "Editing tips from the GOAT",
                        LocalDate.of(2025, 2, 2),
                        trainer2
                );

                Course course3 = new Course(
                        "Adapting novels for the screen",
                        "'A Room with a View', 'Howard's End', 'The Remains of the Day'... hear from the Merchant Ivory legend",
                        LocalDate.of(2025, 3, 3),
                        trainer3
                );

                // saves courses
                courseRepository.save(course1);
                courseRepository.save(course2);
                courseRepository.save(course3);

                // flushing courses ensures DB persistence immediately
                courseRepository.flush();
            }
        };
    }
}