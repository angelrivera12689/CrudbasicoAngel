package com.sena.crud_basic.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.sena.crud_basic.DTO.EventsDTO;
import com.sena.crud_basic.DTO.ResponseDTO;
import com.sena.crud_basic.model.Events;
import com.sena.crud_basic.service.EventService;
import com.sena.crud_basic.Resource.RateLimiterService;

@RestController
@RequestMapping("/api/v1/events")
public class EventController {

    @Autowired
    private EventService eventService;

    @Autowired
    private RateLimiterService rateLimiter;

    // ✅ Método para verificar límite
    private boolean isRateLimited() {
        return !rateLimiter.tryConsume();
    }

    // ✅ Crear un nuevo evento
    @PostMapping("/")
    public ResponseEntity<Object> registerEvent(@RequestBody EventsDTO eventDTO) {
        if (isRateLimited()) {
            return new ResponseEntity<>(new ResponseDTO("429", "🚫 Límite de peticiones alcanzado"), HttpStatus.TOO_MANY_REQUESTS);
        }

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
        if (isRateLimited()) {
            return new ResponseEntity<>(new ResponseDTO("429", "🚫 Límite de peticiones alcanzado"), HttpStatus.TOO_MANY_REQUESTS);
        }

        return new ResponseEntity<>(eventService.findAll(), HttpStatus.OK);
    }

    // ✅ Obtener un evento por su ID
    @GetMapping("/{id}")
    public ResponseEntity<Object> getEventById(@PathVariable int id) {
        if (isRateLimited()) {
            return new ResponseEntity<>(new ResponseDTO("429", "🚫 Límite de peticiones alcanzado"), HttpStatus.TOO_MANY_REQUESTS);
        }

        var event = eventService.findById(id);
        if (!event.isPresent()) {
            return new ResponseEntity<>("Evento no encontrado", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(event.get(), HttpStatus.OK);
    }

    // ✅ Eliminar un evento por su ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteEvent(@PathVariable int id) {
        if (isRateLimited()) {
            return new ResponseEntity<>(new ResponseDTO("429", "🚫 Límite de peticiones alcanzado"), HttpStatus.TOO_MANY_REQUESTS);
        }

        ResponseDTO response = eventService.deleteEvent(id);
        if (response.getStatus().equals(HttpStatus.OK.toString())) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    // ✅ Filtrar eventos
    @GetMapping("/filter")
    public ResponseEntity<List<Events>> filterEvents(
            @RequestParam(required = false) String event_name,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) LocalDate date,
            @RequestParam(required = false) String location,
            @RequestParam(required = false) String category_name) {
        
        if (isRateLimited()) {
            return new ResponseEntity<>( HttpStatus.TOO_MANY_REQUESTS);
        }
    
        List<Events> filteredEvents = eventService.filterEvent(event_name, description, date, location, category_name);
        return ResponseEntity.ok(filteredEvents);
    }
    
    // ✅ Actualizar evento
    @PutMapping("/{id}")
    public ResponseEntity<ResponseDTO> update(@PathVariable int id, @RequestBody EventsDTO eventDTO) {
        if (isRateLimited()) {
            return new ResponseEntity<>(new ResponseDTO("429", "🚫 Límite de peticiones alcanzado"), HttpStatus.TOO_MANY_REQUESTS);
        }

        ResponseDTO response = eventService.update(id, eventDTO);
        return ResponseEntity
                .status(response.getStatus().equals(HttpStatus.OK.toString()) ? HttpStatus.OK : HttpStatus.BAD_REQUEST)
                .body(response);
    }
}