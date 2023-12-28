package com.example.PetCarev1.service;

import com.example.PetCarev1.entity.Pet;
import com.example.PetCarev1.exceptionHandelling.RecordNotFountException;
import com.example.PetCarev1.repository.PetRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PetService {

    private final PetRepo petRepo;

    @Autowired
    public PetService(PetRepo petRepo) {
        this.petRepo = petRepo;
    }

    public List<Pet> findAllPets(){

        return petRepo.findAll();
    }
    public Pet findPetById(long id){
        Optional<Pet> result = petRepo.findById(id);
        Pet pet;
        if(result.isPresent()){
            pet= result.get();
        }
        else {
            throw new RecordNotFountException("Pet Not Found");
        }
        return pet;
    }

    public Pet savePet(Pet pet){
        return petRepo.save(pet);
    }

    public boolean deletePetById(long id){
        Pet pet = findPetById(id);
        petRepo.delete(pet);
        return true;
    }


}
