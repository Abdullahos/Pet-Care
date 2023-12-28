package com.example.PetCarev1.service;

import com.example.PetCarev1.entity.Employee;
import com.example.PetCarev1.entity.Owner;
import com.example.PetCarev1.entity.Pet;
import com.example.PetCarev1.exceptionHandelling.RecordNotFountException;
import com.example.PetCarev1.repository.OwnerRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {OwnerService.class})
class OwnerServiceTest {
    @MockBean
    private OwnerRepo ownerRepo;

    @Autowired
    private OwnerService ownerService;

    private Owner owner;
    @BeforeEach
    void setUp() {
        owner = new Owner(1l,"ali","+2011", null);
    }

    @Test
    void findAll() {
        List<Owner> owners = new ArrayList<>();
        owners.add(new Owner());
        owners.add(new Owner());
        owners.add(new Owner());
        owners.add(new Owner());
        when(ownerRepo.findAll()).thenReturn(owners);
        assertThat(ownerService.findAll()).isEqualTo(owners);
    }
    @Test
    void findAllEmpty() {
        List<Owner> owners = null;
        when(ownerRepo.findAll()).thenReturn(owners);
        assertThat(ownerService.findAll()).isEqualTo(owners);
    }

    @Test
    void findById() {
        when(ownerRepo.findById(anyLong())).thenReturn(Optional.ofNullable(owner));
        assertThat(ownerService.findById(1)).isEqualTo(owner);
    }

    @Test
    void findByIdDoseNotExist() {
        when(ownerRepo.findById(anyLong()))
                .thenThrow( new RecordNotFountException("Employee Not Fount"));
        assertThrows(RecordNotFountException.class,()-> ownerService.findById(1));
    }


    @Test
    void saveOwner() {
        when(ownerRepo.save(any())).thenReturn(owner);
        Owner owner1 = ownerService.saveOwner(owner);
        assertThat(owner).isEqualTo(owner1);
    }

    @Test
    void findAllPetsUsingOwnerId() {
        List<Pet> pets = Arrays.asList(new Pet(), new Pet());
        Owner owner1 =
                new Owner(1l, "ali","+20144", pets);
        when(ownerRepo.findById(anyLong())).thenReturn(Optional.of(owner1));
        assertThat(ownerService.findById(1).getPetList()).isEqualTo(pets);


    }
}