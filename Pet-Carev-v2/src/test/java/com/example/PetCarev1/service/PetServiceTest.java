package com.example.PetCarev1.service;

import com.example.PetCarev1.entity.Owner;
import com.example.PetCarev1.entity.Pet;
import com.example.PetCarev1.exceptionHandelling.RecordNotFountException;
import com.example.PetCarev1.repository.PetRepo;
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
@ContextConfiguration(classes = {PetService.class})
class PetServiceTest {

    @MockBean
    private PetRepo repo;
    @Autowired
    private PetService service;

    private Pet pet;
    @BeforeEach
    void setUp() {
        pet = new Pet(1l,"toto", "cat", new Owner());

    }

    @Test
    void findAllPets() {
        List<Pet> pets = Arrays.asList(new Pet(),new Pet(),new Pet());
        when(repo.findAll()).thenReturn(pets);
        assertThat(service.findAllPets()).isEqualTo(pets);
    }

    @Test
    void findPetById() {
        when(repo.findById(anyLong())).thenReturn(Optional.ofNullable(pet));
        assertThat(service.findPetById(1)).isEqualTo(pet);
    }
    @Test
    void findPetByIdDoseNotExists() {
        when(repo.findById(anyLong()))
                .thenThrow( new RecordNotFountException("Employee Not Fount"));
        assertThrows(RecordNotFountException.class,()-> service.findPetById(1));

    }

    @Test
    void savePet() {
        when(repo.save(any())).thenReturn(pet);
        assertThat(pet).isEqualTo(service.savePet(pet));

    }

}