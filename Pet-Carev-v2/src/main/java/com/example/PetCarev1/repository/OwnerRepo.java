package com.example.PetCarev1.repository;


import com.example.PetCarev1.entity.Owner;
import com.example.PetCarev1.entity.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OwnerRepo extends JpaRepository<Owner, Long> {
}
