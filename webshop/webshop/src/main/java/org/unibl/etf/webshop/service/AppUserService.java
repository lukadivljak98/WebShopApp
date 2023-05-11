package org.unibl.etf.webshop.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.unibl.etf.webshop.exception.AppUserNotFoundException;
import org.unibl.etf.webshop.exception.ProductNotFoundException;
import org.unibl.etf.webshop.exception.UserNotEnabledException;
import org.unibl.etf.webshop.model.AppUser;
import org.unibl.etf.webshop.model.Product;
import org.unibl.etf.webshop.repo.AppUserRepo;
import org.unibl.etf.webshop.repo.ProductRepo;
import org.unibl.etf.webshop.request.EditUserRequest;
import org.unibl.etf.webshop.security.ConfirmationToken;

import javax.swing.text.html.Option;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class AppUserService implements UserDetailsService {
    private final AppUserRepo appUserRepo;
    private final ProductRepo productRepo;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ConfirmationTokenService confirmationTokenService;

    @Autowired
    public AppUserService(AppUserRepo appUserRepo, BCryptPasswordEncoder bCryptPasswordEncoder,
                          ConfirmationTokenService confirmationTokenService, ProductRepo productRepo) {
        this.appUserRepo = appUserRepo;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.confirmationTokenService = confirmationTokenService;
        this.productRepo = productRepo;
    }

    public AppUser addAppUser(AppUser appUser){
        return appUserRepo.save(appUser);
    }

    public List<AppUser> findAllAppUsers(){
        return appUserRepo.findAll();
    }

    public AppUser updateAppUser(AppUser appUser){
        return appUserRepo.save(appUser);
    }

    public AppUser findAppUserById(Long id){
        return appUserRepo.findAppUserById(id)
                .orElseThrow(() -> {
                    log.error("AppUser by id: " + id + " was not found");
                    return new AppUserNotFoundException("AppUser by id: " + id + " was not found");
                });
    }

    public void deleteAppUser(Long id){
        appUserRepo.deleteAppUserById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String email) {
        AppUser appUser =  appUserRepo.findAppUserByMail(email)
                .orElseThrow(() ->{
                log.error("AppUser with email " + email + " not found");
                return new UsernameNotFoundException("AppUser with email " + email + " not found");});
        if(!appUser.isEnabled()){
            throw new DisabledException("User account is not enabled");
        }
        return appUser;
    }

    public String signUpUser(AppUser appUser){
        boolean userExists = appUserRepo.findAppUserByMail(appUser.getMail()).isPresent();
        if(userExists){
            log.error("email already taken. AppUser: " + appUser);
            throw new IllegalStateException("email already taken");
        }
        String encodedPassword = bCryptPasswordEncoder.encode(appUser.getPassword());
        appUser.setPasswordHash(encodedPassword);
        appUserRepo.save(appUser);

        String token = ""+((int)(Math.random()*9000)+1000);
        ConfirmationToken confirmationToken = new ConfirmationToken(token, LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15), appUser);
        confirmationTokenService.saveConfirmationToken(confirmationToken);

        return token;
    }

    public int enableAppUser(String mail) {
        return appUserRepo.enableAppUser(mail);
    }

    public AppUser findAppUserByUsername(String username) {
        return appUserRepo.findAppUserByUsername(username).orElseThrow(() ->{
            log.error("AppUser by username: " + username + " was not found");
               return new AppUserNotFoundException("AppUser by username: " + username + " was not found");});
    }

    public AppUser editAppUser(EditUserRequest request) {
        AppUser appUser = findAppUserByUsername(request.getUsername());
        if(!checkEmail(request.getEmail())){
            log.warn("Email:" + request.getEmail() + " is already taken");
            return null;
        }
        appUser.setName(request.getFirstName());
        appUser.setLastname(request.getLastName());
        appUser.setCity(request.getCity());
        appUser.setUsername(request.getUsername());
        appUser.setPasswordHash(bCryptPasswordEncoder.encode(request.getPassword()));
        appUser.setMail(request.getEmail());
        return appUserRepo.save(appUser);
    }

    public AppUser findAppUserByProductId(Long productId) {
        Product product = productRepo.findProductById(productId).orElseThrow(()->{
            log.error("Product not found. ProductId: " + productId);
                return new ProductNotFoundException("Product not found");});
        return appUserRepo.findAppUserById(product.getSeller().getId()).orElseThrow(()->{
            log.error("User not found. UserId: " + product.getSeller().getId());
                return new AppUserNotFoundException("User not found");});

    }

    public boolean checkUsername(String username){
        Optional<AppUser> user = appUserRepo.findAppUserByUsername(username);
        return user.isEmpty();
    }

    public boolean checkEmail(String email){
        Optional<AppUser> user = appUserRepo.findAppUserByMail(email);
        return user.isEmpty();
    }
}
