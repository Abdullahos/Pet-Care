package com.example.demo.entity;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "group_")
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    @OneToMany(mappedBy = "group")
    private Set<Review> reviews = new HashSet<>();

    @Enumerated(EnumType.STRING)
    private DepartmentRole role;

    public void add(Review review) {
        review.setGroup(this);
        reviews.add(review);
    }
}
