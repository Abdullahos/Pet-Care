package com.example.pets.model;

import java.util.HashSet;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.Set;
// import com.example.pets.model.Skill;

@Entity
@Table(name = "employee")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Employee {
  @Id
  @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
  @Column(name = "employee_id")
  private Long id;

  @NotBlank(message = "Name cannot be blank")
  private String name;

  @NotBlank(message = "Email cannot be blank")
  @Email(message = "Email should be valid")
  private String email;

  @NotBlank(message = "Password cannot be blank")
  private String password;

  @Embedded
  private Timetable timetable = new Timetable();

  @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
  @JoinTable(name = "employee_skills", joinColumns = @JoinColumn(name = "employee_id"), inverseJoinColumns = @JoinColumn(name = "skill_id"))
  private Set<Skill> skills = new HashSet<>();

}
