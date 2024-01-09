package com.example.PetCarev1.service;

import com.example.PetCarev1.dto.RequestNewService;
import com.example.PetCarev1.entity.Employee;
import com.example.PetCarev1.entity.Pet;
import com.example.PetCarev1.entity.ServiceRequest;
import com.example.PetCarev1.entity.Skill;
import com.example.PetCarev1.exceptionHandelling.RecordNotFountException;
import com.example.PetCarev1.repository.EmployeeRepo;
import com.example.PetCarev1.repository.PetRepo;
import com.example.PetCarev1.repository.ServiceRequestRepo;
import com.example.PetCarev1.repository.SkillRepo;
import jakarta.persistence.OptimisticLockException;
import lombok.RequiredArgsConstructor;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ServiceRequestService {
    private final ServiceRequestRepo serviceRequestRepo;
    private final SkillRepo skillRepo;
    private final PetRepo petRepo;
    private final EmployeeRepo employeeRepo;

    public ServiceRequest findById(Long id) {
        Optional<ServiceRequest> result = serviceRequestRepo.findById(id);
        ServiceRequest serviceRequest;
        if (result.isPresent()) {
            serviceRequest = result.get();
        } else {
            throw new RecordNotFountException("not found");
        }
        return serviceRequest;
    }

    public List<ServiceRequest> findAll() {
        return serviceRequestRepo.findAll();
    }

    public boolean deleteById(Long id) {
        ServiceRequest serviceRequest = this.findById(id);
        serviceRequestRepo.delete(serviceRequest);
        return true;

    }

    @Transactional
    @Retryable(retryFor = OptimisticLockException.class)
    public ServiceRequest create(RequestNewService request) {
        ServiceRequest serviceRequest = new ServiceRequest();
        serviceRequest.setPetId(request.getPetId());
        serviceRequest.setDueDate(request.getDueDate());

        List<Skill> skillList = skillRepo.findAllById(request.getSkillsIds());
        if (skillList.isEmpty()) {
            serviceRequest = null; // it will be deleted by the garbage collector
            throw new RuntimeException("we can't serve this request, sorry!!!!");
        }
        serviceRequest.setSkills(skillList);
        LocalDateTime actualServiceRequestEndDate = getActualServiceRequestEndDate(request, skillList, serviceRequest);

        //Avoid getting unavailable employees from the database
        Optional<List<Employee>> optionalAvailableEmployee = employeeRepo.findByEndTimeIsNullOrEndTimeIsBefore(request.getDueDate());
        if (optionalAvailableEmployee.isEmpty()) {
            serviceRequest = null; // it will be deleted by the garbage collector
            throw new RuntimeException("No Current Available Employees");
        }

        //get employees with fewer skills first
        List<Employee> availableEmployees = optionalAvailableEmployee.get();
        availableEmployees.sort(Comparator.comparingInt(a -> a.getSkills().size()));

        Pet pet = petRepo.findById(request.getPetId()).get();
        List<Employee> eligibleEmployees = new ArrayList<>();
        for (Skill skill : skillList) {
            Optional<Employee> optionalEmployee = availableEmployees
                    .stream()
                    .filter(employee -> employee.getSkills().contains(skill))
                    .findFirst();

            if (optionalEmployee.isEmpty()) {
                serviceRequest = null; // it will be deleted by the garbage collector
                throw new RuntimeException("we can't serve this request, sorry!!!!");
            }

            Employee eligibleEmployee = optionalEmployee.get();
            setEmployeeEndDate(request, skill, eligibleEmployee);
            eligibleEmployees.add(eligibleEmployee);
        }
        if (pet.getEndTime() == null || pet.getEndTime().isBefore(LocalDateTime.now())) {
            pet.setEndTime(actualServiceRequestEndDate);
        }
        employeeRepo.saveAll(eligibleEmployees);
        serviceRequest.setEmployees(eligibleEmployees);
        return serviceRequestRepo.save(serviceRequest);
    }

    @Recover
    void recover(OptimisticLockException e) {
        throw new RuntimeException("Sorry we 're getting lot of requests now :(");
    }

    private LocalDateTime getActualServiceRequestEndDate(RequestNewService request, List<Skill> skillList, ServiceRequest serviceRequest) {
        int totalServiceTime = skillList.stream()
                .mapToInt(Skill::getDuration)
                .sum();
        LocalDateTime actualServiceEndDate = LocalDateTime.now().plusMinutes(totalServiceTime);
        if (request.getDueDate().isBefore(actualServiceEndDate)) {
            serviceRequest = null; // it will be deleted by the garbage collector
            throw new RuntimeException(String.format("We are Sorry to inform you that your we need much time to serve all your %s requests", request.getSkillsIds().size()));
        }
        return actualServiceEndDate;
    }

    private static void setEmployeeEndDate(RequestNewService request, Skill skill, Employee eligibleEmployee) {
        if (eligibleEmployee.getEndTime() == null || eligibleEmployee.getEndTime().isBefore(LocalDateTime.now())) {
            eligibleEmployee.setEndTime(LocalDateTime.now());
        }
        if ((eligibleEmployee.getEndTime().plusMinutes(skill.getDuration()).isBefore(request.getDueDate()))
        ) {
            eligibleEmployee.setEndTime(eligibleEmployee.getEndTime().plusMinutes(skill.getDuration()));
        }
    }

    public List<Employee> findAllEmployeesByServiceRequestId(long id) {
        ServiceRequest serviceRequest = findById(id);
        return serviceRequest.getEmployees();
    }

    public List<Skill> findAllSkillsByServiceRequestId(long id) {
        ServiceRequest serviceRequest = findById(id);
        return serviceRequest.getSkills();
    }

}
