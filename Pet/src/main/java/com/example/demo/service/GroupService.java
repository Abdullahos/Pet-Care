package com.example.demo.service;

import com.example.demo.dto.ReviewRequest;
import com.example.demo.entity.*;
import com.example.demo.repository.ApplicationRepo;
import com.example.demo.repository.ApprovedApplicationRepo;
import com.example.demo.repository.GroupRepo;
import com.example.demo.repository.ReviewRepo;
import jakarta.persistence.OptimisticLockException;
import lombok.RequiredArgsConstructor;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

@Service
@RequiredArgsConstructor
public class GroupService {
    private final GroupRepo groupRepo;
    private final ReviewRepo reviewRepo;
    private final ApplicationRepo applicationRepo;
    private final ApprovedApplicationRepo approvedApplicationRepo;


    @Transactional
    @Retryable(retryFor = OptimisticLockException.class, maxAttempts = 5)
    public void makeDecision(ReviewRequest request) {
        if (request.decision.trim().equalsIgnoreCase(Status.REJECT.toString())) {
            reviewRepo.findReviewByApplicationId(request.applicationId)
                    .forEach(review -> reviewRepo.deleteById(review.getId()));
            if (reviewRepo.findReviewByApplicationId(request.applicationId) == null) {
                applicationRepo.deleteById(request.applicationId);
            }
            return;
        }

        if (request.decision.trim().equalsIgnoreCase(Status.ACCEPT.toString())) {
            List<Review> reviews = reviewRepo.findReviewByApplicationAndSkillId(request.applicationId, request.skillId);
            if (reviews.size() == 1) {
                ApproveApplications approveApplications = new ApproveApplications(request.applicationId, request.skillId);
                approvedApplicationRepo.save(approveApplications);
            }

            reviewRepo.deleteReviewByApplicationAndSkillIdAndRole(
                    request.applicationId,
                    request.skillId,
                    DepartmentRole.valueOf(request.role.trim().toUpperCase())
            );
        }
    }

}
