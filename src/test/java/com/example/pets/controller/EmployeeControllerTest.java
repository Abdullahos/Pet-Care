package com.example.pets.controller;

import com.example.pets.controller.EmployeeController;
import com.example.pets.model.Employee;
import com.example.pets.repo.EmployeeRepo;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import org.springframework.http.MediaType;
import static org.mockito.Mockito.doNothing;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.hasSize;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(EmployeeController.class)
public class EmployeeControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private EmployeeRepo employeeRepo;

  @Test
  void shouldGetAllEmployees() throws Exception {
    List<Employee> employees = Arrays.asList(new Employee(1L, "John Doe", "john@example.com", "password123"),
        new Employee(2L, "Jane Doe", "jane@example.com", "password456"));
    when(employeeRepo.findAll()).thenReturn(employees);

    mockMvc.perform(get("/employees"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(2)))
        .andExpect(jsonPath("$[0].name", is("John Doe")))
        .andExpect(jsonPath("$[1].name", is("Jane Doe")));
  }

  @Test
  void shouldAddEmployee() throws Exception {
    Employee newEmployee = new Employee(null, "Jane Doe", "jane@example.com", "password456");
    Employee savedEmployee = new Employee(2L, "Jane Doe", "jane@example.com", "password456");
    when(employeeRepo.save(any(Employee.class))).thenReturn(savedEmployee);

    mockMvc.perform(post("/employees")
        .contentType(MediaType.APPLICATION_JSON)
        .content(new ObjectMapper().writeValueAsString(newEmployee)))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.name", is("Jane Doe")))
        .andExpect(jsonPath("$.email", is("jane@example.com")));
  }

  @Test
  void shouldUpdateEmployee() throws Exception {
    long employeeId = 1L;
    Employee existingEmployee = new Employee(employeeId, "John Doe", "john@example.com", "password123");
    Employee updatedEmployee = new Employee(employeeId, "John Doe Updated", "john_updated@example.com", "password1234");
    when(employeeRepo.findById(employeeId)).thenReturn(Optional.of(existingEmployee));
    when(employeeRepo.save(any(Employee.class))).thenReturn(updatedEmployee);

    mockMvc.perform(patch("/employees/{id}", employeeId)
        .contentType(MediaType.APPLICATION_JSON)
        .content(new ObjectMapper().writeValueAsString(updatedEmployee)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.name", is("John Doe Updated")))
        .andExpect(jsonPath("$.email", is("john_updated@example.com")));
  }

  @Test
  void shouldDeleteEmployeeById() throws Exception {
    long employeeId = 1L;
    Employee employee = new Employee(employeeId, "John Doe", "john@example.com", "password123");
    when(employeeRepo.findById(employeeId)).thenReturn(Optional.of(employee));
    doNothing().when(employeeRepo).delete(employee);

    mockMvc.perform(delete("/employees/{id}", employeeId))
        .andExpect(status().isOk());
  }
}