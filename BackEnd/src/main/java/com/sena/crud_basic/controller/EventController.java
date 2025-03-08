package com.sena.crud_basic.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.sena.crud_basic.DTO.EventsDTO;
import com.sena.crud_basic.service.EventService;

@RestController
@RequestMapping("/api/v1/events")
public class EventController {

    @Autowired
    private EventService eventService;
    
    // Crear un nuevo evento
    @PostMapping("/")
    public ResponseEntity<String> createEvent(@RequestBody EventsDTO event) {
        eventService.save(event);
        return new ResponseEntity<>("Event created successfully", HttpStatus.CREATED);
    }
}