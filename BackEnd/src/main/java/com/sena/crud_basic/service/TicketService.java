package com.sena.crud_basic.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.sena.crud_basic.DTO.TicketDTO;
import com.sena.crud_basic.model.Ticket;
import com.sena.crud_basic.model.Events;
import com.sena.crud_basic.model.Assistant;
import com.sena.crud_basic.repository.TicketRepository;
import com.sena.crud_basic.repository.IEvent;
import com.sena.crud_basic.repository.IAssistant;

@Service
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private IEvent eventRepository;

    @Autowired
    private IAssistant assistantRepository;

    public void save(TicketDTO ticketDTO) {
        Ticket ticket = convertToModel(ticketDTO);
        ticketRepository.save(ticket);
    }

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

    public Ticket convertToModel(TicketDTO ticketDTO) {
        Events event = eventRepository.findById(ticketDTO.getEventId())
        .orElseThrow(() -> new RuntimeException("Event not found with id: " + ticketDTO.getEventId()));

        Assistant assistant = assistantRepository.findById(ticketDTO.getAssistantId())
        .orElseThrow(() -> new RuntimeException("Assistant not found with id: " + ticketDTO.getAssistantId()));

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
