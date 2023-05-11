package org.unibl.etf.webshop.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.unibl.etf.webshop.model.UserSupportMessage;
import org.unibl.etf.webshop.request.UserSupportMessageRequest;
import org.unibl.etf.webshop.service.UserSupportMessageService;

@RestController
@RequestMapping("/user-support-messages")
@Slf4j
public class UserSupportMessageController {

    private final UserSupportMessageService userSupportMessageService;

    @Autowired
    public UserSupportMessageController(UserSupportMessageService userSupportMessageService){
        this.userSupportMessageService = userSupportMessageService;
    }

    @PostMapping
    public ResponseEntity<UserSupportMessage> send(@RequestBody UserSupportMessageRequest request){
        UserSupportMessage userSupportMessage = userSupportMessageService.send(request);
        log.info("User support message successfully sent: " + userSupportMessage);
        return new ResponseEntity<>(userSupportMessage, HttpStatus.CREATED );
    }
}
