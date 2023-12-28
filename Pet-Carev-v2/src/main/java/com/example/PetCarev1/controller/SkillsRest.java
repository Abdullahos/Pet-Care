package com.example.PetCarev1.controller;

import com.example.PetCarev1.entity.Employee;
import com.example.PetCarev1.entity.Skill;
import com.example.PetCarev1.repository.SkillRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("skills")

public class SkillsRest {

    @Autowired
    private SkillRepo skillRepo;
    @GetMapping
    public List<Skill> findAll(){

        return skillRepo.findAll();
    }

    @GetMapping("/{id}")
    public Skill findById(@PathVariable long id){

        return skillRepo.findById(id).get();
    }

    @PostMapping
    public Skill save(@RequestBody Skill skill){

        return skillRepo.save(skill);
    }

    @PostMapping("add-employee/{id}")
    public Skill save(@PathVariable long id,@RequestBody Employee employee){
        Skill skill = skillRepo.findById(id).orElseThrow(()->new RuntimeException("not found"));
        skill.getEmployees().add(employee);
        return skillRepo.save(skill);
    }
}
