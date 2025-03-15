package com.sena.crud_basic.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.sena.crud_basic.DTO.TicketDTO;
import com.sena.crud_basic.DTO.ResponseDTO;
import com.sena.crud_basic.model.Ticket;
import com.sena.crud_basic.service.TicketService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/tickets")
@CrossOrigin(origins = "*") // Permite peticiones desde cualquier origen
public class TicketController {

    @Autowired
    private TicketService ticketService;

    // ✅ Endpoint para crear un ticket
    @PostMapping
    public ResponseEntity<ResponseDTO> createTicket(@RequestBody TicketDTO ticketDTO) {
        ResponseDTO response = ticketService.save(ticketDTO);
        return ResponseEntity.ok(response);
    }

    // ✅ Endpoint para obtener todos los tickets
    @GetMapping
    public ResponseEntity<List<TicketDTO>> getAllTickets() {
        List<TicketDTO> ticketDTOList = ticketService.findAll()
                .stream()
                .map(ticketService::convertToDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(ticketDTOList);
    }

    // ✅ Endpoint para obtener un ticket por ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getTicketById(@PathVariable int id) {
        Optional<Ticket> ticketOpt = ticketService.findById(id);
        if (ticketOpt.isPresent()) {
            return ResponseEntity.ok(ticketService.convertToDTO(ticketOpt.get()));
        }
        return ResponseEntity.badRequest().body(new ResponseDTO("400", "El ticket no existe"));
    }

    // ✅ Endpoint para eliminar un ticket por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO> deleteTicket(@PathVariable int id) {
        ResponseDTO response = ticketService.deleteTicket(id);
        return ResponseEntity.ok(response);
    }
}
