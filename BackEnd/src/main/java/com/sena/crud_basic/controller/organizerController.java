package com.sena.crud_basic.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.sena.crud_basic.DTO.ResponseDTO;
import com.sena.crud_basic.DTO.organizerDTO;
import com.sena.crud_basic.model.organizer;
import com.sena.crud_basic.service.organizerService;
import com.sena.crud_basic.Resource.RateLimiterService;

@RestController
@RequestMapping("/api/v1/organizer")
public class organizerController {

    @Autowired
    private organizerService organizerService;

    @Autowired
    private RateLimiterService rateLimiter;

    // ✅ Método para verificar el límite
    private boolean isRateLimited() {
        return !rateLimiter.tryConsume();
    }

    // ✅ Crear organizador
    @PostMapping("/")
    public ResponseEntity<Object> registerOrganizer(@RequestBody organizerDTO organizerDTO) {
        if (isRateLimited()) {
            return new ResponseEntity<>(new ResponseDTO("429", "🚫 Límite de peticiones alcanzado"), HttpStatus.TOO_MANY_REQUESTS);
        }

        ResponseDTO response = organizerService.save(organizerDTO);
        if (response.getStatus().equals(HttpStatus.OK.toString())) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    // ✅ Obtener todos los organizadores
    @GetMapping("/")
    public ResponseEntity<Object> getAllOrganizers() {
        if (isRateLimited()) {
            return new ResponseEntity<>(new ResponseDTO("429", "🚫 Límite de peticiones alcanzado"), HttpStatus.TOO_MANY_REQUESTS);
        }

        return new ResponseEntity<>(organizerService.findAll(), HttpStatus.OK);
    }

    // ✅ Obtener un organizador por ID
    @GetMapping("/{id}")
    public ResponseEntity<Object> getOrganizerById(@PathVariable int id) {
        if (isRateLimited()) {
            return new ResponseEntity<>(new ResponseDTO("429", "🚫 Límite de peticiones alcanzado"), HttpStatus.TOO_MANY_REQUESTS);
        }

        var organizer = organizerService.findById(id);
        if (!organizer.isPresent()) {
            return new ResponseEntity<>("Organizador no encontrado", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(organizer.get(), HttpStatus.OK);
    }

    // ✅ Eliminar organizador
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteOrganizer(@PathVariable int id) {
        if (isRateLimited()) {
            return new ResponseEntity<>(new ResponseDTO("429", "🚫 Límite de peticiones alcanzado"), HttpStatus.TOO_MANY_REQUESTS);
        }

        ResponseDTO response = organizerService.deleteOrganizer(id);
        if (response.getStatus().equals(HttpStatus.OK.toString())) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    // ✅ Filtrar organizadores
    @GetMapping("/filter")
    public ResponseEntity<Object> filterOrganizer(
            @RequestParam(required = false, name = "name") String name,
            @RequestParam(required = false, name = "phone") String phone,
            @RequestParam(required = false, name = "email") String email,
            @RequestParam(required = false, name = "status") Boolean status) {

        if (isRateLimited()) {
            return new ResponseEntity<>(new ResponseDTO("429", "🚫 Límite de peticiones alcanzado"), HttpStatus.TOO_MANY_REQUESTS);
        }

        List<organizer> organizerList = organizerService.filterorganizers(name, phone, email, status);
        return new ResponseEntity<>(organizerList, HttpStatus.OK);
    }

    // ✅ Actualizar organizador
    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@PathVariable int id, @RequestBody organizerDTO organizerDTO) {
        if (isRateLimited()) {
            return new ResponseEntity<>(new ResponseDTO("429", "🚫 Límite de peticiones alcanzado"), HttpStatus.TOO_MANY_REQUESTS);
        }

        ResponseDTO response = organizerService.update(id, organizerDTO);
        if (response.getStatus().equals(HttpStatus.OK.toString())) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
