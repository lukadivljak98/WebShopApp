package org.unibl.etf.webshop.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.unibl.etf.webshop.model.Category;

import java.util.Optional;

public interface CategoryRepo extends JpaRepository<Category, Long> {
    Optional<Category> findCategoryById(Long id);
    Category findCategoryByType(String type);

    void deleteCategoryById(Long id);
}
