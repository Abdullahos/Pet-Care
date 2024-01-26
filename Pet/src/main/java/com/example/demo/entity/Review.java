package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "review")
@Getter
@Setter
@NoArgsConstructor
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private DepartmentRole role;

    @ManyToOne
    @JoinColumn(name = "application_id")
    private Application application;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group;

    @Column(name = "skill_id")
    private Long skillId;

    @Enumerated(EnumType.STRING)
    private Status status = Status.IN_PROCESS;

    @Version
    private Long version;

    public Review(DepartmentRole role, Application application, Long skillId) {
        this.role = role;
        this.application = application;
        this.skillId = skillId;
    }
}
