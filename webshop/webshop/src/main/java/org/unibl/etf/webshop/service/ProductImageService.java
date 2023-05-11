package org.unibl.etf.webshop.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.unibl.etf.webshop.exception.ProductImageNotFoundException;
import org.unibl.etf.webshop.exception.ProductNotFoundException;
import org.unibl.etf.webshop.model.ProductAttribute;
import org.unibl.etf.webshop.model.ProductImage;
import org.unibl.etf.webshop.repo.ProductImageRepo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
@Slf4j
public class ProductImageService {
    private final ProductImageRepo productImageRepo;
    @Autowired
    private Environment environment;

    @Autowired
    public ProductImageService(ProductImageRepo productImageRepo) {
        this.productImageRepo = productImageRepo;
    }

    public ProductImage addProductImage(ProductImage productImage){
        return productImageRepo.save(productImage);
    }

    public List<ProductImage> findAllProductImages(){
        return productImageRepo.findAll();
    }

    public ProductImage updateProductImage(ProductImage productImage){
        return productImageRepo.save(productImage);
    }

    public ProductImage findProductImageById(Long id){
        return productImageRepo.findProductImageById(id).orElseThrow(() ->{
            log.error("ProductImage by id: " + id + " was not found");
                return new ProductImageNotFoundException("ProductImage by id: " + id + " was not found");});
    }

    public void deleteProductImage(Long id) {
        productImageRepo.deleteProductImageById(id);
    }

    public List<ProductImage> findAllProductImagesByProductId(Long productId) {
        return productImageRepo.findProductImagesByProductId(productId).orElseThrow(() ->{
            log.error("Product by id: " + productId + " was not found");
                return new ProductNotFoundException("Product by id: " + productId + " was not found");});
    }
}
