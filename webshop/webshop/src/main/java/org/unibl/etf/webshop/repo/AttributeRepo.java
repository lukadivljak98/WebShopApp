package org.unibl.etf.webshop.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.unibl.etf.webshop.model.Attribute;

import java.util.List;
import java.util.Optional;

public interface AttributeRepo extends JpaRepository<Attribute, Long> {
    Optional<Attribute> findAttributeById(Long id);

    Optional<Attribute> findAttributeByName(String name);

    Optional<List<Attribute>> findAttributesByCategoryId(Long categoryId);

    void deleteAttributeById(Long id);

}
