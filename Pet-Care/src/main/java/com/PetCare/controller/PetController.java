package com.PetCare.controller;


import com.PetCare.entity.Pet;
import com.PetCare.service.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pets")
public class PetController {

    private final PetService petService;

    @Autowired
    public PetController(PetService petService) {
        this.petService = petService;
    }

    @GetMapping
    public List<Pet> findAllPets(){
        return petService.findAllPets();
    }
    @GetMapping("/{id}")
    public Pet findPetById(@PathVariable long id){
        return petService.findPetById(id);
    }

    @PostMapping
    public Pet savePet(@RequestBody Pet pet){
        return petService.savePet(pet);
    }

    @DeleteMapping("{id}")
    public boolean deletePetById(@PathVariable long id){
        return petService.deletePetById(id);
    }

    @PutMapping
    public Pet uppdatePet(@RequestBody  Pet pet){
        return petService.savePet(pet);
    }

}
