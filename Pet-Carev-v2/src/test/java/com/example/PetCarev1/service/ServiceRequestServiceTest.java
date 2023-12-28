package com.example.PetCarev1.service;

import com.example.PetCarev1.dto.RequestNewService;
import com.example.PetCarev1.entity.Employee;
import com.example.PetCarev1.entity.ServiceRequest;
import com.example.PetCarev1.entity.Skill;
import com.example.PetCarev1.exceptionHandelling.RecordNotFountException;
import com.example.PetCarev1.repository.ServiceRequestRepo;
import com.example.PetCarev1.repository.SkillRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)

class ServiceRequestServiceTest {

    @Mock
    private ServiceRequestRepo serviceRequestRepo;
    @Mock
    private SkillRepo skillRepo;

    @InjectMocks
    private ServiceRequestService service;

    @Test
    void testFindById() {
        ServiceRequest expected = new ServiceRequest();
        when(serviceRequestRepo.findById(1l)).thenReturn(Optional.of(expected));
        ServiceRequest actual = service.findById(1l);
        assertThat(expected).isEqualTo(actual);

    }

    @Test
    void testNotFindById() {

        when(serviceRequestRepo.findById(1l)).thenReturn(Optional.empty());

        assertThrows(RecordNotFountException.class,()-> service.findById(1l));

    }

    @Test
    void findAll() {
        List<ServiceRequest> serviceRequests = new ArrayList<>();
        when(serviceRequestRepo.findAll()).thenReturn(serviceRequests);
        List<ServiceRequest> result = service.findAll();
        assertEquals(serviceRequests, result);

    }

    @Test
    public void testDeleteById() {
        Long requestId = 1L;
        ServiceRequest serviceRequest = new ServiceRequest();
        when(serviceRequestRepo.findById(requestId)).thenReturn(Optional.of(serviceRequest));

        boolean result = service.deleteById(requestId);

        assertTrue(result);

    }

    @Test
    public void testDeleteByIdNotFound() {
        Long requestId = 1L;
        when(serviceRequestRepo.findById(requestId)).thenReturn(Optional.empty());

        assertThrows(RecordNotFountException.class, () -> service.deleteById(requestId));
    }

    @Test
    public void testFindAllEmployeesByServiceRequestId() {
        Long requestId = 1L;
        ServiceRequest serviceRequest = new ServiceRequest();
        when(serviceRequestRepo.findById(requestId)).thenReturn(Optional.of(serviceRequest));

        List<Employee> result = service.findAllEmployeesByServiceRequestId(requestId);

        assertNotNull(result);
    }

    @Test
    public void testFindAllSkillsByServiceRequestId() {
        Long requestId = 1L;
        ServiceRequest serviceRequest = new ServiceRequest();
        when(serviceRequestRepo.findById(requestId)).thenReturn(Optional.of(serviceRequest));

        List<Skill> result = service.findAllSkillsByServiceRequestId(requestId);

        assertNotNull(result);
    }




}