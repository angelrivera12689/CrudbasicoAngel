package com.sena.crud_basic.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.sena.crud_basic.DTO.EventEmployeeDTO;

import com.sena.crud_basic.service.EventEmployeeService;

@RestController
@RequestMapping("/api/v1/event-employees")
public class EventEmployeeController {

    @Autowired
    private EventEmployeeService eventEmployeeService;

    // Registrar una nueva relación entre evento y empleado
    @PostMapping("/")
    public ResponseEntity<String> createEventEmployee(@RequestBody EventEmployeeDTO eventEmployee) {
        eventEmployeeService.save(eventEmployee);
        return new ResponseEntity<>("Event-Employee association created successfully", HttpStatus.CREATED);
    }
}
