package com.sena.crud_basic.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.sena.crud_basic.DTO.CategoryEventDTO;
import com.sena.crud_basic.DTO.ResponseDTO;
import com.sena.crud_basic.model.CategoryEvent;
import com.sena.crud_basic.service.CategoryEventService;

@RestController
@RequestMapping("/api/v1/category-events")
public class CategoryEventController {

    @Autowired
    private CategoryEventService categoryEventService;

    // Registrar una nueva categoría de evento con validaciones
    @PostMapping("/")
    public ResponseEntity<Object> registerCategory(@RequestBody CategoryEventDTO categoryEventDTO) {
        ResponseDTO response = categoryEventService.save(categoryEventDTO);
        if (response.getStatus().equals(HttpStatus.OK.toString())) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    // Consultar todas las categorías de eventos
   @GetMapping("/")
    public ResponseEntity<List<CategoryEvent>> getAllCategories() {
        List<CategoryEvent> categories = categoryEventService.findAll(); // Consulta las categorías activas
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    // Consultar una categoría de evento por ID
    @GetMapping("/{id}")
    public ResponseEntity<Object> getCategoryById(@PathVariable int id) {
        var category = categoryEventService.findById(id);
        if (!category.isPresent()) {
            return new ResponseEntity<>("", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(category.get(), HttpStatus.OK);
    }

    // Eliminar una categoría de evento por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable int id) {
        ResponseDTO response = categoryEventService.delete(id);
        if (response.getStatus().equals(HttpStatus.OK.toString())) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/filter")
public ResponseEntity<Object> filterCategory(
        @RequestParam(required = false) String name,
        @RequestParam(required = false) String description,
        @RequestParam(required = false) Boolean status) {
    
    var categoryEventServiceList = categoryEventService.filterCategory(name, description, status);
    return new ResponseEntity<>(categoryEventServiceList, HttpStatus.OK);
}
@PutMapping("/{id}")
    public ResponseEntity<Object> updateCategory(@PathVariable int id, @RequestBody CategoryEventDTO categoryEventDTO) {
        ResponseDTO response = categoryEventService.update(id, categoryEventDTO);
        if (response.getStatus().equals(HttpStatus.OK.toString())) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
}