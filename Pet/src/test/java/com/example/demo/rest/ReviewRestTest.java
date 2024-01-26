package com.example.demo.rest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.BDDMockito.given;

import com.example.demo.entity.Review;
import com.example.demo.service.ReviewService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(ReviewRest.class)
class ReviewRestTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private ReviewService reviewService;

  @Test
  void testReviewApplicationSkills() throws Exception {
    Long appId = 1L; 
    Long skillId = 1L; 
    List<Review> reviews = new ArrayList<>();
    Review review = new Review();
    reviews.add(review);

    given(reviewService.reviewApplicationSkills(appId, skillId)).willReturn(reviews);

    mockMvc.perform(get("/review")
        .param("app_id", appId.toString())
        .param("skill_id", skillId.toString()))
        .andExpect(status().isOk())
        .andExpect(content().json(asJsonString(reviews)));
  }

  @Test
  void testDeleteReview() throws Exception {
    Long reviewId = 1L; 

    mockMvc.perform(delete("/review/{id}", reviewId))
        .andExpect(status().isNoContent());

    verify(reviewService).deleteReview(reviewId);
  }

  private static String asJsonString(final Object obj) {
    try {
      return new ObjectMapper().writeValueAsString(obj);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}