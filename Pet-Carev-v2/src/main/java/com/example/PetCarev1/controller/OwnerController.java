package com.example.PetCarev1.controller;

import com.example.PetCarev1.entity.Owner;
import com.example.PetCarev1.entity.Pet;
import com.example.PetCarev1.repository.OwnerRepo;
import com.example.PetCarev1.service.OwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/owners")
public class OwnerController {

    private OwnerService ownerService;

    @Autowired
    public OwnerController(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    @GetMapping
    public ResponseEntity<List<Owner>> showOwners(){
        return new ResponseEntity<>(ownerService.findAll(), HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Owner> showOwnerById(@PathVariable long id){
        return new ResponseEntity<>(ownerService.findById(id),HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOwnerById(@PathVariable long id){
        ownerService.deleteOwner(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping
    public ResponseEntity<Owner> saveOwner(@RequestBody Owner owner){

        return new ResponseEntity<>(ownerService.saveOwner(owner),HttpStatus.CREATED);
    }
    @PutMapping
    public ResponseEntity<Owner> updateOwner(@RequestBody Owner owner){

        return new ResponseEntity<>(ownerService.saveOwner(owner),HttpStatus.OK);
    }

    @GetMapping("/{id}/petlist")
    public ResponseEntity<List<Pet>> findAllPEtsUsingPetId(@PathVariable long id){

        return new ResponseEntity<>(ownerService.findAllPetsUsingOwnerId(id),HttpStatus.OK);
    }


}
