package com.example.PetCarev1.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "service_request")
@Getter
@Setter

public class ServiceRequest {
    @Id
    @SequenceGenerator(
            name = "employee_sequence",
            sequenceName = "employee_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "employee_sequence"
    )
    private Long id;

    private Long petId;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime DueDate;

    @ManyToMany
    @JoinTable(
            name = "service_request_employee",
            joinColumns = @JoinColumn(name = "service_request_id"),
            inverseJoinColumns = @JoinColumn(name = "employee_id")
    )

    @EqualsAndHashCode.Exclude
    private List<Skill> skills = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "service_request_skill",
            joinColumns = @JoinColumn(name = "service_request_id"),
            inverseJoinColumns = @JoinColumn(name = "skill_id")
    )
    @EqualsAndHashCode.Exclude
    private List<Employee> employees = new ArrayList<>();


    public ServiceRequest(){}

    public ServiceRequest(Long id, Long petId, LocalDateTime dueDate) {
        this.id = id;
        this.petId = petId;
        DueDate = dueDate;
    }
}
