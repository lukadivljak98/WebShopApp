package org.unibl.etf.webshop.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.unibl.etf.webshop.request.LogInRequest;
import org.unibl.etf.webshop.service.LogInService;

@RestController
@RequestMapping(path = "/log-in")
@AllArgsConstructor
@Slf4j
public class LogInController {

    private final LogInService logInService;

    @PostMapping
    public ResponseEntity<String> logIn(@RequestBody LogInRequest request){
        String response = logInService.logIn(request);
        log.info("User successfully logged in. User jwt: " + response);
        return ResponseEntity.ok(response);
    }
}
