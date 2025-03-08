package com.sena.crud_basic.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.sena.crud_basic.DTO.employeeDTO;
import com.sena.crud_basic.service.employeeService;

@RestController
@RequestMapping("/api/v1/employee")
public class employeeController {

    @Autowired
    private employeeService employeeService;

    // Registrar un nuevo empleado
    @PostMapping("/")
    public ResponseEntity<String> createEmployee(@RequestBody employeeDTO employee) {
        employeeService.save(employee);
        return new ResponseEntity<>("Employee created successfully", HttpStatus.CREATED);
    }
}
