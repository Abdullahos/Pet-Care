package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "approved_application")
@Getter
@Setter
@NoArgsConstructor
public class ApproveApplications {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "application_id")
    private Long applicationId;

    @Column(name = "skill_id")
    private Long skillId;

    public ApproveApplications(Long applications, Long skillId) {
        this.applicationId = applications;
        this.skillId = skillId;
    }
}
