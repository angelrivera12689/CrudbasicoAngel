package com.sena.crud_basic.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.sena.crud_basic.DTO.ResponseDTO;
import com.sena.crud_basic.DTO.organizerDTO;
import com.sena.crud_basic.service.organizerService;

@RestController
@RequestMapping("/api/v1/organizer")
public class organizerController {

    @Autowired
    private organizerService organizerService;

      @PostMapping("/")
    public ResponseEntity<Object> registerOrganizer(@RequestBody organizerDTO organizerDTO) {
        ResponseDTO response = organizerService.save(organizerDTO);
        if (response.getStatus().equals(HttpStatus.OK.toString())) {
            return new ResponseEntity<>(response, HttpStatus.OK);  // Código 200 OK
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);  // Código 400 Bad Request
        }
    }

    // ✅ Obtener todos los organizadores
    @GetMapping("/")
    public ResponseEntity<Object> getAllOrganizers() {
        return new ResponseEntity<>(organizerService.findAll(), HttpStatus.OK);
    }

    // ✅ Obtener un organizador por ID
    @GetMapping("/{id}")
    public ResponseEntity<Object> getOrganizerById(@PathVariable int id) {
        var organizer = organizerService.findById(id);
        if (!organizer.isPresent()) {
            return new ResponseEntity<>("Organizador no encontrado", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(organizer.get(), HttpStatus.OK);
    }

    // ✅ Eliminar un organizador por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteOrganizer(@PathVariable int id) {
        ResponseDTO response = organizerService.deleteOrganizer(id);
        if (response.getStatus().equals(HttpStatus.OK.toString())) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
}
