package com.PetCare.exceptionHandelling;

public class RecordNotFountException extends RuntimeException{
    public RecordNotFountException(String message) {
        super(message);
    }
}
