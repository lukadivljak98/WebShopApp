package org.unibl.etf.webshop.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.unibl.etf.webshop.model.Category;
import org.unibl.etf.webshop.service.CategoryService;

import java.util.List;

@RestController
@RequestMapping("/categories")
@Slf4j
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public ResponseEntity<List<Category>> getAllCategories(){
        List<Category> categories = categoryService.findAllCategories();
        log.info("Success getting all categories on url: localhost:8080/categories. List: " + categories);
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable("id") Long id){
        Category category = categoryService.findCategoryById(id);
        log.info("Success getting category by id on url: localhost:8080/categories/" + id + ". Category: " + category);
        return new ResponseEntity<>(category, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Category> addCategory(@RequestBody Category category){
        Category newCategory = categoryService.addCategory(category);
        log.info("Success adding category on url: localhost:8080/categories. Category: " + newCategory);
        return new ResponseEntity<>(newCategory, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Category> updateCategory(@RequestBody Category category){
        Category updateCategory = categoryService.addCategory(category);
        log.info("Success updating category on url: localhost:8080/categories. Category: " + updateCategory);
        return new ResponseEntity<>(updateCategory, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable("id") Long id){
        categoryService.deleteCategory(id);
        log.info("Success deleting the category on url: localhost:8080/categories/" + id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
