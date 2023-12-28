package com.example.PetCarev1.repository;


import com.example.PetCarev1.entity.ServiceRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceRequestRepo extends JpaRepository<ServiceRequest, Long> {
}
