package com.example.demo.service;

import com.example.demo.entity.Review;
import com.example.demo.repository.ReviewRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepo reviewRepo;

    public List<Review> reviewApplicationSkills(Long appId, Long skillId) {
        List<Review> review = reviewRepo.findReviewByApplicationAndSkillId(appId, skillId);
        return review;
    }

    public void deleteReview(Long id) {
        reviewRepo.deleteById(id);
    }
}
