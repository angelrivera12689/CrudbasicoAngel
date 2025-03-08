package com.sena.crud_basic.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.sena.crud_basic.DTO.CategoryEventDTO;
import com.sena.crud_basic.service.CategoryEventService;

@RestController
@RequestMapping("/api/v1/category-events")
public class CategoryEventController {

    @Autowired
    private CategoryEventService categoryEventService;

    // Registrar una nueva categoría de evento
    @PostMapping("/")
    public ResponseEntity<String> createCategoryEvent(@RequestBody CategoryEventDTO categoryEvent) {
        categoryEventService.save(categoryEvent);
        return new ResponseEntity<>("Category event created successfully", HttpStatus.CREATED);
    }
}
