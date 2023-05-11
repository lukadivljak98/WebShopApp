package org.unibl.etf.webshop.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.unibl.etf.webshop.exception.AttributeNotFoundException;
import org.unibl.etf.webshop.exception.CategoryNotFoundException;
import org.unibl.etf.webshop.model.Attribute;
import org.unibl.etf.webshop.repo.AttributeRepo;

import java.util.List;

@Service
@Slf4j
public class AttributeService {
    private final AttributeRepo attributeRepo;

    @Autowired
    public AttributeService(AttributeRepo attributeRepo) {
        this.attributeRepo = attributeRepo;
    }

    public Attribute addAttribute(Attribute attribute){
        return attributeRepo.save(attribute);
    }

    public List<Attribute> findAllAttributes(){
        return attributeRepo.findAll();
    }

    public Attribute updateAttribute(Attribute attribute){
        return attributeRepo.save(attribute);
    }

    public Attribute findAttributeById(Long id){
        return attributeRepo.findAttributeById(id).orElseThrow(() -> {
            log.error("Attribute by id: " + id + " was not found");
                return new AttributeNotFoundException("Attribute by id: " + id + " was not found");});
    }

    public List<Attribute> findAllAtributesByCategoryId(Long categoryId){
        return attributeRepo.findAttributesByCategoryId(categoryId).orElseThrow(() ->{
            log.error("Category by id: " + categoryId + " was not found");
              return new CategoryNotFoundException("Category by id: " + categoryId + " was not found");});
    }

    public void deleteAttribute(Long id){
        attributeRepo.deleteAttributeById(id);
    }
}
