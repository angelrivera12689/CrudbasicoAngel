package com.sena.crud_basic.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.sena.crud_basic.DTO.EventsDTO;
import com.sena.crud_basic.DTO.ResponseDTO;
import com.sena.crud_basic.model.Events;
import com.sena.crud_basic.repository.IEvent;

import java.util.List;
import java.util.Optional;

@Service
public class EventService {
    
    @Autowired
    private IEvent data;  // Event Repository to interact with the database
    
     
    // ✅ Método para guardar un evento con validaciones
    public ResponseDTO save(EventsDTO eventDTO) {
        // Validar que el nombre tenga entre 1 y 100 caracteres
        if (eventDTO.getEventName().length() < 1 || eventDTO.getEventName().length() > 100) {
            return new ResponseDTO(
                HttpStatus.BAD_REQUEST.toString(),
                "El nombre del evento debe estar entre 1 y 100 caracteres"
            );
        }

        // Validar que la fecha no sea nula
        if (eventDTO.getDate() == null) {
            return new ResponseDTO(
                HttpStatus.BAD_REQUEST.toString(),
                "La fecha del evento es obligatoria"
            );
        }

        // Validar que la hora no sea nula
        if (eventDTO.getTime() == null) {
            return new ResponseDTO(
                HttpStatus.BAD_REQUEST.toString(),
                "La hora del evento es obligatoria"
            );
        }

        // Convertir el DTO a modelo
        Events event = convertToModel(eventDTO);

        // Guardar en la base de datos
        data.save(event);

        return new ResponseDTO(
            HttpStatus.OK.toString(),
            "Evento guardado exitosamente"
        );
    }

    // ✅ Método para obtener todos los eventos
    public List<Events> findAll() {
        return data.findAll();
    }

    // ✅ Método para buscar un evento por ID
    public Optional<Events> findById(int id) {
        return data.findById(id);
    }

    // ✅ Método para eliminar un evento por ID
    public ResponseDTO deleteEvent(int id) {
        Optional<Events> eventOpt = findById(id);
        if (!eventOpt.isPresent()) {
            return new ResponseDTO(
                HttpStatus.BAD_REQUEST.toString(),
                "El evento no existe"
            );
        }

        // Si existe, lo eliminamos
        data.deleteById(id);
        return new ResponseDTO(
            HttpStatus.OK.toString(),
            "Evento eliminado correctamente"
        );
    }
    
    // Method to convert an Events entity to EventDTO
    public EventsDTO convertToDTO(Events events) {
        return new EventsDTO(
                events.getEventName(),
                events.getDescription(),
                events.getDate(),
                events.getTime(),
                events.getLocation(),
                events.getCategoryId());
    }
    
    // Method to convert EventDTO to an Events entity
    public Events convertToModel(EventsDTO eventDTO) {
        return new Events(
                0,  // Assuming ID is auto-generated
                eventDTO.getEventName(),
                eventDTO.getDescription(),
                eventDTO.getDate(),
                eventDTO.getTime(),
                eventDTO.getLocation(),
                eventDTO.getCategoryId(), true);
    }
}
    // Other business logic methods (if needed) can be added here
