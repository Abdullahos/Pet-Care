package com.example.PetCarev1.repository;


import com.example.PetCarev1.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepo extends JpaRepository<Employee, Long> {

    Optional<List<Employee>> findByEndTimeIsNullOrEndTimeIsBefore(LocalDateTime dueDate);
}
