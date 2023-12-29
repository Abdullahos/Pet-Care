package com.example.pets.controller;

import com.example.pets.model.Employee;
import com.example.pets.model.Timetable;
import com.example.pets.repo.EmployeeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PatchMapping;

import java.util.List;
import com.example.pets.model.Skill;
import com.example.pets.repo.SkillRepo;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
public class EmployeeController {

  @Autowired
  private EmployeeRepo employeeRepo;

  @Autowired
  private SkillRepo skillRepo;

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
  public ResponseEntity<Employee> addEmployee(@RequestBody Employee employee,
      @RequestParam(required = false) Set<Long> skillIds) {
    try {
      if (skillIds != null && !skillIds.isEmpty()) {
        Set<Skill> skills = skillIds.stream()
            .map(id -> skillRepo.findById(id).orElseThrow(() -> new RuntimeException("Skill not found with id: " + id)))
            .collect(Collectors.toSet());
        employee.setSkills(skills);
      }
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

  // return timetable of employee
  @GetMapping("/employees/{id}/timetable")
  public ResponseEntity<Timetable> getEmployeeTimetable(@PathVariable Long id) {
    try {
      Employee employee = employeeRepo.findById(id)
          .orElseThrow(() -> new Exception("Employee not found"));
      Timetable timetable = employee.getTimetable();
      return ResponseEntity.ok(timetable);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }
  }

  // return the number of hours available for a specific day of the week
  @GetMapping("/employees/{id}/timetable/{day}")
  public ResponseEntity<Integer> getEmployeeHoursForDay(@PathVariable Long id, @PathVariable String day) {
    try {
      Employee employee = employeeRepo.findById(id)
          .orElseThrow(() -> new Exception("Employee not found"));
      Timetable timetable = employee.getTimetable();
      int hours;
      switch (day.toLowerCase()) {
        case "sunday":
          hours = timetable.getSunday();
          break;
        case "monday":
          hours = timetable.getMonday();
          break;
        case "tuesday":
          hours = timetable.getTuesday();
          break;
        case "wednesday":
          hours = timetable.getWednesday();
          break;
        case "thursday":
          hours = timetable.getThursday();
          break;
        default:
          throw new IllegalArgumentException("Invalid day provided");
      }
      return ResponseEntity.ok(hours);
    } catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest().body(null);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }
  }

  @PatchMapping("/{id}/timetable/decrease")
  public ResponseEntity<?> decreaseTimetable(@PathVariable Long id, @RequestParam String day, @RequestParam int value) {
    try {
      Employee employee = employeeRepo.findById(id)
          .orElseThrow(() -> new Exception("Employee not found"));

      Timetable timetable = employee.getTimetable();
      boolean enoughTime = true;

      switch (day.toLowerCase()) {
        case "sunday":
          enoughTime = timetable.getSunday() >= value;
          if (enoughTime)
            timetable.setSunday(timetable.getSunday() - value);
          break;
        case "monday":
          enoughTime = timetable.getMonday() >= value;
          if (enoughTime)
            timetable.setMonday(timetable.getMonday() - value);
          break;
        case "tuesday":
          enoughTime = timetable.getTuesday() >= value;
          if (enoughTime)
            timetable.setTuesday(timetable.getTuesday() - value);
          break;
        case "wednesday":
          enoughTime = timetable.getWednesday() >= value;
          if (enoughTime)
            timetable.setWednesday(timetable.getWednesday() - value);
          break;
        case "thursday":
          enoughTime = timetable.getThursday() >= value;
          if (enoughTime)
            timetable.setThursday(timetable.getThursday() - value);
          break;
        default:
          throw new IllegalArgumentException("Invalid day provided");
      }

      if (!enoughTime) {
        return ResponseEntity.badRequest().body("Not enough time available to decrease.");
      }

      employeeRepo.save(employee);
      return ResponseEntity.ok(employee);
    } catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body("An error occurred while processing the request.");
    }
  }

}
