package org.unibl.etf.webshop.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.unibl.etf.webshop.model.ProductImage;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface ProductImageRepo extends JpaRepository<ProductImage, Long> {
    Optional<ProductImage> findProductImageById(Long id);

    Optional<List<ProductImage>> findProductImagesByProductId(Long productId);

    void deleteProductImageById(Long id);
}
