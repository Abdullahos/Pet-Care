package com.example.demo.rest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;


import com.example.demo.dto.ReviewRequest;
import com.example.demo.service.GroupService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(GroupRest.class)
class GroupRestTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private GroupService groupService;

  @Test
  void testMakeDecision() throws Exception {
    ReviewRequest reviewRequest = new ReviewRequest();
    reviewRequest.applicationId = 1L;
    reviewRequest.skillId = 1L;
    reviewRequest.role = "Reviewer";
    reviewRequest.decision = "APPROVED";

    mockMvc.perform(post("/group")
        .contentType(MediaType.APPLICATION_JSON)
        .content(asJsonString(reviewRequest)))
        .andExpect(status().isOk());

    verify(groupService).makeDecision(any(ReviewRequest.class));
  }

  // Helper method to convert objects to JSON strings
  private static String asJsonString(final Object obj) {
    try {
      return new ObjectMapper().writeValueAsString(obj);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
