package com.example.pets.controller;
import com.example.pets.model.Owner;
import com.example.pets.repo.OwnerRepo;
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
public class OwnerControllerTest {

    @Mock
    private OwnerRepo ownerRepo;

    @InjectMocks
    private OwnerController ownerController;

    private Owner owner;

    @BeforeEach
    public void setUp() {
        owner = new Owner(); // Assuming Owner has a no-args constructor
        owner.setId(1L);
        owner.setName("John Doe");
        owner.setContact("123456789");
    }

    @Test
    public void getAllOwnersTest() {
        List<Owner> owners = Arrays.asList(owner);
        when(ownerRepo.findAll()).thenReturn(owners);
        ResponseEntity<List<Owner>> response = ownerController.getAllOwners();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
    }

    @Test
    public void getOwnerByIdTest() {
        when(ownerRepo.findById(1L)).thenReturn(Optional.of(owner));
        ResponseEntity<Owner> response = ownerController.getOwnerById(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(owner, response.getBody());
    }

    @Test
    public void getOwnerByIdNotFoundTest() {
        when(ownerRepo.findById(anyLong())).thenReturn(Optional.empty());
        ResponseEntity<Owner> response = ownerController.getOwnerById(1L);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void addOwnerTest() {
        when(ownerRepo.save(any(Owner.class))).thenReturn(owner);
        ResponseEntity<Owner> response = ownerController.addOwner(owner);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(owner, response.getBody());
    }

    @Test
    public void updateOwnerTest() {
        when(ownerRepo.findById(1L)).thenReturn(Optional.of(owner));
        when(ownerRepo.save(any(Owner.class))).thenReturn(owner);
        ResponseEntity<Owner> response = ownerController.updateOwner(1L, owner);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(owner, response.getBody());
    }

    @Test
    public void updateOwnerNotFoundTest() {
        when(ownerRepo.findById(anyLong())).thenReturn(Optional.empty());
        ResponseEntity<Owner> response = ownerController.updateOwner(1L, owner);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void deleteOwnerByIdTest() {
        when(ownerRepo.findById(1L)).thenReturn(Optional.of(owner));
        doNothing().when(ownerRepo).delete(owner);
        ResponseEntity<Void> response = ownerController.deleteOwnerById(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(ownerRepo, times(1)).delete(owner);
    }

    @Test
    public void deleteOwnerByIdNotFoundTest() {
        when(ownerRepo.findById(anyLong())).thenReturn(Optional.empty());
        ResponseEntity<Void> response = ownerController.deleteOwnerById(1L);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}