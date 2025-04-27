package com.sena.crud_basic.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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
        return ResponseEntity
                .status(resp.getStatus().equals("OK") ? 200 : 400)
                .body(resp);
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
        return ResponseEntity
                .status(resp.getStatus().equals("OK") ? 200 : 404)
                .body(resp);
    }

    // Get organizers by event name
    @GetMapping("/by-event/{name}")
    public ResponseEntity<?> byEvent(@PathVariable String name) {
        List<EventOrganizerDTO> organizers = service.findOrganizersByEventName(name);
        if (organizers.isEmpty()) {
            return ResponseEntity
                    .status(404)
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
                    .status(404)
                    .body("No events found for organizer: " + name);
        }
        return ResponseEntity.ok(events);
    }
}
