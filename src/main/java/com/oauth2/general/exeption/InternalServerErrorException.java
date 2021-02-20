package com.oauth2.general.exeption;

public class InternalServerErrorException extends RuntimeException{
    private static final long serialVersionUID=1L;

    public InternalServerErrorException(String message){
        super(message);
    }
}
