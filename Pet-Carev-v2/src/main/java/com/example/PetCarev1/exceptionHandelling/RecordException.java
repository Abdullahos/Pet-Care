package com.example.PetCarev1.exceptionHandelling;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class RecordException {
    @ExceptionHandler
    public ResponseEntity<RecordExceptionResponse> handlingOwnerNotFount(RecordNotFountException ex){
        RecordExceptionResponse response = new RecordExceptionResponse();
        response.message = ex.getMessage();
        response.time = LocalDateTime.now();;
        response.status = HttpStatus.NOT_FOUND.value();
        return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
    }
}
