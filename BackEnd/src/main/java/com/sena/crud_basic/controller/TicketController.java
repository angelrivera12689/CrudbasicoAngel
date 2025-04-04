package com.sena.crud_basic.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.sena.crud_basic.DTO.TicketDTO;
import com.sena.crud_basic.DTO.ResponseDTO;
import com.sena.crud_basic.model.Ticket;
import com.sena.crud_basic.service.TicketService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/tickets")
@CrossOrigin(origins = "*") // Permite peticiones desde cualquier origen
public class TicketController {

    @Autowired
    private TicketService ticketService;

    // ✅ Endpoint para crear un ticket
    @PostMapping("/")
    public ResponseEntity<ResponseDTO> createTicket(@RequestBody TicketDTO ticketDTO) {
        ResponseDTO response = ticketService.save(ticketDTO);
        return ResponseEntity.ok(response);
    }

    // ✅ Endpoint para obtener todos los tickets
    @GetMapping("/")
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

     @GetMapping("/filter")
    public ResponseEntity<Object> filterTickets(
            @RequestParam(required = false, name = "eventId") Integer eventId,
            @RequestParam(required = false, name = "assistantId") Integer assistantId,
            @RequestParam(required = false, name = "price") Double price,
            @RequestParam(required = false, name = "seatNumber") String seatNumber,
            @RequestParam(required = false, name = "status") Boolean status,
            @RequestParam(required = false, name = "fromDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fromDate,
            @RequestParam(required = false, name = "toDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime toDate) {

        List<Ticket> tickets = ticketService.filterTickets(eventId, assistantId, price, seatNumber, status, fromDate, toDate);
        return new ResponseEntity<>(tickets, HttpStatus.OK);
    }
    @PutMapping("/{id}")
    public ResponseEntity<ResponseDTO> updateTicket(@PathVariable int id, @RequestBody TicketDTO ticketDTO) {
        ResponseDTO response = ticketService.update(id, ticketDTO);
        if (response.getStatus().equals(HttpStatus.OK.toString())) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.badRequest().body(response);
        }
    }
}

