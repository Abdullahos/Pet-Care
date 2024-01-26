package com.example.demo.rest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import java.util.*;
import com.example.demo.entity.Application;

// Correct the MediaType import
import org.springframework.http.MediaType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.example.demo.dto.ApplicationRequest;
import com.example.demo.service.ApplicationService;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.verify;

@WebMvcTest(ApplicationRest.class)
class ApplicationRestTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private ApplicationService applicationService;

  @Test
  void testSaveApplication() throws Exception {
    ApplicationRequest applicationRequest = new ApplicationRequest();
    applicationRequest.firstName = "John";
    applicationRequest.lastName = "Doe";
    applicationRequest.email = "john.doe@example.com";
    applicationRequest.description = "An example application";

    applicationRequest.skillList = Arrays.asList(1L, 2L);

    mockMvc.perform(post("/application")
        .contentType(MediaType.APPLICATION_JSON)
        .content(asJsonString(applicationRequest)))
        .andExpect(status().isCreated());

    verify(applicationService).saveApplication(any(ApplicationRequest.class));
  }

  @Test
  void testShowApplicationById() throws Exception {
    Long applicationId = 1L;
    Application application = new Application();
    application.setFirstName("John");
    application.setLastName("Doe");
    application.setEmail("john.doe@example.com");
    application.setDescription("An example application");

    application.setSkills(new ArrayList<>());

    given(applicationService.findApplicationById(applicationId)).willReturn(application);

    mockMvc.perform(get("/application/{id}", applicationId))
        .andExpect(status().isOk())
        .andExpect(content().json(asJsonString(application)));
  }


  private static String asJsonString(final Object obj) {
    try {
      return new ObjectMapper().writeValueAsString(obj);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}