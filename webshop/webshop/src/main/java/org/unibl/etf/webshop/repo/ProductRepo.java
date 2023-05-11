package org.unibl.etf.webshop.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.unibl.etf.webshop.model.AppUser;
import org.unibl.etf.webshop.model.Product;

import java.util.Optional;

public interface ProductRepo extends JpaRepository<Product, Long> {
    void deleteProductById(Long id);

    Optional<Product[]> findProductsByCategoryId(Long categoryId);

    Optional<Product> findProductById(Long id);

    Optional<Product[]> findProductsBySellerId(Long userId);

    Optional<Product[]> findProductsByBuyerId(Long buyerId);

}
