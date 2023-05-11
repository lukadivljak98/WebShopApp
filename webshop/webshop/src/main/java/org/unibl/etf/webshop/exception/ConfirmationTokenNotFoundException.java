package org.unibl.etf.webshop.exception;

public class ConfirmationTokenNotFoundException extends RuntimeException{
    public ConfirmationTokenNotFoundException(String message){
        super(message);
    }
}
