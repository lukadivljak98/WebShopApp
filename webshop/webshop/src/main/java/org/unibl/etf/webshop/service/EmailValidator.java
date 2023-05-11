package org.unibl.etf.webshop.service;

import org.springframework.stereotype.Service;

import java.util.function.Predicate;
import java.util.regex.Pattern;

@Service
public class EmailValidator implements Predicate<String> {

    private static final String PATTERN = "^(.+)@(\\S+)$";

    @Override
    public boolean test(String email) {
        return Pattern.compile(PATTERN)
                .matcher(email)
                .matches();
    }
}
