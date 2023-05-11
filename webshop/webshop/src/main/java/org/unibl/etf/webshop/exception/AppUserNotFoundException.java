package org.unibl.etf.webshop.exception;

public class AppUserNotFoundException extends RuntimeException{

    public AppUserNotFoundException(String message){
        super(message);
    }
}
