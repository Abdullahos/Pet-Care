package com.PetCare.service;


import com.PetCare.entity.Employee;
import com.PetCare.exceptionHandelling.RecordNotFountException;
import com.PetCare.repository.EmployeeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    private final EmployeeRepo employeeRepo;

    @Autowired
    public EmployeeService(EmployeeRepo employeeRepo) {
        this.employeeRepo = employeeRepo;
    }
    public List<Employee> findAll(){
        return  employeeRepo.findAll();
    }

    public Employee findEmployeeById(long id){
        Optional<Employee> result = employeeRepo.findById(id);
        Employee employee;
        if(result.isPresent()){
            employee = result.get();
        }else {
            throw new RecordNotFountException("Employee Not Fount");
        }

        return employee;
    }

    public Employee saveEmployee(Employee employee){
        return employeeRepo.save(employee);
    }
    public boolean deleteEmployeeById(long id){
        Employee employee = findEmployeeById(id);
        employeeRepo.delete(employee);
        return true;
    }

}
