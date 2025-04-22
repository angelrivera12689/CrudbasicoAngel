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
import com.sena.crud_basic.Resource.RateLimiterService;

@RestController
@RequestMapping("/api/v1/category-events")
public class CategoryEventController {

    @Autowired
    private CategoryEventService categoryEventService;

    @Autowired
    private RateLimiterService rateLimiter;

    private boolean isRateLimited() {
        return !rateLimiter.tryConsume();
    }

    // ✅ Registrar una nueva categoría de evento
    @PostMapping("/")
    public ResponseEntity<Object> registerCategory(@RequestBody CategoryEventDTO categoryEventDTO) {
        if (isRateLimited()) {
            return new ResponseEntity<>(new ResponseDTO("429", "🚫 Límite de peticiones alcanzado"), HttpStatus.TOO_MANY_REQUESTS);
        }

        ResponseDTO response = categoryEventService.save(categoryEventDTO);
        return new ResponseEntity<>(response, response.getStatus().equals(HttpStatus.OK.toString()) ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }

    // ✅ Consultar todas las categorías de eventos
    @GetMapping("/")
    public ResponseEntity<List<CategoryEvent>> getAllCategories() {
        if (isRateLimited()) {
            return new ResponseEntity<>(HttpStatus.TOO_MANY_REQUESTS);
        }

        List<CategoryEvent> categories = categoryEventService.findAll();
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    // ✅ Consultar una categoría por ID
    @GetMapping("/{id}")
    public ResponseEntity<Object> getCategoryById(@PathVariable int id) {
        if (isRateLimited()) {
            return new ResponseEntity<>(new ResponseDTO("429", "🚫 Límite de peticiones alcanzado"), HttpStatus.TOO_MANY_REQUESTS);
        }

        var category = categoryEventService.findById(id);
        return category.isPresent()
                ? new ResponseEntity<>(category.get(), HttpStatus.OK)
                : new ResponseEntity<>("Categoría no encontrada", HttpStatus.NOT_FOUND);
    }

    // ✅ Eliminar una categoría por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable int id) {
        if (isRateLimited()) {
            return new ResponseEntity<>(new ResponseDTO("429", "🚫 Límite de peticiones alcanzado"), HttpStatus.TOO_MANY_REQUESTS);
        }

        ResponseDTO response = categoryEventService.delete(id);
        return new ResponseEntity<>(response, response.getStatus().equals(HttpStatus.OK.toString()) ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }

    // ✅ Filtrar categorías
    @GetMapping("/filter")
    public ResponseEntity<Object> filterCategory(
            @RequestParam(required = false) Integer id,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) Boolean status) {

        if (isRateLimited()) {
            return new ResponseEntity<>(new ResponseDTO("429", "🚫 Límite de peticiones alcanzado"), HttpStatus.TOO_MANY_REQUESTS);
        }

        var categoryList = categoryEventService.filterCategory(id, name, description, status);
        return new ResponseEntity<>(categoryList, HttpStatus.OK);
    }

    // ✅ Actualizar una categoría
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateCategory(@PathVariable int id, @RequestBody CategoryEventDTO categoryEventDTO) {
        if (isRateLimited()) {
            return new ResponseEntity<>(new ResponseDTO("429", "🚫 Límite de peticiones alcanzado"), HttpStatus.TOO_MANY_REQUESTS);
        }

        ResponseDTO response = categoryEventService.update(id, categoryEventDTO);
        return new ResponseEntity<>(response, response.getStatus().equals(HttpStatus.OK.toString()) ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }
}
