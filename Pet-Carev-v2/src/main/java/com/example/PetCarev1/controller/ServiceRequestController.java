package com.example.PetCarev1.controller;

import com.example.PetCarev1.dto.RequestNewService;
import com.example.PetCarev1.entity.Employee;
import com.example.PetCarev1.entity.Owner;
import com.example.PetCarev1.entity.Pet;
import com.example.PetCarev1.entity.ServiceRequest;
import com.example.PetCarev1.service.ServiceRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("service-request")
public class ServiceRequestController {

    private ServiceRequestService service;

    @Autowired
    public ServiceRequestController(ServiceRequestService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<ServiceRequest>> findAllServiceRequests(){
        return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServiceRequest> findServiceRequestById(@PathVariable Long id){
        return new ResponseEntity<>(service.findById(id), HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteServiceRequeById(@PathVariable long id){
        service.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping
    public ResponseEntity<ServiceRequest> saveOwner(@RequestBody RequestNewService newService){

        return new ResponseEntity<>(service.save(newService),HttpStatus.CREATED);
    }

    @GetMapping("/{id}/employees")
    public ResponseEntity<List<Employee>> findAllEmployeesByServiceRequestId(@PathVariable long id){

        return new ResponseEntity<>(service.findAllEmployeesByServiceRequestId(id),HttpStatus.OK);
    }
    @GetMapping("/{id}/skills")
    public ResponseEntity<List<Employee>> findAllSkillsByServiceRequestId(@PathVariable long id){

        return new ResponseEntity<>(service.findAllEmployeesByServiceRequestId(id),HttpStatus.OK);
    }




}
