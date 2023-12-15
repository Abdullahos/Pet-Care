package com.PetCare.service;


import com.PetCare.entity.Owner;
import com.PetCare.entity.Pet;
import com.PetCare.exceptionHandelling.RecordNotFountException;
import com.PetCare.repository.OwnerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OwnerService {
    private final OwnerRepo ownerRepo;


    @Autowired
    public OwnerService(OwnerRepo ownerRepo) {
        this.ownerRepo = ownerRepo;
    }

    public List<Owner> findAll(){
        return  ownerRepo.findAll();
    }
    public Owner findById(long id){
        Optional<Owner> result = ownerRepo.findById(id);
        Owner owner;
        if(result.isPresent()){
            owner = result.get();
        }
        else {
            throw new RecordNotFountException("not found");
        }
        return owner;
    }

    public Boolean deleteOwner(long id){
        Optional<Owner> result = ownerRepo.findById(id);
        Owner owner;
        if(result.isPresent()){
            owner = result.get();
        }
        else {
            throw new RecordNotFountException("not found");
        }
        ownerRepo.delete(owner);
        return true;
    }

    public Owner saveOwner(Owner owner){
        return ownerRepo.save(owner);
    }
    public List<Pet> findAllPetsUsingOwnerId(long id){
        Owner owner = findById(id);
        return owner.getPetList();
    }
}
