package com.farah.filmschool.repositories;

import com.farah.filmschool.entities.Trainer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(exported = false)
public interface TrainerRepository extends JpaRepository<Trainer, Integer> {

}
