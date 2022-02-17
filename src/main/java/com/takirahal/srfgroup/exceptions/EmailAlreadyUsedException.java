package com.takirahal.srfgroup.exceptions;

public class EmailAlreadyUsedException extends RuntimeException{
    public EmailAlreadyUsedException(String message){super(message);}
}
