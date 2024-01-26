package com.example.demo.rest;

import com.example.demo.entity.Review;
import com.example.demo.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("review")
@RequiredArgsConstructor
public class ReviewRest {
  private final ReviewService reviewService;

  @GetMapping
  public List<Review> reviewApplicationSkills(@RequestParam("app_id") Long appId,
      @RequestParam("skill_id") Long skillId) {
    List<Review> review = reviewService.reviewApplicationSkills(appId, skillId);
    return review;
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteReview(@PathVariable Long id) {
    reviewService.deleteReview(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}
