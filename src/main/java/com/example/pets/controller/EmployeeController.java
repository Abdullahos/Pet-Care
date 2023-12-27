package com.example.pets.controller;

import com.example.pets.model.Employee;
import com.example.pets.repo.EmployeeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PatchMapping;

import java.util.List;

@RestController
public class EmployeeController {

  @Autowired
  private EmployeeRepo employeeRepo;

  @GetMapping("/employees")
  public ResponseEntity<List<Employee>> getAllEmployees() {
    try {
      List<Employee> employees = employeeRepo.findAll();
      return ResponseEntity.ok(employees);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(null);
    }
  }

  @GetMapping("/employees/{id}")
  public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
    try {
      Employee employee = employeeRepo.findById(id)
          .orElseThrow(() -> new Exception("Employee not found"));
      return ResponseEntity.ok(employee);
    } catch (Exception e) {
      return ResponseEntity.notFound().build();
    }
  }

  @PostMapping("/employees")
  public ResponseEntity<Employee> addEmployee(@RequestBody Employee employee) {
    try {
      Employee savedEmployee = employeeRepo.save(employee);
      return ResponseEntity.status(HttpStatus.CREATED).body(savedEmployee);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(null);
    }
  }

  @PatchMapping("/employees/{id}")
  public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @RequestBody Employee employeeDetails) {
    try {
      Employee employee = employeeRepo.findById(id)
          .orElseThrow(() -> new Exception("Employee not found"));
      if (employeeDetails.getName() != null) {
        employee.setName(employeeDetails.getName());
      }
      if (employeeDetails.getEmail() != null) {
        employee.setEmail(employeeDetails.getEmail());
      }
      if (employeeDetails.getPassword() != null) {
        employee.setPassword(employeeDetails.getPassword());
      }
      final Employee updatedEmployee = employeeRepo.save(employee);
      return ResponseEntity.ok(updatedEmployee);
    } catch (Exception e) {
      return ResponseEntity.notFound().build();
    }
  }

  @DeleteMapping("/employees/{id}")
  public ResponseEntity<Void> deleteEmployeeById(@PathVariable Long id) {
    try {
      Employee employee = employeeRepo.findById(id)
          .orElseThrow(() -> new Exception("Employee not found"));
      employeeRepo.delete(employee);
      return ResponseEntity.ok().build();
    } catch (Exception e) {
      return ResponseEntity.notFound().build();
    }
  }

}
