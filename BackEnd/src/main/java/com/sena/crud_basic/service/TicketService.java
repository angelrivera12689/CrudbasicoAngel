package com.sena.crud_basic.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.sena.crud_basic.DTO.ResponseDTO;
import com.sena.crud_basic.DTO.TicketDTO;
import com.sena.crud_basic.model.Ticket;
import com.sena.crud_basic.model.Events;
import com.sena.crud_basic.model.Assistant;
import com.sena.crud_basic.repository.TicketRepository;
import com.sena.crud_basic.repository.IEvent;
import com.sena.crud_basic.repository.IAssistant;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private IEvent eventRepository;

    @Autowired
    private IAssistant assistantRepository;

    // ✅ Método para registrar un ticket con validaciones
    public ResponseDTO save(TicketDTO ticketDTO) {
        // Validar que el precio sea positivo
        if (ticketDTO.getPrice() <= 0) {
            return new ResponseDTO(
                HttpStatus.BAD_REQUEST.toString(),
                "El precio del ticket debe ser mayor a 0"
            );
        }
    
        // Validar que el número de asiento no esté vacío
        if (ticketDTO.getSeatNumber() == null || ticketDTO.getSeatNumber().trim().isEmpty()) {
            return new ResponseDTO(
                HttpStatus.BAD_REQUEST.toString(),
                "El número de asiento es obligatorio"
            );
        }
    
        // Buscar el evento y el asistente
        Events event = eventRepository.findById(ticketDTO.getEventId())
                .orElseThrow(() -> new RuntimeException("Evento no encontrado con ID: " + ticketDTO.getEventId()));
    
        Assistant assistant = assistantRepository.findById(ticketDTO.getAssistantId())
                .orElseThrow(() -> new RuntimeException("Asistente no encontrado con ID: " + ticketDTO.getAssistantId()));
    
        // Manejo automático de la fecha de compra y estado
        LocalDateTime purchaseDate = ticketDTO.getPurchaseDate() != null
                ? ticketDTO.getPurchaseDate()  // Si viene en el DTO, lo usamos
                : LocalDateTime.now();         // Si no viene, usamos la fecha y hora actuales
    
        boolean status = ticketDTO.getStatus()
                ? ticketDTO.getStatus()        // Si viene en el DTO, lo usamos
                : true;                        // Si no viene, usamos el valor predeterminado
    
        // Convertir el DTO a modelo y guardar
        Ticket ticket = new Ticket(
            event,
            assistant,
            ticketDTO.getPrice(),
            ticketDTO.getSeatNumber(),
            purchaseDate,
            status
        );
    
        ticketRepository.save(ticket);
    
        return new ResponseDTO(
            HttpStatus.OK.toString(),
            "Ticket guardado exitosamente"
        );
    }
    

    // ✅ Método para obtener todos los tickets
    public List<Ticket> findAll() {
        return ticketRepository.getListTicketActive();
    }

    // ✅ Método para buscar un ticket por ID
    public Optional<Ticket> findById(int id) {
        return ticketRepository.findById(id);
    }


     // ✅ Método para eliminar una reseña por ID
     public ResponseDTO deleteTicket(int id) {
        // Buscar el ticket por ID
        Optional<Ticket> ticket = findById(id);
        if (!ticket.isPresent()) {
            // Si no se encuentra el ticket, devolvemos una respuesta de error
            return new ResponseDTO(
                HttpStatus.BAD_REQUEST.toString(),
                "El registro no existe"
            );
        }
    
        // Cambiar el estado del ticket a false (eliminación lógica)
        ticket.get().setStatus(false);
        ticketRepository.save(ticket.get()); // Usamos el repositorio correctamente
    
        // Devolvemos una respuesta de éxito
        return new ResponseDTO(
            HttpStatus.OK.toString(),
            "Ticket eliminado correctamente"
        );
    
    }

    public ResponseDTO update(int id, TicketDTO ticketDTO) {
        Optional<Ticket> ticketOpt = ticketRepository.findById(id);
        if (!ticketOpt.isPresent()) {
            return new ResponseDTO(HttpStatus.BAD_REQUEST.toString(), "El ticket no existe");
        }
    
        Ticket ticket = ticketOpt.get();
        ticket.setPrice(ticketDTO.getPrice());
        ticket.setSeatNumber(ticketDTO.getSeatNumber());
        ticket.setStatus(ticketDTO.getStatus());
    
        // Establecer automáticamente la fecha de compra con la fecha actual
        ticket.setPurchaseDate(LocalDateTime.now());
    
        ticketRepository.save(ticket);
        return new ResponseDTO(HttpStatus.OK.toString(), "Ticket actualizado exitosamente");
    }


    public List<Ticket> filterTickets(
        Integer idTicket,       // Agregamos el filtro por idTicket
        Integer eventId,
        Integer assistantId,
        Double price,
        String seatNumber,
        Boolean status,
        LocalDateTime fromDate,
        LocalDateTime toDate
    ) {
        // Llamamos al repositorio pasando los parámetros
        return ticketRepository.filterTickets(idTicket, eventId, assistantId, price, seatNumber, status, fromDate, toDate);
    }
    
    // ✅ Método para convertir un Ticket a TicketDTO
    public TicketDTO convertToDTO(Ticket ticket) {
        return new TicketDTO(
                ticket.getIdTicket(),
                ticket.getEvent().getIdEvent(),
                ticket.getAssistant().getId(),
                ticket.getPrice(),
                ticket.getSeatNumber(),
                ticket.getPurchaseDate(),
                ticket.getStatus()
        );
    }
 // ✅ Método para convertir un TicketDTO a Ticket
    public Ticket convertToModel(TicketDTO ticketDTO) {
        // Buscar el evento y el asistente en la base de datos
        Events event = eventRepository.findById(ticketDTO.getEventId())
                .orElseThrow(() -> new RuntimeException("Evento no encontrado con ID: " + ticketDTO.getEventId()));
    
        Assistant assistant = assistantRepository.findById(ticketDTO.getAssistantId())
                .orElseThrow(() -> new RuntimeException("Asistente no encontrado con ID: " + ticketDTO.getAssistantId()));
    
        // Crear y retornar el objeto Ticket
        return new Ticket(
                event,
                assistant,
                ticketDTO.getPrice(),
                ticketDTO.getSeatNumber(),
                ticketDTO.getPurchaseDate(),
                ticketDTO.getStatus()
        );
    }
    
    
}
