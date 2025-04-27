package com.sena.crud_basic.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.sena.crud_basic.DTO.EventOrganizerDTO;
import com.sena.crud_basic.DTO.ResponseDTO;
import com.sena.crud_basic.service.EventOrganizerService;

@RestController
@RequestMapping("/api/v1/event-organizer")
public class EventOrganizerController {

    @Autowired
    private EventOrganizerService service;

    // Save EventOrganizer
    @PostMapping("/")
    public ResponseEntity<ResponseDTO> save(@RequestBody EventOrganizerDTO dto) {
        ResponseDTO resp = service.save(dto);

        // Mapear explícitamente el estado HTTP
        HttpStatus status = mapStatus(resp.getStatus());
        return ResponseEntity.status(status).body(resp);
    }

    // Get all EventOrganizers
    @GetMapping("/")
    public ResponseEntity<List<EventOrganizerDTO>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    // Get EventOrganizer by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable int id) {
        Optional<EventOrganizerDTO> eo = service.findById(id);
        return eo
            .<ResponseEntity<?>>map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Delete EventOrganizer by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO> delete(@PathVariable int id) {
        ResponseDTO resp = service.deleteById(id);

        // Mapear explícitamente el estado HTTP
        HttpStatus status = mapStatus(resp.getStatus());
        return ResponseEntity.status(status).body(resp);
    }

    // Get organizers by event name
    @GetMapping("/by-event/{name}")
    public ResponseEntity<?> byEvent(@PathVariable String name) {
        List<EventOrganizerDTO> organizers = service.findOrganizersByEventName(name);
        if (organizers.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("No organizers found for event: " + name);
        }
        return ResponseEntity.ok(organizers);
    }

    // Get events by organizer name
    @GetMapping("/by-organizer/{name}")
    public ResponseEntity<?> byOrganizer(@PathVariable String name) {
        List<EventOrganizerDTO> events = service.findEventsByOrganizerName(name);
        if (events.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("No events found for organizer: " + name);
        }
        return ResponseEntity.ok(events);
    }

    // Método auxiliar para mapear el estado devuelto por el servicio a un código HTTP
    private HttpStatus mapStatus(String status) {
        if (HttpStatus.OK.toString().equalsIgnoreCase(status)) {
            return HttpStatus.OK;
        } else if (HttpStatus.BAD_REQUEST.toString().equalsIgnoreCase(status)) {
            return HttpStatus.BAD_REQUEST;
        } else if (HttpStatus.NOT_FOUND.toString().equalsIgnoreCase(status)) {
            return HttpStatus.NOT_FOUND;
        } else {
            // Por defecto, devolver un error interno del servidor
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }
}