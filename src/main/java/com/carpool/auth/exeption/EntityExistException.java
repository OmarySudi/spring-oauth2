package com.carpool.auth.exeption;

public class EntityExistException extends Exception{

    private static final long serialVersionUID=1L;

    public EntityExistException(String message){super(message);}
}
