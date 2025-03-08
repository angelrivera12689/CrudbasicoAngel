package com.sena.crud_basic.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.sena.crud_basic.DTO.AssistantDTO;

import com.sena.crud_basic.service.AssistantService;

@RestController
@RequestMapping("/api/v1/assistants")
public class AssistantController {

    @Autowired
    private AssistantService assistantService;

    // Registrar un nuevo asistente
    @PostMapping("/")
    public ResponseEntity<String> createAssistant(@RequestBody AssistantDTO assistant) {
        assistantService.save(assistant);
        return new ResponseEntity<>("Assistant created successfully", HttpStatus.CREATED);
    }
}
