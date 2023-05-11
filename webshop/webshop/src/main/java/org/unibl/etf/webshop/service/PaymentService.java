package org.unibl.etf.webshop.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.unibl.etf.webshop.exception.AppUserNotFoundException;
import org.unibl.etf.webshop.exception.ProductNotFoundException;
import org.unibl.etf.webshop.model.AppUser;
import org.unibl.etf.webshop.model.Product;
import org.unibl.etf.webshop.repo.AppUserRepo;
import org.unibl.etf.webshop.repo.ProductRepo;
import org.unibl.etf.webshop.request.PaymentRequest;

@Service
@Slf4j
public class PaymentService {

    private final AppUserRepo appUserRepo;
    private final ProductRepo productRepo;

    @Autowired
    public PaymentService(AppUserRepo appUserRepo, ProductRepo productRepo){
        this.appUserRepo = appUserRepo;
        this.productRepo = productRepo;
    }

    public String processPayment(PaymentRequest paymentRequest){
        Long productId = paymentRequest.getProductId();
        String buyerUsername = paymentRequest.getBuyerUsername();
        Product product = productRepo.findProductById(productId).orElseThrow(()->{
            log.error("Product with id: " + productId + " not found");
            return new ProductNotFoundException("Product with id: " + productId + " not found");}
        );
        AppUser appUser = appUserRepo.findAppUserByUsername(buyerUsername).orElseThrow(()->{
            log.error("User with username: " + buyerUsername + " not found");
               return new AppUserNotFoundException("User with username: " + buyerUsername + " not found");});
        product.setBuyer(appUser);
        return "success";
    }
}
