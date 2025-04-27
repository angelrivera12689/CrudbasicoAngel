package com.sena.crud_basic.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.sena.crud_basic.DTO.EventAssistantDTO;
import com.sena.crud_basic.DTO.ResponseDTO;
import com.sena.crud_basic.service.EventAssistantService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/event-assistant")
public class EventAssistantController {

    @Autowired
    private EventAssistantService eventAssistantService;

    /**
     * Registrar una nueva relación entre evento y asistente.
     *
     * @param eventAssistantDTO Datos para registrar la relación.
     * @return Respuesta con el estado de la operación.
     */
    @PostMapping("/")
    public ResponseEntity<Object> registerEventAssistant(@Validated @RequestBody EventAssistantDTO eventAssistantDTO) {
        ResponseDTO response = eventAssistantService.save(eventAssistantDTO);
        if (response.getStatus().equals(HttpStatus.OK.toString())) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * Consultar todas las relaciones evento-asistente.
     *
     * @return Lista de todas las relaciones.
     */
    @GetMapping("/")
    public ResponseEntity<Object> getAllEventAssistants() {
        return new ResponseEntity<>(eventAssistantService.findAll(), HttpStatus.OK);
    }

    /**
     * Consultar una relación evento-asistente por ID.
     *
     * @param id ID de la relación a buscar.
     * @return Relación encontrada o mensaje de error.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Object> getEventAssistantById(@PathVariable int id) {
        var eventAssistant = eventAssistantService.findById(id);
        if (!eventAssistant.isPresent()) {
            return new ResponseEntity<>("Registro no encontrado", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(eventAssistant.get(), HttpStatus.OK);
    }

    /**
     * Eliminar una relación evento-asistente por ID.
     *
     * @param id ID de la relación a eliminar.
     * @return Respuesta con el estado de la operación.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteEventAssistant(@PathVariable int id) {
        ResponseDTO response = eventAssistantService.deleteById(id);
        if (response.getStatus().equals(HttpStatus.OK.toString())) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    /**
     * Consultar eventos por nombre de asistente.
     *
     * @param name Nombre del asistente.
     * @return Lista de eventos relacionados con el asistente.
     */
    @GetMapping("/assistant/{name}")
    public ResponseEntity<Object> getEventsByAssistantName(@PathVariable String name) {
        List<EventAssistantDTO> events = eventAssistantService.findEventsByAssistantName(name);
        if (events.isEmpty()) {
            return new ResponseEntity<>("No se encontraron eventos para el asistente con el nombre: " + name, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(events, HttpStatus.OK);
    }

    /**
     * Consultar asistentes por nombre de evento.
     *
     * @param name Nombre del evento.
     * @return Lista de asistentes relacionados con el evento.
     */
    @GetMapping("/event/{name}")
    public ResponseEntity<Object> getAssistantsByEventName(@PathVariable String name) {
        List<EventAssistantDTO> assistants = eventAssistantService.findAssistantsByEventName(name);
        if (assistants.isEmpty()) {
            return new ResponseEntity<>("No se encontraron asistentes para el evento con el nombre: " + name, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(assistants, HttpStatus.OK);
    }
}
