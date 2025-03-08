package com.sena.crud_basic.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.sena.crud_basic.DTO.TicketDTO;
import com.sena.crud_basic.service.TicketService;

@RestController
@RequestMapping("/api/v1/tickets")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    // Registrar un nuevo ticket
    @PostMapping("/")
    public ResponseEntity<String> createTicket(@RequestBody TicketDTO ticketDTO) {
        ticketService.save(ticketDTO);
        return new ResponseEntity<>("Ticket created successfully", HttpStatus.CREATED);
    }
}
