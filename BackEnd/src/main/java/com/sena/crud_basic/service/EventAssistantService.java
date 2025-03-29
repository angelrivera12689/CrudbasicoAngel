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

@Service
public class EventAssistantService {

    @Autowired
    private IEventAssistant data;

     // Método para registrar la relación evento-asistente
    public ResponseDTO save(EventAssistantDTO eventAssistantDTO) {
        EventAssistant eventAssistant = convertToModel(eventAssistantDTO);
        data.save(eventAssistant);
        return new ResponseDTO(HttpStatus.OK.toString(), "Registro guardado exitosamente");
    }

    // Método para obtener todas las relaciones evento-asistente
    public List<Assistant> findAll() {
        return data.findAll();
    }

    // Método para obtener una relación por ID
    public Optional<EventAssistant> findById(int id) {
        return Optional.empty();
    }

    // Método para eliminar una relación evento-asistente por ID
    public ResponseDTO deleteById(int id) {
        Optional<EventAssistant> eventAssistant = findById(id);
        if (!eventAssistant.isPresent()) {
            return new ResponseDTO(HttpStatus.NOT_FOUND.toString(), "Registro no encontrado");
        }
        data.deleteById(id);
        return new ResponseDTO(HttpStatus.OK.toString(), "Registro eliminado exitosamente");
    }

    public EventAssistantDTO convertToDTO(EventAssistant eventAssistant) {
        return new EventAssistantDTO(
                eventAssistant.getId_event_assistant(),
                eventAssistant.getEvent(),
                eventAssistant.getAssistant()
        );
    }

    public EventAssistant convertToModel(EventAssistantDTO eventAssistantDTO) {
        Events event = eventAssistantDTO.getEvent();
        Assistant assistant = eventAssistantDTO.getAssistant();
        return new EventAssistant(event, assistant, true);
    }
    
}
