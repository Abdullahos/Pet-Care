package com.example.demo.service;

import com.example.demo.dto.ApplicationRequest;
import com.example.demo.entity.Application;
import com.example.demo.entity.DepartmentRole;
import com.example.demo.entity.Review;
import com.example.demo.entity.Skill;
import com.example.demo.repository.ApplicationRepo;
import com.example.demo.repository.ReviewRepo;
import com.example.demo.repository.SkillRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.EnumSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ApplicationService {

    private final ApplicationRepo applicationRepo;
    private final ReviewRepo reviewRepo;
    private final SkillRepo skillRepo;

    @Transactional
    public void saveApplication(ApplicationRequest request) {
        Application application = new Application(
                request.firstName,
                request.lastName,
                request.email,
                request.description
        );
        List<Skill> skillList = skillRepo.findAllById(request.skillList);
        application.setSkills(skillList);
        applicationRepo.save(application);
        List<DepartmentRole> roleList = EnumSet.allOf(DepartmentRole.class).stream().toList();

        for (Long skillId : request.skillList) {
            for (DepartmentRole role : roleList) {
                Review review = new Review(
                        role,
                        application,
                        skillId
                );
                reviewRepo.save(review);
            }
        }
    }

    public List<Application> findAllApplications() {
        return applicationRepo.findAll();
    }

    public Application findApplicationById(@PathVariable Long id) {
        return applicationRepo.findById(id).
                orElseThrow();
    }

}
