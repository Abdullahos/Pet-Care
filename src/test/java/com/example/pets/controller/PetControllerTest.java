package com.example.pets.controller;

import com.example.pets.model.Pet;
import com.example.pets.repo.PetRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class PetControllerTest {

  @Mock
  private PetRepo petRepo;

  @InjectMocks
  private PetController petController;

  private Pet pet;
  private List<Pet> petList;

  @BeforeEach
  public void setUp() {
    pet = new Pet(1L, "Charlie", "Cat", null); // Assuming Pet has a constructor
    petList = Arrays.asList(pet);
  }

  @Test
  public void getAllPetsTest() {
    when(petRepo.findAll()).thenReturn(petList);
    ResponseEntity<List<Pet>> response = petController.getAllPets();
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(1, response.getBody().size());
  }

  @Test
  public void getPetByIdTest() {
    when(petRepo.findById(1L)).thenReturn(Optional.of(pet));
    ResponseEntity<Pet> response = petController.getPetById(1L);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(pet, response.getBody());
  }

  @Test
  public void getPetByIdNotFoundTest() {
    when(petRepo.findById(anyLong())).thenReturn(Optional.empty());
    ResponseEntity<Pet> response = petController.getPetById(1L);
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
  }

  @Test
  public void addPetTest() {
    when(petRepo.save(any(Pet.class))).thenReturn(pet);
    ResponseEntity<Pet> response = petController.addPet(pet);
    assertEquals(HttpStatus.CREATED, response.getStatusCode());
    assertEquals(pet, response.getBody());
  }

  @Test
  public void updatePetTest() {
    when(petRepo.findById(1L)).thenReturn(Optional.of(pet));
    when(petRepo.save(any(Pet.class))).thenReturn(pet);
    ResponseEntity<?> response = petController.updatePet(1L, pet);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(pet, response.getBody());
  }

  @Test
  public void updatePetNotFoundTest() {
    when(petRepo.findById(anyLong())).thenReturn(Optional.empty());
    ResponseEntity<?> response = petController.updatePet(1L, pet);
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
  }

  @Test
  public void deletePetByIdTest() {
    when(petRepo.findById(1L)).thenReturn(Optional.of(pet));
    doNothing().when(petRepo).delete(pet);
    ResponseEntity<Void> response = petController.deletePetById(1L);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    verify(petRepo, times(1)).delete(pet);
  }

  @Test
  public void deletePetByIdNotFoundTest() {
    when(petRepo.findById(anyLong())).thenReturn(Optional.empty());
    ResponseEntity<Void> response = petController.deletePetById(1L);
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
  }
}