package com.example.pets.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.pets.model.Pet;

public interface PetRepo extends JpaRepository<Pet, Long> {
}