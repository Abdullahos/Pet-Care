package com.example.pets.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.pets.model.Owner;

public interface OwnerRepo extends JpaRepository<Owner, Long> {
}