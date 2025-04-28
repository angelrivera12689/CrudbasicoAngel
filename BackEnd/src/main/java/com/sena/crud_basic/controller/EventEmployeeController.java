package com.sena.crud_basic.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.sena.crud_basic.DTO.EventEmployeeDTO;
import com.sena.crud_basic.service.EventEmployeeService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/event-employees")
public class EventEmployeeController {

    @Autowired
    private EventEmployeeService eventEmployeeService;

    // Registrar una nueva relación entre evento y empleado
    @PostMapping("/")
    public ResponseEntity<String> createEventEmployee(@RequestBody EventEmployeeDTO eventEmployee) {
        eventEmployeeService.save(eventEmployee);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body("Event-Employee association created successfully");
    }

    // Obtener empleados por nombre del evento
    @GetMapping("/by-event/{eventName}")
    public ResponseEntity<?> getEmployeesByEventName(@PathVariable String eventName) {
        List<EventEmployeeDTO> employees = eventEmployeeService.getEmployeesByEventName(eventName);
        if (employees.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("No employees found for event: " + eventName);
        }
        return ResponseEntity.ok(employees);
    }

    // Obtener eventos por nombre del empleado
    @GetMapping("/by-employee/{employeeName}")
    public ResponseEntity<?> getEventsByEmployeeName(@PathVariable String employeeName) {
        List<EventEmployeeDTO> events = eventEmployeeService.getEventsByEmployeeName(employeeName);
        if (events.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("No events found for employee: " + employeeName);
        }
        return ResponseEntity.ok(events);
    }
}
