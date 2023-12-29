package com.example.pets.controller;

import com.example.pets.model.Owner;
// import com.example.pets.model.Pet;
import com.example.pets.repo.OwnerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class OwnerController {

  @Autowired
  private OwnerRepo ownerRepo;

  @GetMapping("/owners")
  public ResponseEntity<List<Owner>> getAllOwners() {
    try {
      List<Owner> owners = ownerRepo.findAll();
      return ResponseEntity.ok(owners);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }

  @GetMapping("/owners/{id}")
  public ResponseEntity<Owner> getOwnerById(@PathVariable Long id) {
    try {
      Owner owner = ownerRepo.findById(id)
          .orElseThrow(() -> new Exception("Owner not found"));
      return ResponseEntity.ok(owner);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
  }

  @PostMapping("/owners")
  public ResponseEntity<Owner> addOwner(@RequestBody Owner owner) {
    try {
      Owner savedOwner = ownerRepo.save(owner);
      return ResponseEntity.status(HttpStatus.CREATED).body(savedOwner);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
  }

  @PatchMapping("/owners/{id}")
  public ResponseEntity<Owner> updateOwner(@PathVariable Long id, @RequestBody Owner ownerDetails) {
    try {
      Owner existingOwner = ownerRepo.findById(id)
          .orElseThrow(() -> new Exception("Owner not found"));

      boolean needUpdate = false;

      if (ownerDetails.getName() != null && !ownerDetails.getName().equals(existingOwner.getName())) {
        existingOwner.setName(ownerDetails.getName());
        needUpdate = true;
      }
      if (ownerDetails.getContact() != null && !ownerDetails.getContact().equals(existingOwner.getContact())) {
        existingOwner.setContact(ownerDetails.getContact());
        needUpdate = true;
      }
      if (needUpdate) {
        Owner updatedOwner = ownerRepo.save(existingOwner);
        return ResponseEntity.ok(updatedOwner);
      } else {
        return ResponseEntity.ok(existingOwner);
      }
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
  }

  @DeleteMapping("/owners/{id}")
  public ResponseEntity<Void> deleteOwnerById(@PathVariable Long id) {
    try {
      Owner owner = ownerRepo.findById(id)
          .orElseThrow(() -> new Exception("Owner not found"));
      ownerRepo.delete(owner);
      return ResponseEntity.ok().build();
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
  }

}
