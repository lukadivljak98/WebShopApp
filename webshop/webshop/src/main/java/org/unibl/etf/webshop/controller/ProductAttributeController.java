package org.unibl.etf.webshop.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.unibl.etf.webshop.model.ProductAttribute;
import org.unibl.etf.webshop.request.ProductAttributeRequest;
import org.unibl.etf.webshop.service.ProductAttributeService;

import java.util.List;

@RestController
@RequestMapping("/product-attributes")
@Slf4j
public class ProductAttributeController {
    private final ProductAttributeService productAttributeService;

    @Autowired
    public ProductAttributeController(ProductAttributeService productAttributeService){
        this.productAttributeService = productAttributeService;
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<List<ProductAttribute>> getProductAttributesByProductId(@PathVariable("id") Long productId){
        List<ProductAttribute> productAttributes = productAttributeService.findAllProductAttributesByProductId(productId);
        log.info("Success getting product attributes by productId on url: localhost:8080/product-attributes/" + productId + ". List: " + productAttributes);
        return new ResponseEntity<>(productAttributes, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ProductAttribute> addProductAttribute(@RequestBody ProductAttributeRequest productAttributeRequest){
        ProductAttribute newProductAttribute = productAttributeService.addProductAttribute(productAttributeRequest);
        log.info("Success adding product attribute on url: localhost:8080/product-attributes. ProductAttribute: " + newProductAttribute);
        return new ResponseEntity<>(newProductAttribute, HttpStatus.CREATED);
    }
}
