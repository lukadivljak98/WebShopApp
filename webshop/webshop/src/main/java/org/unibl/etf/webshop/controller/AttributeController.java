package org.unibl.etf.webshop.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.unibl.etf.webshop.model.Attribute;
import org.unibl.etf.webshop.service.AttributeService;

import java.util.List;

@RestController
@RequestMapping("/attributes")
@Slf4j
public class AttributeController {
    private final AttributeService attributeService;

    public AttributeController(AttributeService attributeService) {
        this.attributeService = attributeService;
    }

    @GetMapping
    public ResponseEntity<List<Attribute>> getAllAttributes(){
        List<Attribute> attributes = attributeService.findAllAttributes();
        log.info("Success getting all attributes on url: localhost:8080/attributes. List: " + attributes);
        return new ResponseEntity<>(attributes, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Attribute> getAttributeById(@PathVariable("id") Long id){
        Attribute attribute = attributeService.findAttributeById(id);
        log.info("Success getting attribute by id on url: localhost:8080/attributes/" + id + ". Attribute: " + attribute);
        return new ResponseEntity<>(attribute, HttpStatus.OK);
    }

    @GetMapping("/category/{id}")
    public ResponseEntity<List<Attribute>> getAttributesByCategoryId(@PathVariable("id") Long categoryId){
        List<Attribute> attributes = attributeService.findAllAtributesByCategoryId(categoryId);
        log.info("Success getting attributes by categoryId on url: localhost:8080/attributes/" + categoryId + ". List: " + attributes);
        return new ResponseEntity<>(attributes, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Attribute> addAttribute(@RequestBody Attribute attribute){
        Attribute newAttribute = attributeService.addAttribute(attribute);
        log.info("Success adding attribute on url: localhost:8080/attributes. Attribute: " + newAttribute);
        return new ResponseEntity<>(newAttribute, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Attribute> updateAttribute(@RequestBody Attribute attribute){
        Attribute updateAttribute = attributeService.addAttribute(attribute);
        log.info("Success updating attribute on url: localhost:8080/attributes. Attribute: " + updateAttribute);
        return new ResponseEntity<>(updateAttribute, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAttribute(@PathVariable("id") Long id){
        attributeService.deleteAttribute(id);
        log.info("Success deleting the attribute on url: localhost:8080/attributes/" + id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
