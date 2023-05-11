package org.unibl.etf.webshop.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.unibl.etf.webshop.exception.AttributeNotFoundException;
import org.unibl.etf.webshop.exception.ProductNotFoundException;
import org.unibl.etf.webshop.model.Attribute;
import org.unibl.etf.webshop.model.Product;
import org.unibl.etf.webshop.model.ProductAttribute;
import org.unibl.etf.webshop.repo.AttributeRepo;
import org.unibl.etf.webshop.repo.ProductAttributeRepo;
import org.unibl.etf.webshop.repo.ProductRepo;
import org.unibl.etf.webshop.request.ProductAttributeRequest;


import java.util.List;

@Service
@Slf4j
public class ProductAttributeService {
    private final ProductAttributeRepo productAttributeRepo;
    private final AttributeRepo attributeRepo;
    private final ProductRepo productRepo;

    @Autowired
    public ProductAttributeService(ProductAttributeRepo productAttributeRepo, AttributeRepo attributeRepo,
                                   ProductRepo productRepo){
        this.productAttributeRepo = productAttributeRepo;
        this.attributeRepo = attributeRepo;
        this.productRepo = productRepo;
    }

    public List<ProductAttribute> findAllProductAttributesByProductId(Long productId){
        return productAttributeRepo.findProductAttributesByProductId(productId).orElseThrow(() ->{
            log.error("Product by id: " + productId + " was not found");
                return new ProductNotFoundException("Product by id: " + productId + " was not found");});
    }

    public ProductAttribute addProductAttribute(ProductAttributeRequest productAttributeRequest) {
        ProductAttribute productAttribute = new ProductAttribute();
        Long attributeId = productAttributeRequest.getAttributeId();
        Long productId = productAttributeRequest.getProductId();
        Attribute attribute = attributeRepo.findAttributeById(attributeId).orElseThrow(()->{
            log.error("Attribute by id: " + attributeId + " not found");
            return new AttributeNotFoundException("Attribute by id: " + attributeId + " not found");}
        );
        Product product = productRepo.findProductById(productId).orElseThrow(()->{
            log.error("Product by id: " + productId + " not found");
                return new ProductNotFoundException("Product by id: " + productId + " not found");}
        );
        productAttribute.setValue(productAttributeRequest.getValue());
        productAttribute.setAttribute(attribute);
        productAttribute.setProduct(product);
        return productAttributeRepo.save(productAttribute);
    }
}
