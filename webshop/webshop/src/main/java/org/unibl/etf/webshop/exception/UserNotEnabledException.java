package org.unibl.etf.webshop.exception;

public class UserNotEnabledException extends RuntimeException{
    public UserNotEnabledException(String message){
        super(message);
    }
}
