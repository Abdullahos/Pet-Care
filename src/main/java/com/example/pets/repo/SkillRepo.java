package com.example.pets.repo;

import com.example.pets.model.Skill;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SkillRepo extends JpaRepository<Skill, Long> {
    // Custom query methods if needed
}