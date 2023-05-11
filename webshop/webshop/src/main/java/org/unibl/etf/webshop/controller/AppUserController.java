package org.unibl.etf.webshop.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.unibl.etf.webshop.model.AppUser;
import org.unibl.etf.webshop.request.EditUserRequest;
import org.unibl.etf.webshop.service.AppUserService;

import java.util.List;

@RestController
@RequestMapping("/app-users")
@Slf4j
public class AppUserController {
    private final AppUserService appUserService;

    public AppUserController(AppUserService appUserService) {
        this.appUserService = appUserService;
    }

    @GetMapping
    public ResponseEntity<List<AppUser>> getAllAppUsers(){
        List<AppUser> appUsers = appUserService.findAllAppUsers();
        log.info("Success getting all users on url: localhost:8080/app-users. List: " + appUsers);
        return new ResponseEntity<>(appUsers, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AppUser> getAppUserById(@PathVariable("id") Long id){
        AppUser appUser = appUserService.findAppUserById(id);
        log.info("Success getting user by id on url: localhost:8080/app-users/" + id + ". User: " + appUser);
        return new ResponseEntity<>(appUser, HttpStatus.OK);
    }

    @GetMapping("/by-username/{username}")
    public ResponseEntity<AppUser> getAppUserByUsername(@PathVariable("username") String username){
        AppUser appUser = appUserService.findAppUserByUsername(username);
        log.info("Success getting user by username on url: localhost:8080/app-users/" + username + ". User: " + appUser);
        return new ResponseEntity<>(appUser, HttpStatus.OK);
    }

    @GetMapping("/by-productId/{productId}")
    public ResponseEntity<AppUser> getAppUserByProductId(@PathVariable("productId") Long productId){
        AppUser appUser = appUserService.findAppUserByProductId(productId);
        log.info("Success getting user by productId on url: localhost:8080/app-users/" + productId + ". User: " + appUser);
        return new ResponseEntity<>(appUser, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<AppUser> addAppUser(@RequestBody AppUser appUser){
        AppUser newAppUser = appUserService.addAppUser(appUser);
        log.info("Success adding user on url: localhost:8080/app-users. User: " + newAppUser);
        return new ResponseEntity<>(newAppUser, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<AppUser> updateAppUser(@RequestBody EditUserRequest request){
        AppUser updatedAppUser = appUserService.editAppUser(request);
        if(updatedAppUser != null) {
            log.info("Success updating user on url: localhost:8080/app-users. User: " + updatedAppUser);
            return new ResponseEntity<>(updatedAppUser, HttpStatus.OK);
        }
        else return new ResponseEntity<>(null, HttpStatus.CONFLICT);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAppUser(@PathVariable("id") Long id){
        appUserService.deleteAppUser(id);
        log.info("Success deleting the user on url: localhost:8080/app-users/" + id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
