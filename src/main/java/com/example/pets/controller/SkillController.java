package com.example.pets.controller;

import com.example.pets.model.Skill;
import com.example.pets.repo.SkillRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/skills")
public class SkillController {

  private final SkillRepo skillRepo;

  @Autowired
  public SkillController(SkillRepo skillRepo) {
    this.skillRepo = skillRepo;
  }

  @PostMapping
  public ResponseEntity<Skill> createSkill(@RequestBody Skill skill) {
    Skill savedSkill = skillRepo.save(skill);
    return new ResponseEntity<>(savedSkill, HttpStatus.CREATED);
  }

  @GetMapping
  public ResponseEntity<Iterable<Skill>> getAllSkills() {
    Iterable<Skill> skills = skillRepo.findAll();
    return new ResponseEntity<>(skills, HttpStatus.OK);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Skill> getSkillById(@PathVariable Long id) {
    return skillRepo.findById(id)
        .map(skill -> new ResponseEntity<>(skill, HttpStatus.OK))
        .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }
}
