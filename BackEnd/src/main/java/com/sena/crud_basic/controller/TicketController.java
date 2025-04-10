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
import com.sena.crud_basic.Resource.RateLimiterService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/tickets")
@CrossOrigin(origins = "*")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @Autowired
    private RateLimiterService rateLimiter;

    // ✅ Método para verificar límite
    private boolean isRateLimited() {
        return !rateLimiter.tryConsume();
    }

    // ✅ Endpoint para crear un ticket
    @PostMapping("/")
    public ResponseEntity<ResponseDTO> createTicket(@RequestBody TicketDTO ticketDTO) {
        if (isRateLimited()) {
            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS)
                    .body(new ResponseDTO("429", "🚫 Límite de peticiones alcanzado"));
        }

        ResponseDTO response = ticketService.save(ticketDTO);
        return ResponseEntity.ok(response);
    }

    // ✅ Endpoint para obtener todos los tickets
    @GetMapping("/")
    public ResponseEntity<?> getAllTickets() {
        if (isRateLimited()) {
            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS)
                    .body(new ResponseDTO("429", "🚫 Límite de peticiones alcanzado"));
        }

        List<TicketDTO> ticketDTOList = ticketService.findAll()
                .stream()
                .map(ticketService::convertToDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(ticketDTOList);
    }

    // ✅ Endpoint para obtener un ticket por ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getTicketById(@PathVariable int id) {
        if (isRateLimited()) {
            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS)
                    .body(new ResponseDTO("429", "🚫 Límite de peticiones alcanzado"));
        }

        Optional<Ticket> ticketOpt = ticketService.findById(id);
        if (ticketOpt.isPresent()) {
            return ResponseEntity.ok(ticketService.convertToDTO(ticketOpt.get()));
        }
        return ResponseEntity.badRequest().body(new ResponseDTO("400", "El ticket no existe"));
    }

    // ✅ Endpoint para eliminar un ticket por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO> deleteTicket(@PathVariable int id) {
        if (isRateLimited()) {
            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS)
                    .body(new ResponseDTO("429", "🚫 Límite de peticiones alcanzado"));
        }

        ResponseDTO response = ticketService.deleteTicket(id);
        return ResponseEntity.ok(response);
    }

    // ✅ Endpoint para filtrar tickets
    @GetMapping("/filter")
    public ResponseEntity<?> filterTickets(
            @RequestParam(required = false, name = "idTicket") Integer idTicket,
            @RequestParam(required = false, name = "eventId") Integer eventId,
            @RequestParam(required = false, name = "assistantId") Integer assistantId,
            @RequestParam(required = false, name = "price") Double price,
            @RequestParam(required = false, name = "seatNumber") String seatNumber,
            @RequestParam(required = false, name = "status") Boolean status,
            @RequestParam(required = false, name = "fromDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fromDate,
            @RequestParam(required = false, name = "toDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime toDate) {

        if (isRateLimited()) {
            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS)
                    .body(new ResponseDTO("429", "🚫 Límite de peticiones alcanzado"));
        }

        List<Ticket> tickets = ticketService.filterTickets(idTicket, eventId, assistantId, price, seatNumber, status, fromDate, toDate);
        return new ResponseEntity<>(tickets, HttpStatus.OK);
    }

    // ✅ Endpoint para actualizar un ticket
    @PutMapping("/{id}")
    public ResponseEntity<ResponseDTO> updateTicket(@PathVariable int id, @RequestBody TicketDTO ticketDTO) {
        if (isRateLimited()) {
            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS)
                    .body(new ResponseDTO("429", "🚫 Límite de peticiones alcanzado"));
        }

        ResponseDTO response = ticketService.update(id, ticketDTO);
        if (response.getStatus().equals(HttpStatus.OK.toString())) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.badRequest().body(response);
        }
    }
}

