package org.unibl.etf.webshop.request;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class RegistrationRequest {
    private final String name;
    private final String lastName;
    private final String mail;
    private final String username;
    private final String password;
    private final String city;
}
