package org.unibl.etf.webshop.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import org.unibl.etf.webshop.model.ProductAttribute;

import java.util.List;
import java.util.Optional;

public interface ProductAttributeRepo extends JpaRepository<ProductAttribute, Long> {
    Optional<List<ProductAttribute>> findProductAttributesByProductId(Long productId);


    Optional<ProductAttribute> findProductAttributeByAttributeId(Long attributeId);
    Optional<ProductAttribute> findProductAttributeByProductIdAndAttributeId(Long productId, Long attributeId);
}
