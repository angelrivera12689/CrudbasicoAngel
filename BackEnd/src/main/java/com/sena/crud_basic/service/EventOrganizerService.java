package com.sena.crud_basic.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.sena.crud_basic.DTO.EventOrganizerDTO;
import com.sena.crud_basic.DTO.ResponseDTO;
import com.sena.crud_basic.model.EventOrganizer;
import com.sena.crud_basic.model.Events;
import com.sena.crud_basic.model.organizer;
import com.sena.crud_basic.repository.IEvent;
import com.sena.crud_basic.repository.IEventOrganizer;
import com.sena.crud_basic.repository.Iorganizer;

@Service
public class EventOrganizerService {

    @Autowired
    private IEventOrganizer eventOrganizerRepository;

    @Autowired
    private IEvent eventsRepository;

    @Autowired
    private Iorganizer organizerRepository;

    /**
     * Registrar una nueva relación evento-organizador.
     */
    public ResponseDTO save(EventOrganizerDTO dto) {
        // Buscar el evento por su ID
        Optional<Events> ev = eventsRepository.findById(dto.getEvent().getIdEvent());
        // NOTA: aquí usamos el getter que tu clase 'organizer' ya genera: getId_organizer()
        Optional<organizer> org = organizerRepository.findById(dto.getOrganizer().getId_organizer());

        if (!ev.isPresent() || !org.isPresent()) {
            return new ResponseDTO(
                HttpStatus.BAD_REQUEST.toString(),
                "Evento o Organizador no encontrados"
            );
        }

        EventOrganizer eo = convertToModel(dto);
        eo.setEvent(ev.get());
        eo.setOrganizer(org.get());
        eo.setStatus(true);
        eventOrganizerRepository.save(eo);

        return new ResponseDTO(
            HttpStatus.OK.toString(),
            "Relación registrada exitosamente"
        );
    }

    /**
     * Obtener todas las relaciones evento-organizador.
     */
    public List<EventOrganizerDTO> findAll() {
        return eventOrganizerRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Obtener una relación por ID.
     */
    public Optional<EventOrganizerDTO> findById(int id) {
        return eventOrganizerRepository.findById(id)
                .map(this::convertToDTO);
    }

    /**
     * Eliminar una relación por ID.
     */
    public ResponseDTO deleteById(int id) {
        if (!eventOrganizerRepository.existsById(id)) {
            return new ResponseDTO(
                HttpStatus.NOT_FOUND.toString(),
                "Registro no encontrado"
            );
        }
        eventOrganizerRepository.deleteById(id);
        return new ResponseDTO(
            HttpStatus.OK.toString(),
            "Relación eliminada exitosamente"
        );
    }

    /**
     * Filtrar organizadores por nombre de evento.
     */
    public List<EventOrganizerDTO> findOrganizersByEventName(String name) {
        return eventOrganizerRepository.findOrganizersByEventName(name)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Filtrar eventos por nombre de organizador.
     */
    public List<EventOrganizerDTO> findEventsByOrganizerName(String name) {
        return eventOrganizerRepository.findEventsByOrganizerName(name)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Convierte entidad a DTO.
     */
    public EventOrganizerDTO convertToDTO(EventOrganizer eo) {
        return new EventOrganizerDTO(
            eo.getId_event_organizer(),
            eo.getEvent(),
            eo.getOrganizer(),
            eo.getStatus()
        );
    }

    /**
     * Convierte DTO a entidad.
     */
    public EventOrganizer convertToModel(EventOrganizerDTO dto) {
        if (dto.getEvent() == null || dto.getOrganizer() == null) {
            throw new IllegalArgumentException("Evento y Organizador no pueden ser nulos");
        }
        return new EventOrganizer(
            dto.getEvent(),
            dto.getOrganizer(),
            dto.isStatus()
        );
    }
}
