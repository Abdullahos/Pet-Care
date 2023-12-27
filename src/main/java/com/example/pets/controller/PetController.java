package com.example.pets.controller;

import com.example.pets.model.Pet;
import com.example.pets.repo.PetRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PetController {

  @Autowired
  private PetRepo petRepo;

  @GetMapping("/pets")
  public ResponseEntity<List<Pet>> getAllPets() {
    try {
      List<Pet> pets = petRepo.findAll();
      return ResponseEntity.ok(pets);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }

  @GetMapping("/pets/{id}")
  public ResponseEntity<Pet> getPetById(@PathVariable Long id) {
    try {
      Pet pet = petRepo.findById(id)
          .orElseThrow(() -> new Exception("Pet not found"));
      return ResponseEntity.ok(pet);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
  }

  @PostMapping("/pets")
  public ResponseEntity<Pet> addPet(@RequestBody Pet pet) {
    try {
      Pet savedPet = petRepo.save(pet);
      return ResponseEntity.status(HttpStatus.CREATED).body(savedPet);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
  }

  @PatchMapping("/pets/{id}")
  public ResponseEntity<?> updatePet(@PathVariable Long id, @RequestBody Pet petDetails) {
    try {
      Pet pet = petRepo.findById(id)
          .orElseThrow(() -> new Exception("Pet not found"));
      if (petDetails.getOwner() != null) {
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body("Changing the owner of a pet is not allowed.");
      }
      if (petDetails.getName() != null) {
        pet.setName(petDetails.getName());
      }
      if (petDetails.getSpecies() != null) {
        pet.setSpecies(petDetails.getSpecies());
      }

      Pet updatedPet = petRepo.save(pet);
      return ResponseEntity.ok(updatedPet);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }
  }

  @DeleteMapping("/pets/{id}")
  public ResponseEntity<Void> deletePetById(@PathVariable Long id) {
    try {
      Pet pet = petRepo.findById(id)
          .orElseThrow(() -> new Exception("Pet not found"));
      petRepo.delete(pet);
      return ResponseEntity.ok().build();
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
  }
}
// {
//   "name": "Charlie",
//   "species": "Cat",
//   "owner": {
//     "id": 1
//   }
// }