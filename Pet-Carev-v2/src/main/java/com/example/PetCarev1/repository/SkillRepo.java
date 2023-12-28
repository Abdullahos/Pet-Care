package com.example.PetCarev1.repository;

import com.example.PetCarev1.entity.Employee;
import com.example.PetCarev1.entity.Skill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SkillRepo extends JpaRepository<Skill, Long> {

}
