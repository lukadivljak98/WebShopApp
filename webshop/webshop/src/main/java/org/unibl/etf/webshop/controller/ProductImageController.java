package org.unibl.etf.webshop.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.unibl.etf.webshop.model.ProductImage;
import org.unibl.etf.webshop.service.ProductImageService;

import java.util.List;

@RestController
@RequestMapping("/product-images")
@Slf4j
public class ProductImageController {
    private final ProductImageService productImageService;

    public ProductImageController(ProductImageService productImageService) {
        this.productImageService = productImageService;
    }

    @GetMapping
    public ResponseEntity<List<ProductImage>> getAllProductImages(){
        List<ProductImage> productImages = productImageService.findAllProductImages();
        log.info("Success getting all product images on url: localhost:8080/product-images. List: " + productImages);
        return new ResponseEntity<>(productImages, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductImage> getProductImageById(@PathVariable("id") Long id){
        ProductImage productImage = productImageService.findProductImageById(id);
        log.info("Success getting product image by id on url: localhost:8080/product-images/" + id + ". ProductImage: " + productImage);
        return new ResponseEntity<>(productImage, HttpStatus.OK);
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<List<ProductImage>> getProductImageByProductId(@PathVariable("id") Long productId){
        List<ProductImage> productImages = productImageService.findAllProductImagesByProductId(productId);
        log.info("Success getting product images by productId on url: localhost:8080/product-images/" + productId + ". List: " + productImages);
        return new ResponseEntity<>(productImages, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ProductImage> addProductImage(@RequestBody ProductImage productImage){
        ProductImage newProductImage = productImageService.addProductImage(productImage);
        log.info("Success adding product image on url: localhost:8080/product-images. ProductImage: " + newProductImage);
        return new ResponseEntity<>(newProductImage, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<ProductImage> updateProductImage(@RequestBody ProductImage productImage){
        ProductImage updateProductImage = productImageService.addProductImage(productImage);
        log.info("Success updating product image on url: localhost:8080/product-images. ProductImage: " + updateProductImage);
        return new ResponseEntity<>(updateProductImage, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProductImage(@PathVariable("id") Long id){
        productImageService.deleteProductImage(id);
        log.info("Success deleting the product image on url: localhost:8080/product-images/" + id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
