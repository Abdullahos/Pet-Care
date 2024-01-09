package com.example.PetCarev1.service;

import com.example.PetCarev1.entity.Owner;
import com.example.PetCarev1.entity.Pet;
import com.example.PetCarev1.exceptionHandelling.RecordNotFountException;
import com.example.PetCarev1.repository.OwnerRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OwnerService {
    private final OwnerRepo ownerRepo;

    public List<Owner> findAll() {
        return ownerRepo.findAll();
    }

    public Owner findById(long id) {
        Optional<Owner> result = ownerRepo.findById(id);
        Owner owner;
        if (result.isPresent()) {
            owner = result.get();
        } else {
            throw new RecordNotFountException("not found");
        }
        return owner;
    }

    public Boolean deleteOwner(long id) {
        Owner result = this.findById(id);
        ownerRepo.delete(result);
        return true;
    }

    public Owner saveOwner(Owner owner) {
        return ownerRepo.save(owner);
    }

    public List<Pet> findAllPetsUsingOwnerId(long id) {
        Owner owner = findById(id);
        return owner.getPetList();
    }
}
