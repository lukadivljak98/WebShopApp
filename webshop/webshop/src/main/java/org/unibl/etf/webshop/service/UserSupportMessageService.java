package org.unibl.etf.webshop.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.unibl.etf.webshop.exception.AppUserNotFoundException;
import org.unibl.etf.webshop.model.AppUser;
import org.unibl.etf.webshop.model.UserSupportMessage;
import org.unibl.etf.webshop.repo.AppUserRepo;
import org.unibl.etf.webshop.repo.UserSupportMessageRepo;
import org.unibl.etf.webshop.request.UserSupportMessageRequest;

@Service
@Slf4j
public class UserSupportMessageService {
    private final UserSupportMessageRepo userSupportMessageRepo;
    private final AppUserRepo appUserRepo;

    @Autowired
    public UserSupportMessageService(UserSupportMessageRepo userSupportMessageRepo, AppUserRepo appUserRepo){
        this.userSupportMessageRepo = userSupportMessageRepo;
        this.appUserRepo = appUserRepo;
    }

    public UserSupportMessage send(UserSupportMessageRequest request) {
        UserSupportMessage userSupportMessage = new UserSupportMessage();
        AppUser appUser = appUserRepo.findAppUserByUsername(request.getSenderUsername()).orElseThrow(()->{
            log.error("User by username: " + request.getSenderUsername() + " not found!");
                return new AppUserNotFoundException("User by username: " + request.getSenderUsername() + " not found!");}
        );
        userSupportMessage.setMessage(request.getMessage());
        userSupportMessage.setSender(appUser);
        userSupportMessage.setRead(false);
        return userSupportMessageRepo.save(userSupportMessage);
    }
}
