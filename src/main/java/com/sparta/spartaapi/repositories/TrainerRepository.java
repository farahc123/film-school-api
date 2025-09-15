package com.sparta.spartaapi.repositories;

import com.sparta.spartaapi.entities.Trainer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(exported = false)
public interface TrainerRepository extends JpaRepository<Trainer, Integer> {

}
