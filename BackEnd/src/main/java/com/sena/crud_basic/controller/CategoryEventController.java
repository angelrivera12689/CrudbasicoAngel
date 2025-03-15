package com.sena.crud_basic.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.sena.crud_basic.DTO.CategoryEventDTO;
import com.sena.crud_basic.DTO.ResponseDTO;
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
    public ResponseEntity<Object> getAllCategories() {
        return new ResponseEntity<>(categoryEventService.findAll(), HttpStatus.OK);
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
    public ResponseEntity<Object> deleteCategory(@PathVariable int id) {
        ResponseDTO response = categoryEventService.deleteCategory(id);
        if (response.getStatus().equals(HttpStatus.OK.toString())) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
}