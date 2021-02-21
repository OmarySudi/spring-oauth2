package com.oauth2.general.exeption;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class RestExceptioHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ErrorMessage error = new ErrorMessage(HttpStatus.BAD_REQUEST, "Validation Error", ex.getBindingResult().toString());

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

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
