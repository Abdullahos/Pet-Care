package com.example.pets.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "skills")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Skill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ElementCollection
    @CollectionTable(name = "skill_proficiencies", joinColumns = @JoinColumn(name = "skill_id"))
    @Column(name = "employee_id")
    private List<Long> proficientEmployeeIds = new ArrayList<>();

    private int time;
}
