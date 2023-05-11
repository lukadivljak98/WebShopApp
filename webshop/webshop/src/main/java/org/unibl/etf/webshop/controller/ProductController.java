package org.unibl.etf.webshop.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.unibl.etf.webshop.model.Product;
import org.unibl.etf.webshop.request.Filter;
import org.unibl.etf.webshop.request.PaymentRequest;
import org.unibl.etf.webshop.request.ProductRequest;
import org.unibl.etf.webshop.service.ProductService;

import java.util.List;

@RestController
@RequestMapping("/products")
@Slf4j
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts(){
        List<Product> products = productService.findAllProducts();
        log.info("Success getting all products on url: localhost:8080/products. List: " + products);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable("id") Long id){
        Product product = productService.findProductById(id);
        log.info("Success getting product by id on url: localhost:8080/products/" + id + ". Product: " + product);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<Product[]> getProductsByCategory(@PathVariable("categoryId") Long categoryId){
        Product[] products = productService.findProductsByCategory(categoryId);
        log.info("Success getting products by categoryId on url: localhost:8080/products/" + categoryId + ". List: " + products);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<Product[]> getProductsByUserId(@PathVariable("userId") Long userId){
        Product[] products = productService.findProductsByUserId(userId);
        log.info("Success getting products by UserId on url: localhost:8080/products/" + userId + ". List: " + products);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/buyer/{buyerId}")
    public ResponseEntity<Product[]> getProductsByBuyerId(@PathVariable("buyerId") Long buyerId){
        Product[] products = productService.findProductsByBuyerId(buyerId);
        log.info("Success getting products by buyerId on url: localhost:8080/products/" + buyerId + ". List: " + products);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Product> addProduct(@ModelAttribute ProductRequest request){
        Product newProduct = productService.addProduct(request);
        log.info("Success adding product on url: localhost:8080/products. Product: " + newProduct);
        return new ResponseEntity<>(newProduct, HttpStatus.CREATED);
    }

    @PostMapping("/filter")
    public ResponseEntity<List<Product>> getProductsByFilter(@ModelAttribute Filter filter){
        List<Product> products = productService.filterProducts(filter);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<Product> updateProduct(@RequestBody PaymentRequest request){
        Product updatedProduct = productService.updateProduct(request);
        log.info("Success updating product on url: localhost:8080/products. Product: " + updatedProduct);
        return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> deleteProduct(@PathVariable("id") Long id){
        productService.deleteProduct(id);
        log.info("Success deleting the product on url: localhost:8080/products/" + id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
