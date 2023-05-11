package org.unibl.etf.webshop.security;

public interface EmailSender {
    void send(String to, String email);
}
