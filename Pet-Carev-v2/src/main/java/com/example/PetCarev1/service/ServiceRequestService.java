package com.example.PetCarev1.service;

import com.example.PetCarev1.dto.RequestNewService;
import com.example.PetCarev1.entity.*;
import com.example.PetCarev1.exceptionHandelling.RecordNotFountException;
import com.example.PetCarev1.repository.PetRepo;
import com.example.PetCarev1.repository.ServiceRequestRepo;
import com.example.PetCarev1.repository.SkillRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ServiceRequestService {

    @Autowired
    private ServiceRequestRepo serviceRequestRepo;
    @Autowired
    private SkillRepo skillRepo;
    @Autowired
    private PetRepo petRepo;

    public ServiceRequest findById(Long id){
        Optional<ServiceRequest> result = serviceRequestRepo.findById(id);
        ServiceRequest serviceRequest;
        if(result.isPresent()){
            serviceRequest = result.get();
        }
        else {
            throw new RecordNotFountException("not found");
        }
        return serviceRequest;
    }

    public List<ServiceRequest> findAll(){
        return serviceRequestRepo.findAll();
    }

    public boolean deleteById(Long id){
        ServiceRequest serviceRequest = this.findById(id);
        serviceRequestRepo.delete(serviceRequest);
        return true;

    }


    public ServiceRequest save(RequestNewService request){
        ServiceRequest serviceRequest = new ServiceRequest();
        serviceRequest.setPetId(request.petId);
        serviceRequest.setDueDate(request.dueDate);

        List<Skill> skillList = skillRepo.findAllById(request.skillsIds);
        System.out.println(skillList.size());
        if (skillList.isEmpty()){
            serviceRequest = null; // it will be deleted by the garbage collector
            throw new RuntimeException("we can't serve this request, sorry!!!!");
        }
        List<Employee> availableEmployees = new ArrayList<>();
        serviceRequest.setSkills(skillList);

        Long numberOfEmployees = 0l;
        List<Employee> employees = new ArrayList<>();
        for (Skill skill: skillList){
            availableEmployees = skill.getEmployees();
            if (availableEmployees.isEmpty()){
                serviceRequest = null; // it will be deleted by the garbage collector
                throw new RuntimeException("we can't serve this request, sorry!!!!");
            }
            Pet pet = petRepo.findById(request.petId).get();

            for (Employee employee: availableEmployees){
                if (employee.getEndTime() == null || employee.getEndTime().isBefore(LocalDateTime.now())){
                    employee.setEndTime(LocalDateTime.now());
                }
                if (pet.getEndTime() == null || pet.getEndTime().isBefore(LocalDateTime.now())){
                    pet.setEndTime(LocalDateTime.now());
                }

                if ((employee.getEndTime().plusMinutes(skill.getDuration()).isBefore(request.dueDate))
                    && (pet.getEndTime().plusMinutes(skill.getDuration()).isBefore(request.dueDate))
                ){

                    employee.setEndTime(employee.getEndTime().plusMinutes(skill.getDuration()));
                    pet.setEndTime(pet.getEndTime().plusMinutes(skill.getDuration()));
                    employees.add(employee);
                    break;
                }
            }

            if (employees.size() == numberOfEmployees + 1){
                numberOfEmployees++;
            }
            else {
                // we do not  have any free employee for one task, so we will cancel this request
                serviceRequest = null; // it will be deleted by the garbage collector
                throw new RuntimeException("we can't serve this request, sorry!!!!");
            }
        }

        serviceRequest.setEmployees(employees);
       return serviceRequestRepo.save(serviceRequest);

    }

    public List<Employee> findAllEmployeesByServiceRequestId(long id){
        ServiceRequest serviceRequest = findById(id);
        return serviceRequest.getEmployees();
    }
    public List<Skill> findAllSkillsByServiceRequestId(long id){
        ServiceRequest serviceRequest = findById(id);
        return serviceRequest.getSkills();
    }




}
