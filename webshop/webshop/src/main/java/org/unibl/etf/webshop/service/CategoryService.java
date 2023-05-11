package org.unibl.etf.webshop.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.unibl.etf.webshop.exception.CategoryNotFoundException;
import org.unibl.etf.webshop.model.Category;
import org.unibl.etf.webshop.repo.CategoryRepo;

import java.util.List;

@Service
@Slf4j
public class CategoryService {
    private final CategoryRepo categoryRepo;

    @Autowired
    public CategoryService(CategoryRepo categoryRepo) {
        this.categoryRepo = categoryRepo;
    }

    public Category addCategory(Category category){
        return categoryRepo.save(category);
    }

    public List<Category> findAllCategories(){
        return categoryRepo.findAll();
    }

    public Category updateCategory(Category category){
        return categoryRepo.save(category);
    }

    public Category findCategoryById(Long id){
        return categoryRepo.findCategoryById(id).orElseThrow(() ->{
            log.error("Category by id: " + id + " was not found");
                return new CategoryNotFoundException("Category by id: " + id + " was not found");});
    }

    public void deleteCategory(Long id){
        categoryRepo.deleteCategoryById(id);
    }
}
