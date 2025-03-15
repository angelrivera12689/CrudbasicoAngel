package com.sena.crud_basic.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.sena.crud_basic.DTO.EventsDTO;
import com.sena.crud_basic.DTO.ResponseDTO;
import com.sena.crud_basic.service.EventService;

@RestController
@RequestMapping("/api/v1/events")
public class EventController {

    @Autowired
    private EventService eventService;
    
    // Crear un nuevo evento
    @PostMapping("/")
    public ResponseEntity<Object> registerEvent(@RequestBody EventsDTO eventDTO) {
        ResponseDTO response = eventService.save(eventDTO);
        if (response.getStatus().equals(HttpStatus.OK.toString())) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    // ✅ Obtener todos los eventos
    @GetMapping("/")
    public ResponseEntity<Object> getAllEvents() {
        return new ResponseEntity<>(eventService.findAll(), HttpStatus.OK);
    }

    // ✅ Obtener un evento por su ID
    @GetMapping("/{id}")
    public ResponseEntity<Object> getEventById(@PathVariable int id) {
        var event = eventService.findById(id);
        if (!event.isPresent()) {
            return new ResponseEntity<>("Evento no encontrado", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(event.get(), HttpStatus.OK);
    }

    // ✅ Eliminar un evento por su ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteEvent(@PathVariable int id) {
        ResponseDTO response = eventService.deleteEvent(id);
        if (response.getStatus().equals(HttpStatus.OK.toString())) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
}