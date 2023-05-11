package org.unibl.etf.webshop.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.unibl.etf.webshop.exception.AppUserNotFoundException;
import org.unibl.etf.webshop.exception.AttributeNotFoundException;
import org.unibl.etf.webshop.exception.ProductImageNotFoundException;
import org.unibl.etf.webshop.exception.ProductNotFoundException;
import org.unibl.etf.webshop.model.*;
import org.unibl.etf.webshop.repo.*;
import org.unibl.etf.webshop.request.Filter;
import org.unibl.etf.webshop.request.PaymentRequest;
import org.unibl.etf.webshop.request.ProductRequest;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ProductService {
    private final ProductRepo productRepo;
    private final CategoryRepo categoryRepo;
    private final AppUserRepo appUserRepo;
    private final ProductImageRepo productImageRepo;
    private final AttributeRepo attributeRepo;
    private final ProductAttributeRepo productAttributeRepo;

    private final ProductImageService productImageService;

    @Autowired
    private Environment environment;

    @Autowired
    public ProductService(ProductRepo productRepo, CategoryRepo categoryRepo, AppUserRepo appUserRepo,
                          ProductImageRepo productImageRepo, AttributeRepo attributeRepo
                          , ProductImageService productImageService
    , ProductAttributeRepo productAttributeRepo) {
        this.productRepo = productRepo;
        this.categoryRepo = categoryRepo;
        this.appUserRepo = appUserRepo;
        this.productImageRepo =productImageRepo;
        this.attributeRepo = attributeRepo;
        this.productAttributeRepo = productAttributeRepo;
        this.productImageService = productImageService;
    }

    public Product addProduct(ProductRequest request){
        System.out.println(request);
        Product product = new Product();
        product.setTitle(request.getTitle());
        product.setDescription(request.getDescription());
        product.setPrice(Double.parseDouble(request.getPrice()));
        product.setNew(request.isNew());
        product.setLocation(request.getLocation());
        product.setCategory(categoryRepo.findCategoryByType(request.getCategory()));
        AppUser appUser = appUserRepo.findAppUserByUsername(request.getSeller()).orElseThrow(()->{
            log.error("User with username: " + request.getSeller() + " not found");
                return new AppUserNotFoundException("User with username: " + request.getSeller() + " not found");});
        product.setSeller(appUser);
        product.setProductImage(saveProductImages(request.getImages(), product));
        return productRepo.save(product);
    }

    public List<ProductImage> saveProductImages(MultipartFile[] images, Product product){
        Path root = Paths.get(environment.getProperty("file.upload.path")).toAbsolutePath();
        List<ProductImage> productImages = new ArrayList<>();
        System.out.println(images[0].getOriginalFilename());
        for (int i = 0; i < images.length; i++) {
            try {
                Files.copy(images[i].getInputStream(), root.resolve(Objects.requireNonNull(images[i].getOriginalFilename())));
            } catch (Exception e) {
                log.error("Could not store the file. Error: " + e.getMessage());
                throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
            }
            ProductImage newProductImage = new ProductImage();
            newProductImage.setUrl("http://localhost:8080/images/" + images[i].getOriginalFilename());
            newProductImage.setProduct(product);
            productImageRepo.save(newProductImage);
            productImages.add(newProductImage);
            if(i == 0)
                product.setImageUrl("http://localhost:8080/images/" + images[i].getOriginalFilename());
        }
        return productImages;
    }

    public List<Product> findAllProducts(){
        return productRepo.findAll();
    }

    public Product updateProduct(PaymentRequest request){
        Long productId = request.getProductId();
        String buyerUsername = request.getBuyerUsername();
        Product product = productRepo.findProductById(productId).orElseThrow(()->{
            log.error("Product with id: " + productId + " not found");
                return new ProductNotFoundException("Product with id: " + productId + " not found");}
        );
        AppUser appUser = appUserRepo.findAppUserByUsername(buyerUsername).orElseThrow(()->{
            log.error("User with username: " + buyerUsername + " not found");
                return new AppUserNotFoundException("User with username: " + buyerUsername + " not found");});
        if(product.getBuyer() == null)
            product.setBuyer(appUser);
        return productRepo.save(product);
    }

    public Product findProductById(Long id){
        return productRepo.findProductById(id).orElseThrow(() ->{
            log.error("Product by id: " + id + " was not found");
                return new ProductNotFoundException("Product by id: " + id + " was not found");});
    }

    public void deleteProduct(Long id){
        Path root = Paths.get(environment.getProperty("file.upload.path")).toAbsolutePath();
        List<ProductImage> productImages = productImageRepo.findProductImagesByProductId(id).orElseThrow(()->
                new ProductImageNotFoundException("Product Image not found"));
        for(ProductImage productImage : productImages){
            String[] s = productImage.getUrl().split("/");
            String imageName = s[s.length-1];
            Path path = root.resolve(imageName);
            try {
                Files.delete(path);
            }catch(IOException e){
                log.error("Failed deleting the file: " + e);
            }
            //productImageService.deleteProductImage(productImage.getId());
        }
        productRepo.deleteProductById(id);
    }

    public Product[] findProductsByCategory(Long categoryId) {
        return productRepo.findProductsByCategoryId(categoryId).orElseThrow(() ->{
            log.error("Products not found");
                return new ProductNotFoundException("Products not found");});
    }

    public Product[] findProductsByUserId(Long userId) {
        return productRepo.findProductsBySellerId(userId).orElseThrow(() ->{
            log.error("Products not found");
                return new ProductNotFoundException("Products not found");});
    }

    public Product[] findProductsByBuyerId(Long buyerId) {
        return productRepo.findProductsByBuyerId(buyerId).orElseThrow(() ->{
            log.error("Products not found");
                return new ProductNotFoundException("Products not found");});
    }

    public List<Product> filterProducts(Filter filter) {
        List<Product> products = productRepo.findAll();
        log.info(String.valueOf(filter));
        double priceOd = Double.parseDouble(filter.getPriceOd());
        double priceDo = Double.parseDouble(filter.getPriceDo());
        boolean isNew = Objects.equals(filter.getIsNew(), "on");
        List<Product> filteredProducts = products.stream()
                .filter(product -> product.getCategory().getType().equalsIgnoreCase(filter.getCategory()))
                .toList();
        List<Product> finalList = new ArrayList<>();
        for(Product p : filteredProducts) {
            for (String s : filter.getMap().split(";")) {
                String attributeName = s.split("-")[0];
                Attribute attribute = attributeRepo.findAttributeByName(attributeName).orElseThrow(() ->
                        new AttributeNotFoundException("Attribute not found"));
                ProductAttribute productAttribute = productAttributeRepo.findProductAttributeByProductIdAndAttributeId(p.getId(), attribute.getId())
                        .orElseThrow(() -> new ProductNotFoundException("product not found"));
                double value1 = Double.parseDouble(s.split("-")[1]);
                double value2 = Double.parseDouble(s.split("-")[2]);
                if(Double.parseDouble(productAttribute.getValue()) >= value1 &&
                Double.parseDouble(productAttribute.getValue()) <= value2)
                    finalList.add(p);
            }
        }
        return finalList.stream()
                .filter(product -> product.getPrice() >= priceOd && product.getPrice() <= priceDo)
                .filter(product -> product.getLocation().equalsIgnoreCase(filter.getLocation()))
                .filter(product -> product.isNew() == isNew)
                .distinct()
                .collect(Collectors.toList());
    }
}
