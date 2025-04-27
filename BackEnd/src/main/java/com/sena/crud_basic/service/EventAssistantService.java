package com.sena.crud_basic.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.sena.crud_basic.DTO.EventAssistantDTO;
import com.sena.crud_basic.DTO.ResponseDTO;
import com.sena.crud_basic.model.EventAssistant;
import com.sena.crud_basic.model.Events;
import com.sena.crud_basic.model.Assistant;
import com.sena.crud_basic.repository.IEventAssistant;
import com.sena.crud_basic.repository.IEvent;
import com.sena.crud_basic.repository.IAssistant;

@Service
public class EventAssistantService {

    @Autowired
    private IEventAssistant eventAssistantRepository;

    @Autowired
    private IEvent eventsRepository;

    @Autowired
    private IAssistant assistantRepository;

    // Método para registrar la relación evento-asistente
    public ResponseDTO save(EventAssistantDTO eventAssistantDTO) {
        // Verificar que el evento y el asistente existen
        Optional<Events> event = eventsRepository.findById(eventAssistantDTO.getEvent().getIdEvent());
        Optional<Assistant> assistant = assistantRepository.findById(eventAssistantDTO.getAssistant().getId());

        if (!event.isPresent() || !assistant.isPresent()) {
            return new ResponseDTO(
                HttpStatus.BAD_REQUEST.toString(),
                "Evento o Asistente no encontrados"
            );
        }

        // Convertir el DTO al modelo
        EventAssistant eventAssistant = convertToModel(eventAssistantDTO);
        eventAssistant.setEvent(event.get());
        eventAssistant.setAssistant(assistant.get());
        eventAssistantRepository.save(eventAssistant);

        return new ResponseDTO(HttpStatus.OK.toString(), "Registro guardado exitosamente");
    }

    // Método para obtener todas las relaciones evento-asistente
    public List<EventAssistant> findAll() {
        return eventAssistantRepository.findAll();
    }

    // Método para obtener una relación por ID
    public Optional<EventAssistant> findById(int id) {
        return eventAssistantRepository.findById(id);
    }

    // Método para eliminar una relación evento-asistente por ID
    public ResponseDTO deleteById(int id) {
        Optional<EventAssistant> eventAssistant = findById(id);
        if (!eventAssistant.isPresent()) {
            return new ResponseDTO(HttpStatus.NOT_FOUND.toString(), "Registro no encontrado");
        }

        eventAssistantRepository.deleteById(id);
        return new ResponseDTO(HttpStatus.OK.toString(), "Registro eliminado exitosamente");
    }

    // Método para convertir un modelo a un DTO
    public EventAssistantDTO convertToDTO(EventAssistant eventAssistant) {
        return new EventAssistantDTO(
                eventAssistant.getId_event_assistant(),
                eventAssistant.getEvent(),
                eventAssistant.getAssistant()
        );
    }

    // Método para convertir un DTO a un modelo
    public EventAssistant convertToModel(EventAssistantDTO eventAssistantDTO) {
        // Validar que el evento y el asistente no sean nulos
        if (eventAssistantDTO.getEvent() == null || eventAssistantDTO.getAssistant() == null) {
            throw new IllegalArgumentException("Evento o Asistente no pueden ser nulos");
        }

        return new EventAssistant(
                eventAssistantDTO.getEvent(),
                eventAssistantDTO.getAssistant(),
                true
        );
    }

    // Filtro para obtener eventos por nombre de asistente
    public List<EventAssistantDTO> findEventsByAssistantName(String name) {
        List<EventAssistant> eventAssistants = eventAssistantRepository.findEventsByAssistantName(name);
        return convertToDTOList(eventAssistants);
    }

    // Filtro para obtener asistentes por nombre de evento
    public List<EventAssistantDTO> findAssistantsByEventName(String name) {
        List<EventAssistant> eventAssistants = eventAssistantRepository.findAssistantsByEventName(name);
        return convertToDTOList(eventAssistants);
    }

    // Método auxiliar para convertir una lista de modelos a una lista de DTOs
    private List<EventAssistantDTO> convertToDTOList(List<EventAssistant> eventAssistants) {
        return eventAssistants.stream()
                .map(eventAssistant -> new EventAssistantDTO(
                        eventAssistant.getId_event_assistant(),
                        eventAssistant.getEvent(),
                        eventAssistant.getAssistant()))
                .toList();
    }
}
