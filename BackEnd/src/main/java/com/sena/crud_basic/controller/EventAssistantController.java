package com.sena.crud_basic.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.sena.crud_basic.DTO.EventAssistantDTO;
import com.sena.crud_basic.service.EventAssistantService;

@RestController
@RequestMapping("/api/v1/event-assistant")
public class EventAssistantController {

    @Autowired
    private EventAssistantService eventAssistantService;

    // Registrar una nueva relación entre evento y asistente
    @PostMapping("/")
    public ResponseEntity<String> createEventAssistant(@RequestBody EventAssistantDTO eventAssistant) {
        eventAssistantService.save(eventAssistant);
        return new ResponseEntity<>("Event-Assistant association created successfully", HttpStatus.CREATED);
    }
}
