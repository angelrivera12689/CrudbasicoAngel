package com.sena.crud_basic.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.sena.crud_basic.DTO.EventAssistantDTO;
import com.sena.crud_basic.DTO.ResponseDTO;
import com.sena.crud_basic.service.EventAssistantService;

@RestController
@RequestMapping("/api/v1/event-assistant")
public class EventAssistantController {

    @Autowired
    private EventAssistantService eventAssistantService;

    // Registrar una nueva relación entre evento y asistente
    @PostMapping("/")
    public ResponseEntity<Object> registerEventAssistant(@RequestBody EventAssistantDTO eventAssistantDTO) {
        ResponseDTO response = eventAssistantService.save(eventAssistantDTO);
        if (response.getStatus().equals(HttpStatus.OK.toString())) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    // 📌 Consultar todas las relaciones Evento-Asistente
    @GetMapping("/")
    public ResponseEntity<Object> getAllEventAssistants() {
        return new ResponseEntity<>(eventAssistantService.findAll(), HttpStatus.OK);
    }

    // 📌 Consultar una relación por ID
    @GetMapping("/{id}")
    public ResponseEntity<Object> getEventAssistantById(@PathVariable int id) {
        var eventAssistant = eventAssistantService.findById(id);
        if (!eventAssistant.isPresent()) {
            return new ResponseEntity<>("Registro no encontrado", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(eventAssistant.get(), HttpStatus.OK);
    }

    // 📌 Eliminar una relación por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteEventAssistant(@PathVariable int id) {
        ResponseDTO response = eventAssistantService.deleteById(id);
        if (response.getStatus().equals(HttpStatus.OK.toString())) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}