package com.example.PetCarev1.service;

import com.example.PetCarev1.entity.Employee;
import com.example.PetCarev1.exceptionHandelling.RecordNotFountException;
import com.example.PetCarev1.repository.EmployeeRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmployeeService {
    private final EmployeeRepo employeeRepo;

    public List<Employee> findAll() {
        return employeeRepo.findAll();
    }

    public Employee findEmployeeById(long id) {
        Optional<Employee> result = employeeRepo.findById(id);
        Employee employee;
        if (result.isPresent()) {
            employee = result.get();
        } else {
            throw new RecordNotFountException("Employee Not Fount");
        }

        return employee;
    }

    public Employee saveEmployee(Employee employee) {
        return employeeRepo.save(employee);
    }

    public boolean deleteEmployeeById(long id) {
        Employee employee = findEmployeeById(id);
        employeeRepo.delete(employee);
        return true;
    }

}
