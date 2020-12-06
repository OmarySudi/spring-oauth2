package com.carpool.auth.exeption;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestExceptioHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorMessage> handleEntityNofFound(EntityNotFoundException ex){
        ErrorMessage error = new ErrorMessage(HttpStatus.NOT_FOUND,"Entity not Found",ex.getMessage());

        return new ResponseEntity<>(error,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InternalServerErrorException.class)
    public ResponseEntity<ErrorMessage> globalExceptionHandler(InternalServerErrorException ex){
        ErrorMessage error = new ErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR,"There is internal server error",ex.getMessage());

        return new ResponseEntity<>(error,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(EntityExistException.class)
    public ResponseEntity<ErrorMessage> handleEntityExistException(EntityExistException ex){

        ErrorMessage error = new ErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR,"Entity Exists",ex.getMessage());

        return new ResponseEntity<>(error,HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
