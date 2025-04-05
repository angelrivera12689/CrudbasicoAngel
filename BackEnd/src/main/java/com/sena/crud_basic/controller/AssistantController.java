package com.sena.crud_basic.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.sena.crud_basic.DTO.AssistantDTO;
import com.sena.crud_basic.DTO.ResponseDTO;
import com.sena.crud_basic.model.Assistant;
import com.sena.crud_basic.service.AssistantService;

@RestController
@RequestMapping("/api/v1/assistants")
public class AssistantController {

    @Autowired
    private AssistantService assistantService;  // Usar 'assistantService' en lugar de 'AssistantService'

    // Registrar un nuevo asistente con validaciones
    @PostMapping("/")
    public ResponseEntity<Object> registerAssistant(@RequestBody AssistantDTO assistantDTO) {
        // Llamamos al servicio para guardar el asistente y obtener la respuesta
        ResponseDTO respuesta = assistantService.save(assistantDTO);
        if (respuesta.getStatus().equals(HttpStatus.OK.toString())) {
            return new ResponseEntity<>(respuesta, HttpStatus.OK);  // Si es OK, respuesta 200
        } else {
            return new ResponseEntity<>(respuesta, HttpStatus.BAD_REQUEST);  // Si hay error, respuesta 400
        }
    }

    // Consultar todos los asistentes
    @GetMapping("/")
    public ResponseEntity<Object> getAllAssistants() {
        return new ResponseEntity<>(assistantService.findAll(), HttpStatus.OK);
    }

    // Consultar un asistente por su ID
    @GetMapping("/{id}")
    public ResponseEntity<Object> getAssistantById(@PathVariable int id) {
        var assistant = assistantService.findById(id);
        if (!assistant.isPresent()) {
            return new ResponseEntity<>("", HttpStatus.NOT_FOUND);  // Si no lo encontramos, 404
        }
        return new ResponseEntity<>(assistant.get(), HttpStatus.OK);  // Si lo encontramos, 200
    }

    // Eliminar un asistente por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteAssistant(@PathVariable int id) {
        // Llamamos al servicio para eliminar al asistente
        ResponseDTO message = assistantService.delete(id);
        
        if (message.getStatus().equals(HttpStatus.OK.toString())) {
            return new ResponseEntity<>(message, HttpStatus.OK);  // Si la eliminación es exitosa
        } else {
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);  // Si hay algún problema
        }
    }
    @GetMapping("/filter")
public ResponseEntity<List<Assistant>> filterAssistants(
        @RequestParam(required = false) Integer id,
        @RequestParam(required = false) String name,
        @RequestParam(required = false) String email,
        @RequestParam(required = false) String phone,
        @RequestParam(required = false) Boolean status
) {
    List<Assistant> results = assistantService.filterAssistants(id, name, email, phone, status);
    return new ResponseEntity<>(results, HttpStatus.OK);
}
    
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateAssistant(@PathVariable int id, @RequestBody AssistantDTO assistantDTO) {
        ResponseDTO response = assistantService.update(id, assistantDTO);
        if (response.getStatus().equals(HttpStatus.OK.toString())) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
}
