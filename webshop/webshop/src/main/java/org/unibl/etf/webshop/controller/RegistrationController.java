package org.unibl.etf.webshop.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.unibl.etf.webshop.request.RegistrationRequest;
import org.unibl.etf.webshop.service.RegistrationService;

@RestController
@RequestMapping(path = "/registration")
@AllArgsConstructor
@Slf4j
public class RegistrationController {
    private final RegistrationService registrationService;

    @PostMapping
    public String register(@RequestBody RegistrationRequest request){
        return registrationService.register(request);
    }

    @GetMapping(path = "/confirm")
    public String confirm(@RequestParam("token") String token){
        log.info("Registration successfully confirmed. Token: " + token);
        return registrationService.confirmToken(token);
    }
}
