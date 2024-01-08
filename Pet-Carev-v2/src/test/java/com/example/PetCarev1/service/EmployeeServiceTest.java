package com.example.PetCarev1.service;

import com.example.PetCarev1.entity.Employee;
import com.example.PetCarev1.exceptionHandelling.RecordNotFountException;
import com.example.PetCarev1.repository.EmployeeRepo;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {EmployeeService.class})
class EmployeeServiceTest {
    @MockBean
    private EmployeeRepo employeeRepo;
    @Autowired
    private EmployeeService underTest;

    Employee employee;

    @BeforeEach
    void setUp() {
        employee = new Employee(1l,"ali","ali@gmail.com","password");
    }

    @Test
    void findAll() {
        List<Employee> employees = new ArrayList<>() ;
        employees.add(new Employee());
        employees.add(new Employee());
        employees.add(new Employee());
        employees.add(new Employee());
        when(employeeRepo.findAll()).thenReturn(employees);
        List<Employee> actual = underTest.findAll();
        assertThat(employees).isEqualTo(actual);
    }
    @Test
    void findAllEmpty() {
        List<Employee> employees = null;
        when(employeeRepo.findAll()).thenReturn(employees);
        List<Employee> actual = underTest.findAll();
        assertThat(employees).isEqualTo(actual);
    }



    @Test
    void findEmployeeByExistsId() {

        when(employeeRepo.findById(any())).thenReturn(Optional.of(employee));
        assertThat(underTest.findEmployeeById(1)).isEqualTo(employee);


    }

    @Test
    void findEmployeeByNotExistsId() {
        when(employeeRepo.findById(any()))
                .thenThrow( new RecordNotFountException("Employee Not Fount"));
        assertThrows(RecordNotFountException.class,()-> underTest.findEmployeeById(1));


    }

    @Test
    void saveEmployee() {

//        underTest.saveEmployee(employee);
//        ArgumentCaptor<Employee> employeeArgumentCaptor =
//                ArgumentCaptor.forClass(Employee.class);
//
//        verify(employeeRepo).save(employeeArgumentCaptor.capture());
//        Employee employee1 = employeeArgumentCaptor.getValue();
//        assertThat(employee1).isEqualTo(employee);

        when(employeeRepo.save(any())).thenReturn(employee);
        Employee employee1 = underTest.saveEmployee(employee);
        assertThat(employee).isEqualTo(employee1);
    }

}