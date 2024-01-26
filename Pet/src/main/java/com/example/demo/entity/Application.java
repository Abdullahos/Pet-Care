package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "applicati")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String FirstName;

    @Column(name = "last_name")
    private String lastName;

    private String email;

    private String description;

    @ManyToMany
    @JoinTable(
            name = "Application_skill",
            joinColumns = @JoinColumn(name = "application_id"),
            inverseJoinColumns = @JoinColumn(name = "skill_id")
    )
    private List<Skill> skills = new ArrayList<>();

    @Version
    private Long version;

    public Application(String firstName, String lastName, String email, String description) {
        FirstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.description = description;
    }
}
