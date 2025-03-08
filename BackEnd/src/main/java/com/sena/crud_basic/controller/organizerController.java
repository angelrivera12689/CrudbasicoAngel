package com.sena.crud_basic.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.sena.crud_basic.DTO.organizerDTO;
import com.sena.crud_basic.service.organizerService;

@RestController
@RequestMapping("/api/v1/organizer")
public class organizerController {

    @Autowired
    private organizerService organizerService;

    @PostMapping("/")
    public ResponseEntity<Object> registerOrganizer(@RequestBody organizerDTO organizer) {
        organizerService.save(organizer);
        return new ResponseEntity<>("Organizer registered successfully", HttpStatus.CREATED);
    }
}
