package com.example.PetCarev1.exceptionHandelling;

public class RecordNotFountException extends RuntimeException{
    public RecordNotFountException(String message) {
        super(message);
    }
}
