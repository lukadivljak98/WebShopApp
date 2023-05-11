package org.unibl.etf.webshop.service;

import lombok.AllArgsConstructor;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.unibl.etf.webshop.exception.UserNotEnabledException;
import org.unibl.etf.webshop.request.LogInRequest;
import org.unibl.etf.webshop.security.JwtUtils;

@Service
@AllArgsConstructor
public class LogInService {

    private final AppUserService appUserService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    public String logIn(LogInRequest request) {
        try{
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        } catch(Exception e){
            if(e instanceof DisabledException)
                return "User account is not enabled";
            return "Bad Credentials";
        }
        final UserDetails user = appUserService.loadUserByUsername(request.getEmail());
        if(!user.isEnabled()){
            return "User account is not enabled";
        }
        if(!user.isAccountNonLocked()) {
            return "Account is locked";
        }
        if(user != null){
            return jwtUtils.generateToken(user);
        }
        return "some error has occurred";
//        try {
//            Authentication authentication = authenticationManager.authenticate(
//                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
//            final UserDetails user = appUserService.loadUserByUsername(request.getEmail());
//            if (!user.isEnabled()) {
//                return "User account is not enabled";
//            }
//            if (!user.isAccountNonLocked()) {
//                return "Account is locked";
//            }
//            return jwtUtils.generateToken(user);
//        } catch (DisabledException e) {
//            return "User account is not enabled";
//        } catch (BadCredentialsException e) {
//            return "Bad Credentials";
//        }

    }

}
