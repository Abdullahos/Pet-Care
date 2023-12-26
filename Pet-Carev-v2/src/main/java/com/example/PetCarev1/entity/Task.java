package com.example.PetCarev1.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "task")
public class Task {
    @Id
    @SequenceGenerator(
            name = "pet_sequence",
            sequenceName = "pet_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "pet_sequence"
    )
    private Long id;

    private String name;

    private int cost;

    private int durationWithMinutes;

    @ManyToMany
    @JoinTable(
            name = "employee_task"
    )
    private List<Employee> employees;

    public Task(){}

    public Task(String name, int cost, int durationWithMinutes) {
        this.name = name;
        this.cost = cost;
        this.durationWithMinutes = durationWithMinutes;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public int getDurationWithMinutes() {
        return durationWithMinutes;
    }

    public void setDurationWithMinutes(int durationWithMinutes) {
        this.durationWithMinutes = durationWithMinutes;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }
}
