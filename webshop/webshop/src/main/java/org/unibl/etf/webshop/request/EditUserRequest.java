package org.unibl.etf.webshop.request;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class EditUserRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String city;
    private String username;
}
