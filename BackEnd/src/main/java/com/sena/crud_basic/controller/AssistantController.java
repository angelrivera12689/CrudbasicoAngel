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
import com.sena.crud_basic.Resource.RateLimiterService;

@RestController
@RequestMapping("/api/v1/assistants")
public class AssistantController {

    @Autowired
    private AssistantService assistantService;

    @Autowired
    private RateLimiterService rateLimiter;

    // Método común para verificar el límite de peticiones
    private boolean isRateLimited() {
        return !rateLimiter.tryConsume();
    }

    // ✅ Registrar un nuevo asistente
    @PostMapping("/")
    public ResponseEntity<Object> registerAssistant(@RequestBody AssistantDTO assistantDTO) {
        if (isRateLimited()) {
            return new ResponseEntity<>(new ResponseDTO("429", "🚫 Límite de peticiones alcanzado"), HttpStatus.TOO_MANY_REQUESTS);
        }

        ResponseDTO respuesta = assistantService.save(assistantDTO);
        return new ResponseEntity<>(respuesta,
                respuesta.getStatus().equals(HttpStatus.OK.toString()) ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }

    // ✅ Consultar todos los asistentes
    @GetMapping("/")
    public ResponseEntity<Object> getAllAssistants() {
        if (isRateLimited()) {
            return new ResponseEntity<>(new ResponseDTO("429", "🚫 Límite de peticiones alcanzado"), HttpStatus.TOO_MANY_REQUESTS);
        }

        return new ResponseEntity<>(assistantService.findAll(), HttpStatus.OK);
    }

    // ✅ Consultar un asistente por ID
    @GetMapping("/{id}")
    public ResponseEntity<Object> getAssistantById(@PathVariable int id) {
        if (isRateLimited()) {
            return new ResponseEntity<>(new ResponseDTO("429", "🚫 Límite de peticiones alcanzado"), HttpStatus.TOO_MANY_REQUESTS);
        }

        var assistant = assistantService.findById(id);
        return assistant.isPresent()
                ? new ResponseEntity<>(assistant.get(), HttpStatus.OK)
                : new ResponseEntity<>("Asistente no encontrado", HttpStatus.NOT_FOUND);
    }

    // ✅ Eliminar un asistente
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteAssistant(@PathVariable int id) {
        if (isRateLimited()) {
            return new ResponseEntity<>(new ResponseDTO("429", "🚫 Límite de peticiones alcanzado"), HttpStatus.TOO_MANY_REQUESTS);
        }

        ResponseDTO message = assistantService.delete(id);
        return new ResponseEntity<>(message,
                message.getStatus().equals(HttpStatus.OK.toString()) ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }

    // ✅ Filtrar asistentes
    @GetMapping("/filter")
    public ResponseEntity<List<Assistant>> filterAssistants(
            @RequestParam(required = false) Integer id,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String phone,
            @RequestParam(required = false) Boolean status) {

        if (isRateLimited()) {
            return new ResponseEntity<>(null, HttpStatus.TOO_MANY_REQUESTS);
        }

        List<Assistant> results = assistantService.filterAssistants(id, name, email, phone, status);
        return new ResponseEntity<>(results, HttpStatus.OK);
    }

    // ✅ Actualizar un asistente
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateAssistant(@PathVariable int id, @RequestBody AssistantDTO assistantDTO) {
        if (isRateLimited()) {
            return new ResponseEntity<>(new ResponseDTO("429", "🚫 Límite de peticiones alcanzado"), HttpStatus.TOO_MANY_REQUESTS);
        }

        ResponseDTO response = assistantService.update(id, assistantDTO);
        return new ResponseEntity<>(response,
                response.getStatus().equals(HttpStatus.OK.toString()) ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }
}
