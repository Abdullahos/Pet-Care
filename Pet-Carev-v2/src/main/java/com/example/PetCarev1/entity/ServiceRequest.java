package com.example.PetCarev1.entity;

import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "service_request")
public class ServiceRequest {
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

    @Transient
    private String number;
    
    private long pet_id;

    @ManyToMany
    @JoinTable(
            name = "request_task",
            joinColumns = @JoinColumn(name = "service_request_id"),
            inverseJoinColumns = @JoinColumn(name = "tast_id")
    )
    private List<Task> tasks;

    private Date dueDate;

    public ServiceRequest(){}

    public ServiceRequest(long pet_id, List<Task> tasks, Date dueDate) {
        this.pet_id = pet_id;
        this.tasks = tasks;
        this.dueDate = dueDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return "REQ_" + id;
    }


    public Long getPet_id() {
        return pet_id;
    }

    public void setPet_id(Long pet_id) {
        this.pet_id = pet_id;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }
}
